package net.gepergee.usualtestproject.activity.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import net.gepergee.usualtestproject.R;


/**
 * @author geqipeng
 * @date 2017/11/13
 */

public class TranslateAnimActivity extends Activity implements View.OnClickListener {


    private LinearLayout linBg;
    private Button btnTranslate;
    private boolean isTranslated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_anim);
        initView();
    }


    private void initView() {
        linBg = findViewById(R.id.lin_bg);
        btnTranslate = findViewById(R.id.btn_translate);
        btnTranslate.setOnClickListener(this);

    }

    private void translate(final View view, final int start, final int end) {
        ValueAnimator mValueAnimator = ValueAnimator.ofInt(start, end);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                view.layout(value, view.getTop(), value + view.getWidth(), view.getBottom());
            }
        });
        mValueAnimator.start();
    }

    @Override
    public void onClick(View view) {
        int bgWidth = linBg.getWidth();
        int btnWidth = btnTranslate.getWidth();
        if (!isTranslated) {
            translate(btnTranslate, 0,  bgWidth - btnWidth);
            isTranslated = true;
        } else {
            translate(btnTranslate,  bgWidth - btnWidth, btnTranslate.getPaddingLeft());
            isTranslated = false;
        }
    }
}
