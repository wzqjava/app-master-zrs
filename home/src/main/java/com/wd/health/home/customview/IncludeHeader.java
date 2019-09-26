package com.wd.health.home.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.health.home.R;

/**
 * @author 荣生
 * @description: 头部自定义view
 * @date :2019/8/8 9:07
 */
public class IncludeHeader extends LinearLayout {

    private String text;
    private View view;
    private TextView mText;
    private ImageView header_pic;
    private Drawable drawable;
    private int message_flag;
    private ImageView message_iv;
    private TextView message_num;

    public IncludeHeader(Context context) {
        super(context);
    }

    public IncludeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.home_include_header, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncludeHeader);
        text = array.getString(R.styleable.IncludeHeader_headerText);
        drawable = array.getDrawable(R.styleable.IncludeHeader_headerImage);
        message_flag = array.getInteger(R.styleable.IncludeHeader_headerMessage, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        header_pic = findViewById(R.id.header_pic);
        message_iv = view.findViewById(R.id.message_iv);
        message_num = view.findViewById(R.id.message_num);


        if (null != drawable) {
            header_pic.setImageDrawable(drawable);
            header_pic.setPadding(10, 10, 10, 10);
        }


        mText = findViewById(R.id.header_title);
        mText.setText(text);


        if(message_flag !=0){
            message_iv.setVisibility(INVISIBLE);
            message_num.setVisibility(INVISIBLE);
        }


    }
}
