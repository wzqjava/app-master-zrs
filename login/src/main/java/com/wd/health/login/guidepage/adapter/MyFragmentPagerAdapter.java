package com.wd.health.login.guidepage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created :  LiZhIX
 * Date :  2019/8/8 10:12
 * Description  :
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> frags;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        this.frags = frags;
    }

    @Override
    public Fragment getItem(int i) {
        return frags.get(i);
    }

    @Override
    public int getCount() {
        return frags.size() != 0 ? frags.size() : 0;
    }
}