package com.qianfeng.android.myapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.CollectionActivity;

public class MyFragment extends Fragment {


    private LinearLayout myCollection;

    public MyFragment() {
        // Required empty public constructor
    }


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);


        initView(view);
        initListener();


        return view;
    }

    private void initListener() {
        myCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        myCollection = (LinearLayout) view.findViewById(R.id.my_collection_ll);

    }


}
