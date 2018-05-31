package net.gepergee.usualtestproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.bm.library.PhotoView;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.view.CustomTextView;
import java.util.Locale;

/**
 * @author geqipeng
 * @date 2017/11/27
 */

public class CustomTextActivity extends Activity {

    private TextView tvChangeLanguage;
    private boolean isChange=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_text);
        init();
    }

    private void init() {
        CustomTextView textThree=findViewById(R.id.tv_text_three);
        textThree.setTextColor(Color.BLACK);
        textThree.setText(getString(R.string.content_hint));
        // 开启前台服务
       //startForegroundService()
        PhotoView mPhotoView=findViewById(R.id.photo_view);
        mPhotoView.enable();
        findViewById(R.id.tv_anim_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomTextActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });


        tvChangeLanguage = findViewById(R.id.tv_change_language);
        tvChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage();
            }
        });



    }

    private void setLanguage() {
        /**
         * 切换韩文
         */
        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 韩语
        config.locale = Locale.KOREA;
        isChange = true;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        finish();
        startActivity(new Intent(this, CustomTextActivity.class));


    }
}
