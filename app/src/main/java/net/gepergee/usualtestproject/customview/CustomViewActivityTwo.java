package net.gepergee.usualtestproject.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

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

        TextView tvClickMe=findViewById(R.id.tv_click_me);
        // 创建手势识别器
        final GestureDetector gestureDetector=new GestureDetector(this,simpleOnGestureListener);
        tvClickMe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 为监听器设置数据源
                return gestureDetector.onTouchEvent(event);
            }
        });
    }




    // 创建一个监听回调
    GestureDetector.SimpleOnGestureListener simpleOnGestureListener=new GestureDetector.SimpleOnGestureListener(){
        // 双击回调
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e("tag","doubleTab");
            return super.onDoubleTap(e);
        }

        // 单点抬起回调   在双击的第一次抬起时触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("tag","onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        // 手指按下回调
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("tag","onDown");
            return super.onDown(e);
        }

        // 手指滑翔回调
        // 参数含义： e1：手指按下时的event，e2:手指抬起时的event
        // velocityX:在 X 轴上的运动速度(像素／秒), velocityY:在 Y 轴上的运动速度(像素／秒)。

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("tag","onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // 滚动回到
        // 参数： e1：手指按下时的event，e2:手指抬起时的event
        // distanceX:x轴上滚动的距离 distanceY：y轴上滚动的距离
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("tag","onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        // 单点确认回调   双击时不会触发
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e("tag","onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        // 长按回调
        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("tag","onLongPress");
            super.onLongPress(e);
        }

        // 显示进度回调
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }
    };


}
