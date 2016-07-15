package com.qianfeng.android.myapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianfeng.android.myapp.R;

/**
 * Created by Administrator on 2016/7/13.
 */
public class CommonLocationFragment extends Fragment {

    public static CommonLocationFragment newInstance() {
        CommonLocationFragment fragment = new CommonLocationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_common_location,null);
    }
}
