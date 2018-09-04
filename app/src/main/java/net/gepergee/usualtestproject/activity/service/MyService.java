package net.gepergee.usualtestproject.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author petergee
 * @date 2018/9/3
 */
public class MyService extends Service {

   private MyBinder myBinder=new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("tag", "Service Started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("tag", "Service Destroyed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("tag", "Service onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("tag", "Service onUnbind");
        return super.onUnbind(intent);
    }
}
class MyBinder extends Binder {
 public void printWord(){
     Log.e("tag","helloWorld");
 }
}


