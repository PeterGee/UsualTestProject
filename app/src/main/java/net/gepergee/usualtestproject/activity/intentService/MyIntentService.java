package net.gepergee.usualtestproject.activity.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


/**
 * intentService
 *
 * @author petergee
 * @date 2018/8/2
 */
public class MyIntentService extends IntentService {
    private int progress = 0;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(1000);
            while (true) {
                progress++;
                if (progress > 100) {
                    break;
                }
                sendBroadCast();
                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendBroadCast() {
        Intent mIntent = new Intent();
        mIntent.setAction("updateProgress");
        mIntent.putExtra("updateProgress", progress);
        getApplicationContext().sendBroadcast(mIntent);
    }
}
