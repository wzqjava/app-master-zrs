package com.wd.health.home.fanye;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author: 张荣生
 * date: 2019/8/28 16:42
 * update: $date$
 */
public class MyLiearLayout extends LinearLayout {
    public MyLiearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels / 3);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
