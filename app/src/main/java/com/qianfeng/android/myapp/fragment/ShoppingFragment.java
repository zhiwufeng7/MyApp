package com.qianfeng.android.myapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qianfeng.android.myapp.R;


public class ShoppingFragment extends Fragment {


    private ListView listView;

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

        View view=inflater.inflate(R.layout.fragment_shopping, container, false);

        listView= (ListView) view.findViewById(R.id.shopping_lv);

        //设置空视图
        initEmptyView(inflater);



        return view ;
    }

    private void initEmptyView(LayoutInflater inflater) {

        View emptyView=inflater.inflate(R.layout.shopping_empty_view,null);

        ViewGroup viewGroup= (ViewGroup) listView.getParent();

        viewGroup.addView(emptyView);
        listView.setEmptyView(emptyView);

    }


}
