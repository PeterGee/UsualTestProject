package net.gepergee.usualtestproject.customview.normaluse;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import net.gepergee.usualtestproject.R;



/**
 * @author petergee
 * @date 2018/4/12
 */

public class MyCustomAnimActivity extends Activity implements View.OnClickListener {

    private ImageView imgCustomDog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_custom_view);
        // 发送本地广播
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("HELLO"));
        initView();
        // 注册本地广播
        registerBroadcastReceiver();
        
    }

    private void registerBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("HELLO");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void initView() {
        imgCustomDog = findViewById(R.id.img_dog_custom);
        findViewById(R.id.btn_start).setOnClickListener(this);

        // animTestMethod();
        // colorAnimTestMethod(imgCustomDog);


    }

    // 本地广播接收者
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("HELLO")) {
                Toast.makeText(MyCustomAnimActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void colorAnimTestMethod(ImageView view) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "color", 0xffff0000, 0xff00ff00);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    private void animTestMethod() {
        imgCustomDog.animate().translationX(500).setDuration(500);
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imgCustomDog, "translateX", 500);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                // keyFrameAnim(imgCustomDog);
                // propertyAnim(imgCustomDog);
                // multiAnim(imgCustomDog);
                break;
        }
    }

    /**
     * 关键帧动画
     *
     * @param imgCustomDog
     */
    private void keyFrameAnim(ImageView imgCustomDog) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
        Keyframe keyframe3 = Keyframe.ofFloat(1, 80);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(progressBar, holder);
        animator.start();


    }

    /**
     * 复合动画
     *
     * @param imgCustomDog
     */
    private void multiAnim(ImageView imgCustomDog) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imgCustomDog, "scaleX", 2);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imgCustomDog, "scaleY", 2);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator2);
        // set.playTogether(animator1,animator2);
        set.start();
    }

    /**
     * 属性动画
     *
     * @param imgCustomDog
     */
    private void propertyAnim(ImageView imgCustomDog) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 2);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 2);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 2);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imgCustomDog, holder1, holder2, holder3);
        animator.setDuration(1000);
        animator.start();
    }

}
