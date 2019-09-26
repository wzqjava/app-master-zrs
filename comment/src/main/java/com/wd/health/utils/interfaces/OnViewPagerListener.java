package com.wd.health.utils.interfaces;

/**
 * Created :  LiZhIX
 * Date :  2019/8/15 13:25
 * Description  :
 */
public interface OnViewPagerListener {

    /*初始化完成*/
    void onInitComplete();

    /*释放的监听*/
    void onPageRelease(boolean isNext, int position);

    /*选中的监听以及判断是否滑动到底部*/
    void onPageSelected(int position, boolean isBottom);

}
