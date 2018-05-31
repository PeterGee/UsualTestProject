package net.gepergee.usualtestproject.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;


/**
 * @author geqipeng
 * @date 2017/11/2
 */

public class AnimActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isExpanded=true;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
       // ImageView imgIcon=findViewById(R.id.img_icon);
       // tvText = findViewById(R.id.tv_text);
       // imgIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        createAnimator(tvText,tvText.getWidth(),0);
        if (isExpanded){
            close(tvText);
            isExpanded=false;
        }else {
            open(tvText);
            isExpanded=true;
        }
    }

    /**
     * 打开通知
     */
    private void open(View view) {
        //获取屏幕密度
        float density = getResources().getDisplayMetrics().density;
        //dp to px   200dp
        int textWidth = (int) (density * 200 + 0.5);
        view.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator=createAnimator(view,0,textWidth);
        valueAnimator.start();

    }

    /**
     * 关闭通知
     */
    private void close(final View view) {
        int width=view.getWidth();
        ValueAnimator valueAnimator=createAnimator(view,width,0);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        valueAnimator.start();
    }

    /**
     * 设置动画
     * @param view
     * @param start
     * @param end
     * @return
     */
    private ValueAnimator createAnimator(final View view, int start, int end) {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(start,end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value=(Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
                layoutParams.width=value;
                view.setLayoutParams(layoutParams);
            }
        });
        return valueAnimator;
    }
}
