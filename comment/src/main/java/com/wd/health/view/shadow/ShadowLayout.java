package com.wd.health.view.shadow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wd.health.comment.R;

/**
 * githubAuthor : lihangleo2
 * Created :  LiZhIX
 * Date :  2019/8/5 14:54
 * Description  :  阴影
 *
 * 圆角属性
 * app:hl_cornerRadius="18dp" 阴影圆角属性（同时如果设置了背景填充色也是背景圆角）
 * 阴影扩散程度
 * app:hl_shadowLimit="5dp" 阴影的扩散区域
 * 阴影布局背景颜色值
 * app:hl_shadowBackColor="#fff" 阴影布局背景填充色，圆角属性即是阴影圆角
 * 阴影的颜色
 * app:hl_shadowColor="#2a000000" 阴影的颜色可以随便改变,透明度的改变可以改变阴影的清晰程度
 * x轴的偏移量
 * app:hl_dx="0dp" 也可以理解为左右偏移量
 * y轴的偏移量
 * app:hl_dy="0dp" 也可以理解为上下的偏移量
 * 阴影的4边可见不可见（与偏移量无关）
 * app:hl_leftShow="false" 左边的阴影不可见，其他3边保持不变
 *
 *
 */
public class ShadowLayout extends FrameLayout {

    private int mBackGroundColor;
    private int mShadowColor;
    private float mShadowLimit;
    private float mCornerRadius;
    private float mDx;
    private float mDy;
    private boolean leftShow;
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    private Paint shadowPaint;
    private Paint paint;

    private int leftPading;
    private int topPading;
    private int rightPading;
    private int bottomPading;
    //阴影布局子空间区域
    private RectF rectf = new RectF();

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setMDx(float mDx) {
        if (Math.abs(mDx) > mShadowLimit) {
            if (mDx > 0) {
                this.mDx = mShadowLimit;
            } else {
                this.mDx = -mShadowLimit;
            }
        } else {
            this.mDx = mDx;
        }
        setPading();
    }


    public void setMDy(float mDy) {
        if (Math.abs(mDy) > mShadowLimit) {
            if (mDy > 0) {
                this.mDy = mShadowLimit;
            } else {
                this.mDy = -mShadowLimit;
            }
        } else {
            this.mDy = mDy;
        }
        setPading();
    }


    public float getmCornerRadius() {
        return mCornerRadius;
    }


    public void setmCornerRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        setBackgroundCompat(getWidth(), getHeight());
    }


    public float getmShadowLimit() {
        return mShadowLimit;
    }

    public void setmShadowLimit(int mShadowLimit) {
        this.mShadowLimit = mShadowLimit;
        setPading();
    }


    public void setmShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        setBackgroundCompat(getWidth(), getHeight());
    }

    public void setLeftShow(boolean leftShow) {
        this.leftShow = leftShow;
        setPading();
    }

    public void setRightShow(boolean rightShow) {
        this.rightShow = rightShow;
        setPading();
    }

    public void setTopShow(boolean topShow) {
        this.topShow = topShow;
        setPading();
    }

    public void setBottomShow(boolean bottomShow) {
        this.bottomShow = bottomShow;
        setPading();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }


    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);

        //矩形画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mBackGroundColor);

        setPading();
    }


    public void setPading() {
        int xPadding = (int) (mShadowLimit + Math.abs(mDx));
        int yPadding = (int) (mShadowLimit + Math.abs(mDy));

        if (leftShow) {
            leftPading = xPadding;
        } else {
            leftPading = 0;
        }

        if (topShow) {
            topPading = yPadding;
        } else {
            topPading = 0;
        }


        if (rightShow) {
            rightPading = xPadding;
        } else {
            rightPading = 0;
        }

        if (bottomShow) {
            bottomPading = yPadding;
        } else {
            bottomPading = 0;
        }

        setPadding(leftPading, topPading, rightPading, bottomPading);
    }


    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }

        try {
            //默认是显示
            leftShow = attr.getBoolean(R.styleable.ShadowLayout_hl_leftShow, true);
            rightShow = attr.getBoolean(R.styleable.ShadowLayout_hl_rightShow, true);
            bottomShow = attr.getBoolean(R.styleable.ShadowLayout_hl_bottomShow, true);
            topShow = attr.getBoolean(R.styleable.ShadowLayout_hl_topShow, true);
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, getResources().getDimension(R.dimen.dp_0));
            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, getResources().getDimension(R.dimen.dp_5));

            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_hl_dx, 0);
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_hl_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, getResources().getColor(R.color.colorDefaultShadowBack));
            mBackGroundColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowBackColor, getResources().getColor(R.color.colorAppWhite));
        } finally {
            attr.recycle();
        }
    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

        if (dy > 0) {
            shadowRect.top += dy;
            shadowRect.bottom -= dy;
        } else if (dy < 0) {
            shadowRect.top += Math.abs(dy);
            shadowRect.bottom -= Math.abs(dy);
        }

        if (dx > 0) {
            shadowRect.left += dx;
            shadowRect.right -= dx;
        } else if (dx < 0) {

            shadowRect.left += Math.abs(dx);
            shadowRect.right -= Math.abs(dx);
        }

        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectf.left = leftPading;
        rectf.top = topPading;
        rectf.right = getWidth() - rightPading;
        rectf.bottom = getHeight() - bottomPading;
        int trueHeight = (int) (rectf.bottom - rectf.top);
        if (mCornerRadius > trueHeight / 2) {
            //画圆角矩形
            canvas.drawRoundRect(rectf, trueHeight / 2, trueHeight / 2, paint);
        } else {
            canvas.drawRoundRect(rectf, mCornerRadius, mCornerRadius, paint);
        }
    }

}