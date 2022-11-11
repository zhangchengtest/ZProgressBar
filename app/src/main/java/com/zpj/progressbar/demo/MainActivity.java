package com.zpj.progressbar.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonElement;
import com.zpj.progressbar.ZProgressBar;
import com.zpj.progressbar.demo.dto.DownloadDTO;
import com.zpj.progressbar.demo.utils.GsonUtil;
import com.zpj.progressbar.demo.utils.HttpUrl;
import com.zpj.progressbar.demo.utils.Installation;
import com.zpj.progressbar.demo.utils.OKHttp3Util;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ZProgressBar progressBar;
    private Button restartBtn;

    private static final String BROADCAST_ACTION_DISC = "com.cunw.cloud.peer.download.broadcast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress_test);
        restartBtn = findViewById(R.id.btn_restart);
        progressBar.setProgress(0);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadDTO dto = new DownloadDTO();
                dto.setMachineCode(Installation.id());
                dto.setFilePath("http://blog.punengshuo.com/app-2.0.4.zip");
                download(dto);
            }
        });

        // 注册广播接收
        BroadcastReceiver receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION_DISC); // 只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);

        requestMyPermissions();

    }

    private void download(  DownloadDTO dto) {
//        final Resources resources = getResources();
//        final SharedPreferencesUtil sp = new SharedPreferencesUtil(activity);
        String token = "test-token";

        Log.i(TAG, "enter here " );

        String json = GsonUtil.GsonString(dto);
        OKHttp3Util.postAsyn(HttpUrl.DOWNLOAD_REQUEST, token, json, new OKHttp3Util.ResultCallback<JsonElement>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "请求失败=" + e.toString());
            }

            @Override
            public void onResponse(JsonElement response) {
                Log.e(TAG,"成功--->" + response.toString());
            }
        });
        Log.i(TAG, "enter end " );
    }


    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(MainActivity.this,
//                    "receive broadcast", Toast.LENGTH_LONG).show();
            progressBar.setProgress(intent.getExtras().getInt("count"));
        }

    }

    private void requestMyPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            Log.d(TAG, "requestMyPermissions: 有写SD权限");
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //没有授权，编写申请权限代码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
            Log.d(TAG, "requestMyPermissions: 有读SD权限");
        }

    }

}