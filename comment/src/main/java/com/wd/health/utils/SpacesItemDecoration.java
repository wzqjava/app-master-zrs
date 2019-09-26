package com.wd.health.utils;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created :  LiZhIX
 * Date :  2019/8/14 17:22
 * Description  :   这是LinearLayoutManager设置Item间距的的一个辅助类
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private HashMap<String, Integer> spaceValue; //间隔
    private boolean includeEdge; //是否包含四周边距

    public static final String TOP_SPACE = "top_space";
    public static final String BOTTOM_SPACE = "bottom_space";
    public static final String LEFT_SPACE = "left_space";
    public static final String RIGHT_SPACE = "right_space";

    public SpacesItemDecoration(HashMap spaceValue, boolean includeEdge) {
        this.spaceValue = spaceValue;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (includeEdge) {
            if (spaceValue.get(LEFT_SPACE) != null)
                outRect.left = spaceValue.get(LEFT_SPACE);
            if (spaceValue.get(RIGHT_SPACE) != null)
                outRect.right = spaceValue.get(RIGHT_SPACE);
            if (spaceValue.get(BOTTOM_SPACE) != null)
                outRect.bottom = spaceValue.get(BOTTOM_SPACE);
            if (spaceValue.get(TOP_SPACE) != null)
                outRect.top = spaceValue.get(TOP_SPACE);
        } else {
            if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.left = 0;
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1)
                    outRect.right = 0;
            }
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) == 0)
                    outRect.top = 0;
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1)
                    outRect.bottom = 0;
            }
        }
    }

}