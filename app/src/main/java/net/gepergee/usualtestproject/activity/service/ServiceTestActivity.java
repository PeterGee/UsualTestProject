package net.gepergee.usualtestproject.activity.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.gepergee.aidlservice.IMyAidlInterface;
import net.gepergee.usualtestproject.R;

/**
 * @author petergee
 * @date 2018/9/3
 */
public class ServiceTestActivity extends Activity implements View.OnClickListener {
    private boolean isBinded;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        initView();
    }

    private void initView() {
        Button btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(this);
        Button btnStopService = findViewById(R.id.btn_stop_service);
        btnStopService.setOnClickListener(this);
        Button btnBindService = findViewById(R.id.btn_bind__service);
        btnBindService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        // intent.setClass(this, MyService.class);
        switch (v.getId()) {
            case R.id.btn_start_service:
                startService(intent);
                break;
            case R.id.btn_stop_service:
                stopService(intent);
                break;
            case R.id.btn_bind__service:
                intent.setAction("net.gepergee.aidlservice.AidlService");
                intent.setPackage("net.gepergee.aidlservice");
                bindService(intent, connection, BIND_AUTO_CREATE);
                isBinded=true;
                break;
            default:
                break;

        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // MyBinder binder = (MyBinder) service;
            // binder.printWord();
            IMyAidlInterface stub=  IMyAidlInterface.Stub.asInterface(service);
            if (stub==null){
                return;
            }
            try {
                String s=stub.setName("PeterGee");
                Log.e("tag","name set is "+s);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBinded){
            unbindService(connection);
        }
    }
}
