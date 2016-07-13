package com.qianfeng.android.myapp.presenter;
import com.qianfeng.android.myapp.handle.MainCallBack;
import com.qianfeng.android.myapp.model.MainModel;
import com.qianfeng.android.myapp.model.OnMainModel;
import com.qianfeng.android.myapp.view.MainView;

/**
 * 实现Model里面回调的方法
 *
 * Created by my on 2016/7/12.
 */
public class OnMainPresenter implements MainPresenter ,MainCallBack{

    private MainView view;
    private MainModel model;

    public OnMainPresenter(MainView view){
        this.view=view;
        model=new OnMainModel();

    }



    @Override
    public void getString() {
       model.loadData("sss",this);

    }

    @Override
    public void onSuccess(String str) {

        view.setText(str);
    }

    @Override
    public void onError() {

        view.setText("错误");
    }
}
