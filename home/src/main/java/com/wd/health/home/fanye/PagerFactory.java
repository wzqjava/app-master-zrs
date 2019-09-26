package com.wd.health.home.fanye;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * author: 张荣生
 * date: 2019/8/28 16:42
 * update: $date$
 */
public class PagerFactory {
    private Context mContext;
    private Bitmap m_book_bg = null;

    private int m_backColor = 0xffff9e85;  // 背景颜色

    private boolean m_isfirstPage,m_islastPage;

    private Paint mPaint;

    public PagerFactory(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(m_backColor);
    }

    public void onDraw(Canvas c) {
        if (m_book_bg == null)
            c.drawColor(m_backColor);
        else
            c.drawBitmap(m_book_bg, 0, 0, null);
    }

    public void onDraw(Canvas c, Bitmap bitmap){
        c.drawBitmap(bitmap, 0, 0, null);
    }


    public void setBgBitmap(Bitmap BG) {
        m_book_bg = BG;
    }
}
