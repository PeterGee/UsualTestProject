package net.gepergee.usualtestproject.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.customview.canvas.CustomPieView;
import net.gepergee.usualtestproject.customview.eventDispatch.RemoteControlMenuView;
import net.gepergee.usualtestproject.customview.matrix.Rotate3dAnimation;
import net.gepergee.usualtestproject.customview.path.ThirdOrderBezierView;
import net.gepergee.usualtestproject.customview.view.MyRadioButton;

/**
 * @author petergee
 * @date 2018/6/7
 */
public class CustomViewActivityTwo extends Activity {
    private boolean b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_two);
        Log.e("tag","onCreate");
        init();
    }

    private void init() {
        CustomPieView pieView = findViewById(R.id.custom_pie);
        pieView.setStartAngle(-90);
        float[] datas = {100, 200, 300, 200, 500};
        pieView.setData(datas);

        // 贝塞尔曲线
        final ThirdOrderBezierView bezierView=findViewById(R.id.bezier_view);
        CheckBox checkBox1=findViewById(R.id.cb1);
        CheckBox checkBox2=findViewById(R.id.cb2);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bezierView.setCotrolMode(1);
                }else {
                    bezierView.setCotrolMode(2);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bezierView.setCotrolMode(2);
                }else {
                    bezierView.setCotrolMode(1);
                }
            }
        });


        ImageView imageView=findViewById(R.id.iv_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int heightC=v.getHeight()/2;
                int widthC=v.getWidth()/2;

                Rotate3dAnimation animation=new Rotate3dAnimation(getApplicationContext(),0,
                        180,widthC,heightC,0f,true);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                animation.setInterpolator(new LinearInterpolator());
                v.startAnimation(animation);
            }
        });

        // radioButton测试用

        MyRadioButton rbOne=findViewById(R.id.rb_one);
        rbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setChecked(!b);
                b=!b;
            }
        });

        RemoteControlMenuView remoteControlMenuView=findViewById(R.id.remote_view);
        remoteControlMenuView.setMenuListener(new RemoteControlMenuView.MenuListener() {
            @Override
            public void onLeftClicked() {
                Log.e("tag","left");
            }

            @Override
            public void onRightClicked() {
                Log.e("tag","right");
            }

            @Override
            public void onTopClicked() {
                Log.e("tag","top");
            }

            @Override
            public void onDownClicked() {
                Log.e("tag","down");
            }

            @Override
            public void onCenterClicked() {
                Log.e("tag","center");
            }
        });

    }

}
