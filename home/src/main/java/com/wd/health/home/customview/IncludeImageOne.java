package com.wd.health.home.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wd.health.home.R;

/**
 * @author 荣生
 * @description:首页 常见view
 * @date :2019/8/6 19:34
 */
public class IncludeImageOne extends LinearLayout {

    private String text;
    private View view;
    private TextView mText;
    private String image;
    private ImageView mImage;
    private HomeImageBack back;

    public IncludeImageOne(Context context) {
        super(context);
    }

    public IncludeImageOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.home_include_department, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncludeImageOne);
        text = array.getString(R.styleable.IncludeImageOne_mTextOne);
        image = array.getString(R.styleable.IncludeImageOne_mImageOne);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mText = view.findViewById(R.id.mText);
        mImage = view.findViewById(R.id.image);

        mText.setText(text);
        if (image.equals("1")) {
            mImage.setImageResource(R.drawable.common_disease);
        } else {
            mImage.setImageResource(R.drawable.common_drugs);
        }

        mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (back != null) {
                    back.onClick();
                }
            }
        });


    }

    public interface HomeImageBack {
        void onClick();
    }

    public void setImageBack(HomeImageBack back) {
        this.back = back;
    }

}
