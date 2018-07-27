package net.gepergee.usualtestproject.activity.widget;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import net.gepergee.usualtestproject.R;


/**
 * @author petergee
 * @date 2018/3/27
 */

public class KeyBoardAdjustTestActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_adjust);
        adjustKeyboard();
    }

    private void adjustKeyboard() {
        final ImageView imgDog=findViewById(R.id.img_dog);
        //这个可以获取键盘的高度
        imgDog.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                imgDog.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获取屏幕整体高度
                int height = imgDog.getHeight();
               Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anim);
               imgDog.startAnimation(animation);
                /*//判断键盘是否显示和消失
                boolean visible = (double) displayHight / height < 0.8;
                int statusBarHeight = 0;
                //获取键盘的输入法工具条的高度，否则设置的lyoContent的高度将会多出一点
                *//*try {
                    Class<?> c = Class.forName("com.android.internal.R$dimen");
                    Object obj = c.newInstance();
                    Field field = c.getField("status_bar_height");
                    int x = Integer.parseInt(field.get(obj).toString());
                    statusBarHeight = getApplicationContext().getResources().getDimensionPixelSize(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
                int keyboardHeight = height - displayHight - statusBarHeight;
                onSoftKeyBoardVisible(visible, keyboardHeight);*/
            }
        });
        //获取键盘的高度

    }

  /*  private void onSoftKeyBoardVisible(boolean visible, int keyboardHeight) {
        //如果键盘显示那么获取到他的高度设置lyoContent的marginBottom 这里lyoContent就是包裹EditText的那个view
        if (visible) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, keyboardHeight);
            //relBaseView.setLayoutParams(lp);
        } else {
            //消失直接设置lyoContent的marginBottom 为0让其恢复到原来的布局
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
           // relBaseView.setLayoutParams(lp);
        }

    }*/
}
