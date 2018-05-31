package net.gepergee.usualtestproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author petergee
 * @date 2018/4/17
 */

public class CustomViewFour extends View {

    private Paint mPaint;

    public CustomViewFour(Context context) {
        this(context,null);
    }

    public CustomViewFour(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomViewFour(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * 绘制子view
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 绘制前景色
     * @param canvas
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
