package net.gepergee.usualtestproject.customview.matrix;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * matrix camera
 * @author petergee
 * @date 2018/6/13
 */
public class CustomMatrixCamera extends View {

    private int mWidth,mHeight;

    public CustomMatrixCamera(Context context) {
        this(context, null);
    }

    public CustomMatrixCamera(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Paint mPaint = new Paint();
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

        // camera 移动1像素等价于view移动72像素

        Camera camera1=new Camera();
        camera1.setLocation(1,0,-8);
        Matrix matrix=new Matrix();
        camera1.getMatrix(matrix);
        Log.e("tag","matrix= "+matrix.toShortString());

        Camera camera2=new Camera();
        camera2.translate(-72,0,0);
        Matrix matrix1=new Matrix();
        camera2.getMatrix(matrix1);
        Log.e("tag","matrix2= "+matrix1.toShortString());
    }
}
