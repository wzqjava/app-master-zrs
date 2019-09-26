package com.wd.health.home.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.health.home.R;

/**
 * @author 荣生
 * @description:首页 标题 view
 * @date :2019/8/6 19:34
 */
public class IncludeTitle extends LinearLayout {

    private String text;
    private View view;
    private TextView mText;

    public IncludeTitle(Context context) {
        super(context);
    }

    public IncludeTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.home_include_title, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncludeTitle);
        text = array.getString(R.styleable.IncludeTitle_mText);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mText = view.findViewById(R.id.mText);
        mText.setText(text);
    }
}
