package net.gepergee.usualtestproject.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author geqipeng
 * @date 2017/12/20
 */

public class GradientTextView extends View {

    private Paint mPaint;


    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShadowLayer(10F, 15F, 15F, Color.GRAY);
        RectF rect = new RectF(0 , 0, 200, 200);
        canvas.drawRoundRect(rect, (float)75, (float)75, mPaint);
    }
}
