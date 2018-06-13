package net.gepergee.usualtestproject.customview.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.gepergee.usualtestproject.R;

import java.util.Arrays;
import java.util.Locale;

/**
 * matrix 矩阵变换使用
 *
 * @author petergee
 * @date 2018/6/12
 */
public class CustomMatrixView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;
    private Bitmap bitmap;
    private Matrix mMatrix;

    public CustomMatrixView(Context context) {
        this(context, null);
    }

    public CustomMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        // initMatrixBitmapChange();
        mMatrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_002);
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
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.translate(mWidth/2,mHeight/2);
        // matrixScale(src, dst);
        // mapRadius();
        // mapRect(mMatrix);
        // mapVectors(mMatrix);
        setRectToRect();
        canvas.drawBitmap(bitmap, mMatrix, mPaint);

        // getViewLocationInScreen();
        // getViewLocationInScreenByMatrix(canvas);



    }

    /**
     * 通过matrix获取view在屏幕中位置
     * @param canvas
     */
    private void getViewLocationInScreenByMatrix(Canvas canvas) {
        float[] values=new float[9];
        int[] location=new int[2];
        Matrix matrix = canvas.getMatrix();
        matrix.getValues(values);
        location[0]= (int) values[2];
        location[1]= (int) values[5];
        Log.e("tag","location= "+Arrays.toString(location));
    }

    /**
     *  获取view在屏幕中的位置
     */
    private void getViewLocationInScreen() {
        int[] location=new int[2];
        this.getLocationOnScreen(location);
        Log.e("tag","locationOnScreen= "+ Arrays.toString(location));
    }

    /**
     * matrix bitmap变换
     *
     * @param
     */
    private void initMatrixBitmapChange() {
        // bitmap变换前位置
        float[] src = new float[]{0, 0, bitmap.getWidth(), 0, bitmap.getWidth(), bitmap.getHeight(), 0, bitmap.getHeight()};
        // bitmap 变换后位置
        float[] dst = new float[]{0, 0, bitmap.getWidth(), 0, bitmap.getWidth(), bitmap.getHeight() - 100, 0, bitmap.getHeight()};
        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        mMatrix.postScale(1f, 1f);
        mMatrix.postTranslate(0, 100);
    }

    /**
     * 测量向量  与测量点类似，区别在于不受位移影响
     *
     * @param mMatrix
     */
    private void mapVectors(Matrix mMatrix) {
        float[] src = new float[]{1000, 800};
        float[] dst = new float[2];
        mMatrix.setScale(0.5f, 1f);
        mMatrix.postTranslate(10, 10);
        Log.e("tag", "dst= " + Arrays.toString(dst));  // dst= [0.0, 0.0]
        mMatrix.mapVectors(dst, src);
        Log.e("tag", "dst= " + Arrays.toString(dst));  //  dst= [500.0, 800.0]
    }

    /**
     * 测量矩形 mapRect
     *
     * @param mMatrix
     */
    private void mapRect(Matrix mMatrix) {
        RectF rect = new RectF(400, 400, 600, 600);
        mMatrix.setScale(0.5f, 1.0f);
        mMatrix.postSkew(0, 1);
        Log.e("tag", "before= " + rect.toString()); // before= RectF(400.0, 400.0, 600.0, 600.0)
        boolean b = mMatrix.mapRect(rect);
        Log.e("tag", "after= " + rect.toString());   // after= RectF(200.0, 600.0, 300.0, 900.0)
        Log.e("tag", "isRect=" + b);  // isRect=false

    }

    /**
     * 测量半径
     */
    private void mapRadius() {
        float radius = 100;
        float result = 0;
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1.0f);
        Log.e("tag", "before radius= " + radius);
        result = matrix.mapRadius(radius);
        Log.e("tag", "after radius= " + result);

    }

    private void matrixScale(float[] src, float[] dst) {
        // 创建matrix
        Matrix mMatrix = new Matrix();
        // scale缩放
        mMatrix.setScale(0.5f, 1f);
        // 打印结果
        Log.e("tag", "变换前src= " + Arrays.toString(src));
        Log.e("tag", "变换前dst= " + Arrays.toString(dst));
        // 调用mapPoints方法
        // mMatrix.mapPoints(src);

        // 使用该方法，dst会保存变化后的数据，src中数据不会发生变化
        //  mMatrix.mapPoints(dst,src);

        // void mapPoints (float[] dst, int dstIndex,float[] src, int srcIndex, int pointCount)
        /**
         *  dstIndex 目标数据起始索引  srcIndex 源数据索引  pointCount 点数
         */
        mMatrix.mapPoints(dst, 0, src, 2, 2);

        // 打印变化后数据
        Log.e("tag", "变换后src= " + Arrays.toString(src));
        Log.e("tag", "变换后dst= " + Arrays.toString(dst));
    }

    /**
     * 设置RectToRect
     */
    private void setRectToRect() {
        RectF srcRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dstRect = new RectF(0, 0, mWidth, mHeight);
        mMatrix.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER);
        // mMatrix.setRectToRect(srcRect,dstRect, Matrix.ScaleToFit.START);
        // mMatrix.setRectToRect(srcRect,dstRect, Matrix.ScaleToFit.END);
        // mMatrix.setRectToRect(srcRect,dstRect, Matrix.ScaleToFit.FILL);

    }
}
