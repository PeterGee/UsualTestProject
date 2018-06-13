package net.gepergee.usualtestproject.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * path终结版
 * @author petergee
 * @date 2018/6/11
 */
public class FinalPathView extends View {
    private  int mWidth,mHeight;
    private Paint mPaint;

    public FinalPathView(Context context) {
        this(context,null);
    }

    public FinalPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();

        path1.addCircle(0, 0, 200, Path.Direction.CW);
        path2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path3.addCircle(0, -100, 100, Path.Direction.CW);
        path4.addCircle(0, 100, 100, Path.Direction.CCW);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            path1.op(path2, Path.Op.DIFFERENCE);
            path1.op(path3, Path.Op.UNION);
            path1.op(path4, Path.Op.DIFFERENCE);
        }

        canvas.drawPath(path1, mPaint);
    }
}
