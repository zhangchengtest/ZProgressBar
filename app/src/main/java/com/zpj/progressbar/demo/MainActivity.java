package com.zpj.progressbar.demo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonElement;
import com.zpj.progressbar.ZProgressBar;
import com.zpj.progressbar.demo.dto.DownloadDTO;
import com.zpj.progressbar.demo.fragments.AccessFragment;
import com.zpj.progressbar.demo.fragments.FilesFragment;
import com.zpj.progressbar.demo.model.Content;
import com.zpj.progressbar.demo.model.LiteViewModel;
import com.zpj.progressbar.demo.model.SelectionViewModel;
import com.zpj.progressbar.demo.utils.FilesViewAdapter;
import com.zpj.progressbar.demo.utils.GsonUtil;
import com.zpj.progressbar.demo.utils.HttpUrl;
import com.zpj.progressbar.demo.utils.Installation;
import com.zpj.progressbar.demo.utils.OKHttp3Util;
import com.zpj.progressbar.demo.vo.PeerResultVO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements FilesViewAdapter.FilesAdapterListener{

    private String TAG = MainActivity.class.getSimpleName();

    private ZProgressBar progressBar;

    private String ipStr;

    private static final String BROADCAST_ACTION_DISC = "com.cunw.cloud.peer.download.broadcast";

    private FilesFragment filesFragment;

    private Button restartBtn;

    private ActionMode actionMode;

    private LiteViewModel liteViewModel;

    private long lastClickTime = 0;
    private SelectionViewModel selectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        ipStr = intent.getStringExtra("ipStr");


        progressBar = findViewById(R.id.progress_test);
        progressBar.setProgress(0);
        progressBar.setProgressBarColor(R.color.color_CCCCCC);

        restartBtn = findViewById(R.id.btn_restart);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(0);
                DownloadDTO dto = new DownloadDTO();
                dto.setMachineCode(Installation.id());
//                dto.setFilePath("http://blog.punengshuo.com/haha4.jpg");
                dto.setFilePath(selectionViewModel.getUri().getValue());

                 download(dto);
            }
        });

        restartBtn.setEnabled(false);
        selectionViewModel = new ViewModelProvider(this).get(SelectionViewModel.class);
        selectionViewModel.getUri().observe(this, (uri) -> {
            if(uri != null){
                LogUtils.error(TAG, "haha");
                LogUtils.error(TAG, uri);
                restartBtn.setEnabled(true);
            }else{
                restartBtn.setEnabled(false);
            }
        });


        // 注册广播接收
        BroadcastReceiver receiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION_DISC); // 只有持有相同的action的接受者才能接收此广播
        registerReceiver(receiveBroadCast, filter);

        requestMyPermissions();

        liteViewModel = new ViewModelProvider(this).get(LiteViewModel.class);

        recentFile();


        filesFragment = new FilesFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, filesFragment)
                .commit();

    }

    private void download(  DownloadDTO dto) {
//        final Resources resources = getResources();
//        final SharedPreferencesUtil sp = new SharedPreferencesUtil(activity);
        String token = "test-token";

        Log.i(TAG, "enter here " );

        String json = GsonUtil.GsonString(dto);
        Log.i(TAG, ipStr + HttpUrl.DOWNLOAD_REQUEST );
        OKHttp3Util.postAsyn(ipStr + HttpUrl.DOWNLOAD_REQUEST, token, json, new OKHttp3Util.ResultCallback<JsonElement>() {
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


    @Override
    public void invokeAction(@NonNull PeerResultVO.TransferFileVO proxy, @NonNull View view) {

        if (SystemClock.elapsedRealtime() - lastClickTime < Settings.CLICK_OFFSET) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();

    }


    @Override
    public void onClick(@NonNull PeerResultVO.TransferFileVO proxy) {

        if (SystemClock.elapsedRealtime() - lastClickTime < 500) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();


    }
    @Override
    public void invokePauseAction(@NonNull PeerResultVO.TransferFileVO proxy) {

        if (SystemClock.elapsedRealtime() - lastClickTime < Settings.CLICK_OFFSET) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();




    }

    private void showFragment(Fragment fragment) {

        releaseActionMode();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(Content.NAME)
                .setReorderingAllowed(true)
                .commit();


    }

    private void releaseActionMode() {
        try {
            if (actionMode != null) {
                actionMode.finish();
                actionMode = null;
            }
            getCurrentAccessFragment().releaseActionMode();
        } catch (Throwable throwable) {
            LogUtils.error(TAG, throwable);
        }
    }

    public AccessFragment getCurrentAccessFragment() {
        return (AccessFragment) getCurrentFragment();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }


    private void recentFile() {
//        final Resources resources = getResources();
//        final SharedPreferencesUtil sp = new SharedPreferencesUtil(activity);
        String token = "test-token";

        Log.i(TAG, "enter here " );

        Log.i(TAG, ipStr + HttpUrl.RECENT_FILES );
        OKHttp3Util.getAsyn(ipStr + HttpUrl.RECENT_FILES +"?pageNum=1&pageSize=5", token, new OKHttp3Util.ResultCallback<JsonElement>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e(TAG, "请求失败=" + e.toString());
            }

            @Override
            public void onResponse(JsonElement response) {
                Log.e(TAG,"成功--->" + response.toString());

                PeerResultVO result = GsonUtil.GsonToBean(response.toString(), PeerResultVO.class);
                liteViewModel.getLiveDataFiles().postValue(result.getData().getList());

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