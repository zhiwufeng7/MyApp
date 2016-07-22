package com.qianfeng.android.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qianfeng.android.myapp.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView login_wb;
    private ImageView login_qq_black;
    private ImageView ogin_wx_black;
    private ImageView btn_clean_1;
    private ImageView img_no_sea;
    private ImageView btn_left_back_2;
    private EditText assword_et;
    private ImageView btn_clean_2;
    private EditText assword_et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        initView();
        initListener();

    }

    private void initListener() {
        login_wb.setOnClickListener(this);
        login_qq_black.setOnClickListener(this);
        ogin_wx_black.setOnClickListener(this);
        btn_left_back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        assword_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = assword_et.getText().toString().trim();
                if(trim.length()>0) {
                    btn_clean_1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        assword_et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String trim = assword_et1.getText().toString().trim();
                if(trim.length()>0) {
                    btn_clean_2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        btn_clean_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assword_et.setText(null);
                btn_clean_1.setVisibility(View.GONE);
            }
        });

        btn_clean_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assword_et1.setText(null);
                btn_clean_2.setVisibility(View.GONE);
            }
        });

        img_no_sea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int inputType = assword_et.getInputType();
                if (inputType!=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    img_no_sea.setImageDrawable(getResources().getDrawable(R.drawable.img_see));
                    // 显示为普通文本
                    assword_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    // 使光标始终在最后位置
                    Editable etable = assword_et.getText();
                    Selection.setSelection(etable, etable.length());
                }else {
                    img_no_sea.setImageDrawable(getResources().getDrawable(R.drawable.img_no_sea));
                    assword_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    // 使光标始终在最后位置
                    Editable etable = assword_et.getText();
                    Selection.setSelection(etable, etable.length());
                }

            }
        });
    }

    private void initView() {
        login_wb = (ImageView) findViewById(R.id.login_wb);
        login_qq_black = (ImageView) findViewById(R.id.login_qq_black);
        ogin_wx_black = (ImageView) findViewById(R.id.login_wx_black);
        btn_clean_1 = (ImageView) findViewById(R.id.btn_clean_1);
        img_no_sea = (ImageView) findViewById(R.id.img_no_sea);
        btn_left_back_2 = (ImageView) findViewById(R.id.btn_left_back_2);
        assword_et =  (EditText)findViewById(R.id.assword_et);
        btn_clean_2 = (ImageView) findViewById(R.id.btn_clean_2);
        assword_et1 =  (EditText)findViewById(R.id.assword_et1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_wb:
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(weibo);
                break;
            case R.id.login_qq_black:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
            case R.id.login_wx_black:
                Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
                authorize(weixin);
                Toast.makeText(LoginActivity.this,"微信appid有误，无法连接到微信", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void authorize(Platform platform) {
        //判断用户是否已经授权
        if (platform.isAuthValid()) {
            //获取用户名
            String userName = platform.getDb().getUserName();
            Log.d("google_lenve_fb", "authorize: " + userName);
        } else {
            //引导用户进行登录
            platform.authorize();
        }
        //监听三方登录状态
        platform.setPlatformActionListener(new PlatformActionListener() {
            //登录成功时回调该方法
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            //登录失败时回调该方法
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(LoginActivity.this,"登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
