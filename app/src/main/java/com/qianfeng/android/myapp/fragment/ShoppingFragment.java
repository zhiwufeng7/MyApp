package com.qianfeng.android.myapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.MainActivity;
import com.qianfeng.android.myapp.adapter.ShoppingCartEVAdapter;


public class ShoppingFragment extends Fragment {


    private ExpandableListView expandableListView;
    private Button emptyButton;
    private ShoppingCartEVAdapter adapter;

    public ShoppingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShoppingFragment newInstance() {
        ShoppingFragment fragment = new ShoppingFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.shopping_lv);

        //设置空视图
        initEmptyView(inflater);

        initListener();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        initAdapter();

    }

    private void initAdapter() {
        if(adapter==null){
            adapter = new ShoppingCartEVAdapter(getActivity());
            expandableListView.setAdapter(adapter);
        }else {
            adapter.initData();
            adapter.notifyDataSetChanged();
        }


        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    private void initListener() {

        //点击去逛逛的时候跳转到分类界面
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                RadioButton radiobutton = (RadioButton) mainActivity.radioGroup.getChildAt(1);
                radiobutton.setChecked(true);
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    private void initEmptyView(LayoutInflater inflater) {

        View emptyView = inflater.inflate(R.layout.shopping_empty_view, null);

        emptyButton = (Button) emptyView.findViewById(R.id.shopping_empty_btn);

        ViewGroup viewGroup = (ViewGroup) expandableListView.getParent();

        viewGroup.addView(emptyView);
        expandableListView.setEmptyView(emptyView);

    }



}
