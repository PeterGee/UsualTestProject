package net.gepergee.usualtestproject.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.designmode.singleton.LogUtils;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private ComponentName defaultComponent;
    private ComponentName componentEleven;
    private ComponentName componentTwelve;
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        LogUtils.getmLogUtils().E("init");
        defaultComponent = getComponentName();
        componentEleven = new ComponentName(getBaseContext(), "net.edaibu.testapplication.activity.changeIcon.ElevenActivity");
        componentTwelve = new ComponentName(getBaseContext(), "net.edaibu.testapplication.activity.changeIcon.TwelveActivity");
        mPackageManager = getApplicationContext().getPackageManager();


        Button btnJump11 = findViewById(R.id.btn_jump11);
        Button btnJump12 = findViewById(R.id.btn_jump12);
        btnJump11.setOnClickListener(this);
        btnJump12.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // startActivity(new Intent(this,JsCallJavaActivity.class));

        switch (view.getId()) {
            case R.id.btn_jump11:
                disableComponent(defaultComponent);
                disableComponent(componentTwelve);
                enableComponent(componentEleven);
                Log.e("tag","双11");
                break;
            case R.id.btn_jump12:
                disableComponent(defaultComponent);
                disableComponent(componentEleven);
                enableComponent(componentTwelve);
                Log.e("tag","双12");
                break;
            default:
                break;
        }

    }

    private void enableComponent(ComponentName component) {
        mPackageManager.setComponentEnabledSetting(component,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableComponent(ComponentName Component) {
        mPackageManager.setComponentEnabledSetting(Component,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.e(TAG, "hasCapture");

    }
}