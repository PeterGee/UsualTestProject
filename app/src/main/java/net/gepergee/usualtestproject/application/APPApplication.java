package net.gepergee.usualtestproject.application;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;


public class APPApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initX5WebView();
    }

    private void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
            }
            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}


