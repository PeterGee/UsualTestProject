package net.gepergee.usualtestproject.activity.intentService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import net.gepergee.usualtestproject.R;

/**
 * intentService
 *
 * @author petergee
 * @date 2018/8/2
 */
public class IntentServiceActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        initBroadCastReceiver();
        initView();
    }

    private void initBroadCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateProgress");
        registerReceiver(broadcastReceiver, intentFilter);
    }


    private void initView() {
        Button btnStartService = findViewById(R.id.btn_start);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(IntentServiceActivity.this, MyIntentService.class);
                startService(intent);
            }
        });

    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "updateProgress") {
                int progress = intent.getIntExtra("updateProgress", 0);
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(progress);

                if (progress == 100) {
                    Toast.makeText(IntentServiceActivity.this, "downLoadSuccess", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}
