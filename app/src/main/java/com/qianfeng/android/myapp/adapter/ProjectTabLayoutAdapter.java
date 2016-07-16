package com.qianfeng.android.myapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 2016/7/15.
 */
public class ProjectTabLayoutAdapter  extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<String> titles;

    public ProjectTabLayoutAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList==null ? 0:fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }
}
