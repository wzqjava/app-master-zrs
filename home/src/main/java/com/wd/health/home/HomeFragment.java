package com.wd.health.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.BusUtils;
import com.bumptech.glide.Glide;
import com.wd.health.Constant;
import com.wd.health.home.activity.InformationActivity;
import com.wd.health.home.activity.InquiryActivity;
import com.wd.health.home.adapter.DepartmentAdapter;
import com.wd.health.home.adapter.FormationAdapter;
import com.wd.health.home.adapter.PlateAdapter;
import com.wd.health.home.base.MyBaseFragment;
import com.wd.health.home.customview.IncludeImageOne;
import com.wd.health.home.fanye.FrescoLoadUtil;
import com.wd.health.home.fanye.MyLiearLayout;
import com.wd.health.home.fanye.Pager;
import com.wd.health.home.fanye.PagerFactory;
import com.wd.health.home.httpbean.BannerShowBean;
import com.wd.health.home.httpbean.DepartmentBean;
import com.wd.health.home.httpbean.FormationBean;
import com.wd.health.home.httpbean.PlateListBean;
import com.wd.health.home.url.MyUrl;
import com.wd.health.utils.RsaCoder;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

/**
 * Created :  LiZhIX
 * Date :  2019/8/5 14:56
 * Description  :  Fragment 首页
 */
public class HomeFragment extends MyBaseFragment {

    private List<String> list_banner;
    private RecyclerView ImageRecy;
    private RecyclerView PlateRecy;
    private List<FormationBean.ResultBean> list_formation;
    private FormationAdapter formationAdapter;
    private RecyclerView formationRecy;
    private Map<String, Object> formationMap;
    private ImageView assessmentImage;
    private NestedScrollView scroll;
    private IncludeImageOne faq0, faq1;
    private NestedScrollView mNestedScrollView;

    public static final String TAG_WHITE = "ChangeWhite";
    public static final String TAG_TRANSPARENT = "ChangeTransparent";
    private com.wd.health.home.fanye.MyLiearLayout linearLayout;

    private int screenWidth;
    private int screenHeight;
    private Pager pager;
    private Bitmap currentBitmap, mCurPageBitmap, mNextPageBitmap;
    private Canvas mCurPageCanvas;
    private Canvas mNextPageCanvas;
    private PagerFactory pagerFactory;
    //图片数组
    private String[] pages ;
    private int all_width;
    private int avg_width;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void setViewData(View view) {

        all_width = getActivity().getResources().getDisplayMetrics().widthPixels;
        avg_width = all_width/3;
        //实例化bannerlist 和 资讯list
        list_banner = new ArrayList<>();
        list_formation = new ArrayList<>();

        //登陆极光
        loginJg();

        //实例化资讯适配器
        formationAdapter = new FormationAdapter(getActivity(), list_formation);

        //资讯条目点击事件
        formationAdapter.setItemClick(new FormationAdapter.itemClick() {
            @Override
            public void play(int i) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                intent.putExtra("infoId", i + "");
                getActivity().startActivity(intent);
            }
        });

        formationRecy.setAdapter(formationAdapter);
        //网络请求banner
        prsenter.getMethod(MyUrl.BANNER_SHOW, BannerShowBean.class);

    }

    private void loginJg() {
        //从个人信息获取极光账户 密码
        final String nickName = userInfo.getNickName();
        String jiGuangPwd = userInfo.getJiGuangPwd();
        String pwd = "";

        //解密字符串获取密码
        try {
            pwd = RsaCoder.decryptByPublicKey(jiGuangPwd);
            Log.i("pwd", pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("jiguanglogin", "登录++" + nickName);

        //注册极光
        final String finalPwd = pwd;
        JMessageClient.register(nickName, pwd, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("jiguanglogin", "注册" + i + s);
                //登陆极光
                JMessageClient.login(nickName, finalPwd, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Log.i("jiguanglogin", "登录" + i + s);
                    }
                });
            }
        });




    }

    @SuppressLint("CutPasteId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {

        ImageRecy = (RecyclerView) view.findViewById(R.id.ImageRecy);
        PlateRecy = (RecyclerView) view.findViewById(R.id.PlateRecy);
        formationRecy = (RecyclerView) view.findViewById(R.id.FormationRecy);
        assessmentImage = (ImageView) view.findViewById(R.id.assessmentImage);
        scroll = (NestedScrollView) view.findViewById(R.id.scroll);
        faq0 = (IncludeImageOne) view.findViewById(R.id.home_faq0);
        faq1 = (IncludeImageOne) view.findViewById(R.id.home_faq1);
        linearLayout = (MyLiearLayout) view.findViewById(R.id.typeview);
        mNestedScrollView = view.findViewById(R.id.scroll);
        formationRecy.setLayoutManager(new LinearLayoutManager(getActivity()));
        ImageRecy.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        assessmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentByRouter(Constant.ACTIVITY_URL_MINE);
            }
        });

        //下滑事件监听  到一定距离搜索背景变白
        mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 170)
                    BusUtils.postSticky(TAG_WHITE);
                else
                    BusUtils.postSticky(TAG_TRANSPARENT);
            }
        });
        //常见病症点击事件
        faq0.setImageBack(new IncludeImageOne.HomeImageBack() {
            @Override
            public void onClick() {
                Bundle bundle = new Bundle();
                bundle.putInt("select", 0);
                intentByRouter(Constant.ACTIVITY_URL_FAQ, bundle);
            }
        });

        //常见药品点击事件
        faq1.setImageBack(new IncludeImageOne.HomeImageBack() {
            @Override
            public void onClick() {
                Bundle bundle = new Bundle();
                bundle.putInt("select", 1);
                intentByRouter(Constant.ACTIVITY_URL_FAQ, bundle);
            }
        });

    }

    @Override
    public int setLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initImmersionBar() {
    }

    @Override
    public void setData(Object data) {

        if (data instanceof BannerShowBean) {
            setBannerData(data);
            prsenter.getMethod(MyUrl.DEPARTMENT_SHOW, DepartmentBean.class);
        } else if (data instanceof DepartmentBean) {
            setDepartmentData(data);
            prsenter.getMethod(MyUrl.PLATELIST, PlateListBean.class);
        } else if (data instanceof PlateListBean) {
            setPlateListData(data);
        } else if (data instanceof FormationBean) {
            setFormationData(data);
        }

    }

    //资讯标题数据
    private void setPlateListData(Object data) {
        formationMap = new HashMap<>();
        List<PlateListBean.ResultBean> result = ((PlateListBean) data).getResult();
        PlateRecy.setLayoutManager(new GridLayoutManager(getActivity(), result.size()));
        final PlateAdapter plateAdapter = new PlateAdapter(getActivity(), result);
        plateAdapter.setPlateChange(new PlateAdapter.PlateBack() {
            @Override
            public void changed(int i) {
                plateAdapter.notifyDataSetChanged();
                formationMap.put("plateId", i + "");
                prsenter.getMethod(MyUrl.FORMATION, formationMap, FormationBean.class);
            }
        });
        PlateRecy.setAdapter(plateAdapter);

        formationMap.put("plateId", result.get(0).getId() + "");
        formationMap.put("page", "1");
        formationMap.put("count", "5");
        prsenter.getMethod(MyUrl.FORMATION, formationMap, FormationBean.class);
    }

    //资源数据
    private void setFormationData(Object data) {
        list_formation.clear();
        List<FormationBean.ResultBean> result = ((FormationBean) data).getResult();
        list_formation.addAll(result);
        formationAdapter.notifyDataSetChanged();
    }

    //科室列表数据
    private void setDepartmentData(Object data) {
        List<DepartmentBean.ResultBean> result = ((DepartmentBean) data).getResult();
        DepartmentAdapter ImageAdapter = new DepartmentAdapter(getActivity(), result);
        ImageAdapter.setOnClick(new DepartmentAdapter.onClick() {
            @Override
            public void play(int i) {
                Intent intent = new Intent(getActivity(), InquiryActivity.class);
                intent.putExtra("id", i);
                startActivity(intent);
            }
        });
        ImageRecy.setAdapter(ImageAdapter);
    }

    //banner数据
    private void setBannerData(Object data) {
        List<BannerShowBean.ResultBean> result = ((BannerShowBean) data).getResult();
        int size = result.size();
        pages=new String[size];
        for (int i = 0; i < result.size(); i++) {
            String imageUrl = result.get(i).getImageUrl();
            list_banner.add(imageUrl);
            pages[i]=imageUrl;
        }
        initFyView();
        //默认加载第一张图
        loadImage(mCurPageCanvas, 0);

    }

    /**
     * 初始化翻页效果
     */
    private void initFyView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        pager = new Pager(getActivity(), screenWidth, screenHeight);
        //下面这段代码是全屏的,不太方便使用,注掉
/*        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(pager, layoutParams);*/
        //把咱们的图片加入到之前的布局里
        linearLayout.addView(pager);
        //图片类型
        mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        pagerFactory = new PagerFactory(getApplicationContext());
        pager.setBitmaps(mCurPageBitmap, mCurPageBitmap);
        //翻页监听
        pager.setOnTouchListener(new View.OnTouchListener() {
            private int count = pages.length;
            private int currentIndex = 0;
            private int lastIndex = 0;
            private Bitmap lastBitmap = null;
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                boolean ret = false;
                if (v == pager) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {

                        if(e.getX()>avg_width && e.getX()<all_width-avg_width){
                            Toast.makeText(getActivity(), currentIndex+"", Toast.LENGTH_SHORT).show();
                        }else {

                            pager.calcCornerXY(e.getX(), e.getY());
                            lastBitmap = currentBitmap;
                            lastIndex = currentIndex;
                            pagerFactory.onDraw(mCurPageCanvas, currentBitmap);
                            if (pager.DragToRight()) {    // 向右滑动，显示前一页
                                if (currentIndex == 0){
                                    currentIndex=pages.length-1;
                                }else{
                                    pager.abortAnimation();
                                    currentIndex--;
                                }
                                loadImage(mNextPageCanvas, currentIndex);
                            } else {        // 向左滑动，显示后一页
                                if (currentIndex + 1 == count){
                                    currentIndex=0;
                                }else{
                                    pager.abortAnimation();
                                    currentIndex++;
                                }
                                //加载图片并显示出来
                                loadImage(mNextPageCanvas, currentIndex);
                                // Toast.makeText(FanYeActivity.this, "当前第"+currentIndex+"页", Toast.LENGTH_SHORT).show();
                            }

                        }


                    } else if (e.getAction() == MotionEvent.ACTION_MOVE) {

                    } else if (e.getAction() == MotionEvent.ACTION_UP) {
                        if(e.getX()>avg_width && e.getX()<all_width-avg_width){

                        }else {
                            if (!pager.canDragOver()) {
                                currentIndex = lastIndex;
                                currentBitmap = lastBitmap;
                            }
                        }

                    }

                    ret = pager.doTouchEvent(e);
                    return ret;
                }
                return false;
            }
        });
    }

    /**
     * 请求图片并显示
     * @param canvas
     * @param index
     */
    private void loadImage(final Canvas canvas, int index) {
        //使用封装的Fresco图片加载框架的工具类请求得到网络图片的Bitmap
        FrescoLoadUtil.getInstance().loadImageBitmap(pages[index], new FrescoLoadUtil.FrescoBitmapCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap result) {
                if (result!=null){
                    //原来的大小
                    int width = result.getWidth();
                    int height = result.getHeight();
                    // 设置想要的大小
                    int newWidth = linearLayout.getMeasuredWidth();
                    int newHeight = linearLayout.getMeasuredHeight();
                    // 计算缩放比例
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;
                    // 取得想要缩放的matrix参数
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    // 修改得到的bitmap的大小,根据个人需求自行设置
                    Bitmap bitMap = Bitmap.createBitmap(result, 0, 0, width, height, matrix, true);
                    currentBitmap = bitMap;
                    //传入得到的图片
                    pagerFactory.onDraw(canvas, bitMap);
                    pager.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    pager.postInvalidate();
                }else {
                    Log.e("result为空","---");
                }

            }

            @Override
            public void onFailure(String throwable) {
                Log.e("onFailure",throwable);
            }


        });
    }



}