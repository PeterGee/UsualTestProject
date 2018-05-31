package net.gepergee.usualtestproject.notification;

import android.app.Activity;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;


/**
 * @author petergee
 * @date 2018/3/20
 */

public class NotificationTestActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        TextView tvShowNotification=findViewById(R.id.show_notification);
        tvShowNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showNotification();
    }

    private void showNotification() {
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        Bitmap myBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.bg_001);
        Notification notification = new NotificationCompat.Builder(this,"1")
                .setSmallIcon(R.mipmap.bg_001)
                .setContentTitle("通知")
                .setContentText("notification detail ")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.DEFAULT_VIBRATE)
                .build();
        notificationManagerCompat.notify(1,notification);

    }
}
