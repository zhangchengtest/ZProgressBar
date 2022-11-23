package com.zpj.progressbar.demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.zpj.progressbar.demo.utils.JumpUtils;


public class LoginActivity extends AppCompatActivity {

    private TextView ipAddrTextView, portTextView;
    private AppCompatCheckBox item_pwd;
    private AppCompatTextView do_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        ipAddrTextView = findViewById(R.id.ip_addr);
        portTextView = findViewById(R.id.port);
        do_login = findViewById(R.id.do_login);
        do_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validParam();
                doLogin();


            }
        });

        // 记住密码
        item_pwd = findViewById(R.id.item_pwd);
        item_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        // 记住了密码补全用户信息
        fillUser();
    }

    private void fillUser() {
        SharedPreferences p = getSharedPreferences("user", MODE_PRIVATE);
        String ipAddr = p.getString("ipAddr", "");
        String port = p.getString("port", "");
        String nickName = p.getString("nickName", "");
        if (!TextUtils.isEmpty(ipAddr)) {
            ipAddrTextView.setText(ipAddr);
        }else{
            ipAddrTextView.setText("192.168.71.255");
        }

        if (!TextUtils.isEmpty(port)) {
            portTextView.setText(port);
        }else{
            portTextView.setText("7768");
        }


    }

    /**
     * 保存用户信息
     */
    private void saveUser() {
        SharedPreferences s = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        boolean b = item_pwd.isChecked();
        if (b) {
            editor.putString("ipAddr", ipAddrTextView.getText().toString());
            editor.putString("port", portTextView.getText().toString());
        } else {
            editor.clear();
        }
        editor.apply();
    }

    /**
     * 参数校验
     */
    private void validParam() {
        if (TextUtils.isEmpty(ipAddrTextView.getText())) {
            Toast.makeText(LoginActivity.this, "请输入ip！", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(portTextView.getText())) {
            Toast.makeText(LoginActivity.this, "请输入端口！", Toast.LENGTH_LONG).show();
            return;
        }

    }

    /**
     * 执行login操作
     */
    private void doLogin() {
        saveUser();

        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
        String ipStr = "http://"+ipAddrTextView.getText().toString() + ":"+ portTextView.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("ipStr", ipStr);//往Bundle中存放数据
        JumpUtils.startActivity(LoginActivity.this, MainActivity.class, bundle, false);
    }
}