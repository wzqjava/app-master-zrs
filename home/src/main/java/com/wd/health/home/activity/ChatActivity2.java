package com.wd.health.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.health.config.ConfigApp;
import com.wd.health.home.R;
import com.wd.health.home.adapter.ChatingAdapter2;
import com.wd.health.home.base.MyBaseActivity;
import com.wd.health.home.bean.DialogueBean;
import com.wd.health.home.emjoy.DefEmoticons;
import com.wd.health.home.emjoy.EmjoyAdapter;
import com.wd.health.home.url.MyUrl;
import com.wd.health.home.util.MyMediaRecorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
/**
 * 张荣生
 * 聊天界面
 */
public class ChatActivity2 extends MyBaseActivity {

    private String sendName,doctorName,path;
    List<DialogueBean> list;
    Handler handler = new Handler();
    private ChatingAdapter2 adapter;
    private TextView name;
    private XRecyclerView recy;
    private EditText message,message_yy;
    private List<Message> all_MessageList;
    private Button send,picture,yy,emjou_button;
    private MyMediaRecorder instance;
    private int yy_flag = 0, emjoy_flag = 0;
    private int all_page,page=0,doctorId,inquiryId;
    private LinearLayoutManager manager;
    private RecyclerView emjoy;
    private long startTime,endTime;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_chat2;
    }
    @Override
    protected void initData() {
        list.clear();
        List<Conversation> conversationList = JMessageClient.getConversationList();
        Conversation singleConversation1 = Conversation.createSingleConversation(sendName, ConfigApp.APP_KEY);
        Conversation singleConversation = JMessageClient.getSingleConversation(sendName, ConfigApp.APP_KEY);
        List<Message> allMessage = singleConversation.getAllMessage();
        all_MessageList=allMessage;

        //数据分页
        DataPaging(allMessage);

        if (adapter.getItemCount() > 0) {
            recy.scrollToPosition(adapter.getItemCount());
        }
        //表情包数据
        EmjoyData();

    }

    /**
     * 数据分页
     * @param allMessage
     */
    private void DataPaging(List<Message> allMessage) {
        if(allMessage!=null){

            int size = allMessage.size();
            int sum=size%15;
            all_page=size/15;

            if(size>=15){

                if(sum>=10){
                    page=all_page;
                    for (int i = page*15-1; i <all_MessageList.size(); i++) {
                        Message message = all_MessageList.get(i);
                        String userName = message.getFromUser().getUserName();
                        if (userName.equals(sendName)) {
                            list.add(new DialogueBean(message, 0));
                        } else {
                            list.add(new DialogueBean(message, 1));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    page=all_page-1;
                    int startItem;
                    if(page==0){
                        startItem=0;
                    }else {
                        startItem=page*15-1;
                    }
                    for (int i =startItem ; i <all_MessageList.size() ; i++) {
                        Message message = all_MessageList.get(i);
                        String userName = message.getFromUser().getUserName();
                        if (userName.equals(sendName)) {
                            list.add(new DialogueBean(message, 0));
                        } else {
                            list.add(new DialogueBean(message, 1));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }else {
                page=0;
                for (Message message : allMessage) {
                    String userName = message.getFromUser().getUserName();
                    if (userName.equals(sendName)) {
                        MessageContent content = message.getContent();
                        list.add(new DialogueBean(message, 0));
                    } else {
                        list.add(new DialogueBean(message, 1));
                    }
                }
                adapter.notifyDataSetChanged();
            }


        }
    }

    /**
     * 加载表情包数据
     */
    private void EmjoyData() {
        GridLayoutManager manager = new GridLayoutManager(this, 8);
        emjoy.setLayoutManager(manager);
        EmjoyAdapter adapter = new EmjoyAdapter(this, DefEmoticons.sEmojiArray);
        adapter.setOnClick(new EmjoyAdapter.Click() {
            @Override
            public void play(String s) {
                Editable text = message.getText();
                String xiaox = text + s;
                message.setText(xiaox);
                message.setSelection(xiaox.length());
            }
        });
        emjoy.setAdapter(adapter);
    }

    @Override
    protected void initView() {

        //注册极光事件
        JMessageClient.registerEventReceiver(this);
        instance = MyMediaRecorder.getInstance();

        Intent intent = getIntent();
        sendName=intent.getStringExtra("name");
        doctorName = intent.getStringExtra("doctorName");
        inquiryId = intent.getIntExtra("inquiryId", -1);
        doctorId = intent.getIntExtra("doctorId", -1);

        emjoy = (RecyclerView) findViewById(R.id.emjoy);
        name = (TextView) findViewById(R.id.name);
        recy = (XRecyclerView) findViewById(R.id.recy);
        message = (EditText) findViewById(R.id.message);
        message_yy = (EditText) findViewById(R.id.message_yy);
        send = (Button) findViewById(R.id.send);
        picture = (Button) findViewById(R.id.picture);
        yy = (Button) findViewById(R.id.yy);
        emjou_button = (Button) findViewById(R.id.emjou_button);

        name.setText(doctorName);
        manager = new LinearLayoutManager(this);
//        manager.setStackFromEnd(true);
        recy.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new ChatingAdapter2(list, this);
        recy.setAdapter(adapter);

        recy.setLoadingMoreEnabled(false);
        recy.setPullRefreshEnabled(true);

        initYY();
        initLisener();
    }

    private void initLisener() {

        //点击发送事件
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendClick();
            }
        });

       //点击图片事件
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_PICK);
                intent1.setType("image/*");
                startActivityForResult(intent1, 111);
            }
        });

        //点击语音事件
        yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(yy_flag==0){
                    message.setVisibility(View.GONE);
                    message_yy.setVisibility(View.VISIBLE);
                    yy.setBackgroundResource(R.drawable.common_icon_keyboard_n);
                    yy_flag=1;
                }else {
                    message.setVisibility(View.VISIBLE);
                    message_yy.setVisibility(View.GONE);
                    yy.setBackgroundResource(R.drawable.common_icon_voice_n);
                    yy_flag=0;
                }

            }
        });

        emjou_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emjoy_flag == 0) {
                    emjoy.setVisibility(View.VISIBLE);
                    emjou_button.setBackgroundResource(R.drawable.common_icon_keyboard_n);
                    emjoy_flag = 1;
                } else {
                    emjoy.setVisibility(View.GONE);
                    emjou_button.setBackgroundResource(R.drawable.common_icon_expression_n);
                    emjoy_flag = 0;
                }
            }
        });

        recy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if(page==0){
                    Toast.makeText(ChatActivity2.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                }else {
                    page--;
                    int startItem;
                    if(page==0){
                        startItem=0;
                    }else {
                        startItem=page*15-1;
                    }

                    int endItem=page+1;

                    for (int i = startItem; i <endItem*15-1 ; i++) {
                        List<DialogueBean> morelist=new ArrayList<>();
                        Message message = all_MessageList.get(i);
                        String userName = message.getFromUser().getUserName();
                        if (userName.equals(sendName)) {
                            morelist.add(new DialogueBean(message, 0));
                        } else {
                            morelist.add(new DialogueBean(message, 1));
                        }
                        List<DialogueBean> newlist=new ArrayList<>();
                        newlist.addAll(morelist);
                        newlist.addAll(list);
                        list.clear();
                        list.addAll(newlist);
                        adapter.notifyDataSetChanged();
                        morelist=null;
                        newlist=null;
                    }

                    if(page!=0){
                        recy.scrollToPosition(15+lastVisibleItemPosition);
                    }

                }
                recy.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });


    }

    /**
     * 点击发送
     */
    private void sendClick() {
        String messages = message.getText().toString();
        if (!messages.equals("")) {
            Message singleTextMessage = JMessageClient.createSingleTextMessage(sendName, ConfigApp.APP_KEY, messages);
            JMessageClient.sendMessage(singleTextMessage);
            message.setText("");
            list.add(new DialogueBean(singleTextMessage,1));
            adapter.notifyDataSetChanged();
            recy.scrollToPosition(adapter.getItemCount());

            Map<String, Object> map = new HashMap<>();
            map.put("inquiryId", inquiryId);
            map.put("msgContent", messages);
            map.put("type", 1);
            map.put("doctorId", doctorId);
            prsenter.postMethod(MyUrl.pushMessage, map, null);

        }
    }

    private void initYY() {
        message_yy.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yy_Down();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        yy_Up();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 结束录制语音
     */
    private void yy_Up() {
        message_yy.setText("按住 说话");
        endTime = System.currentTimeMillis();
        stopRecord();

        if(endTime - startTime >=1000){
            File file = new File(path);
            Message singleVoiceMessage = null;
            try {
                singleVoiceMessage = JMessageClient.createSingleVoiceMessage(sendName, ConfigApp.APP_KEY, file, 10 * 1000);
            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException", e.getMessage());
            }
            JMessageClient.sendMessage(singleVoiceMessage);
            Toast.makeText(ChatActivity2.this, "路是完毕", Toast.LENGTH_SHORT).show();
            list.add(new DialogueBean(singleVoiceMessage,1));
            adapter.notifyDataSetChanged();
            recy.scrollToPosition(adapter.getItemCount() - 1);

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            MultipartBody part = builder.addFormDataPart("content", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/octet-stream"),
                            file)).build();


            Map<String, Object> map = new HashMap<>();
            map.put("inquiryId", inquiryId);
            map.put("type", 3);
            map.put("doctorId", doctorId);
            prsenter.sendMessage(MyUrl.pushMessage, map, part, null);

        }else {
            Toast.makeText(ChatActivity2.this, "录制时间不足", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 开始录制语音
     */
    private void yy_Down() {
        startTime = System.currentTimeMillis();
        message_yy.setText("正在 录音");
        Toast.makeText(ChatActivity2.this, "开始录制", Toast.LENGTH_SHORT).show();
        path = Environment.getExternalStorageDirectory() + "/" + "jchat_audio.m4a";
        startRecord(path);
    }

    /**
     * 在线消息
     */
    public void onEvent(final MessageEvent event) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = event.getMessage();
                String userName = message.getFromUser().getUserName();
                if (userName.equals(sendName)) {
                    list.add(new DialogueBean(message, 0));
                } else {
                    list.add(new DialogueBean(message, 1));
                }
                adapter.notifyDataSetChanged();
                recy.scrollToPosition(adapter.getItemCount());
            }
        }, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 111) {

            Uri selectedImage = data.getData();

            String realPathFromURI = getRealPathFromURI(selectedImage);
            File file = new File(realPathFromURI);
            try {
                Message singleImageMessage = JMessageClient.createSingleImageMessage(sendName, ConfigApp.APP_KEY, file);
                JMessageClient.sendMessage(singleImageMessage);
                list.add(new DialogueBean(singleImageMessage,1));
                adapter.notifyDataSetChanged();
                recy.scrollToPosition(adapter.getItemCount());

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                MultipartBody part = builder.addFormDataPart("content", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file)).build();


                Map<String, Object> map = new HashMap<>();
                map.put("inquiryId", inquiryId);
                map.put("type", 2);
                map.put("doctorId", doctorId);
                prsenter.sendMessage(MyUrl.pushMessage, map, part, null);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "图片发送失败", Toast.LENGTH_SHORT).show();
            }

        }


    }

    /**
     * 通过url 获取文件路径
     */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 开始录音
     */
    private void startRecord(String path) {
        instance.startRecord(path);
    }

    /**
     * 结束录音
     */
    private void stopRecord() {
        instance.stopRecord();
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
        handler = null;
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    protected void destroyData() {

    }
}
