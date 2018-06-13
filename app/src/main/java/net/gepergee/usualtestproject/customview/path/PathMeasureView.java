package net.gepergee.usualtestproject.customview.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.gepergee.usualtestproject.R;

/**
 * PathMeasure
 *
 * @author petergee
 * @date 2018/6/12
 */
public class PathMeasureView extends View {

    private final String TAG = getClass().getSimpleName();
    private int mWidth, mHeight;
    private Paint mPaint;
    private float[] pos;
    private float[] tan;
    private Bitmap bitmap;
    private Matrix matrix;
    private float currentValue = 0;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
         bitmapInit();


    }

    private void bitmapInit() {
        //  pos 记录当前位置数据；
        //  tan 记录当前位置tan数据
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 缩小为原来的一半大小
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_001, options);
        matrix = new Matrix();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

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
        canvas.translate(mWidth / 2, mHeight / 2);
        // pathNormalUseOne(canvas);
        // pathSegment(canvas);
        // nextContour(canvas);

        // drawCircle
         drawArrowView(canvas);


    }

    /**
     * 绘制箭头效果的圆
     *
     * @param canvas
     */
    private void drawArrowView(Canvas canvas) {
        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(path, true);
        currentValue += 0.005;
        // 限制currentValue取值位于0-1之间
        if (currentValue>=1){
            currentValue=0;
        }
        // postTanMethod(pathMeasure);
        getMatrixMethod(pathMeasure);


        // 绘制bitmap
        canvas.drawBitmap(bitmap,matrix,mPaint);
        // 刷新
        invalidate();

    }

    /**
     * 获取matrix
     * @param pathMeasure
     */
    private void getMatrixMethod(PathMeasure pathMeasure) {
        // 获取当前位置的matrix
        pathMeasure.getMatrix(pathMeasure.getLength()*currentValue,matrix,
                PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(-bitmap.getWidth()/2,-bitmap.getHeight()/2);
    }

    /**
     * 获取path 上的tan值
     * @param pathMeasure
     */
    private void postTanMethod(PathMeasure pathMeasure) {
        // 获取path上的值
        pathMeasure.getPosTan(pathMeasure.getLength()*currentValue,pos,tan);
        matrix.reset();

        // 获取角度
        float degrees= (float) (Math.atan2(tan[1],tan[0])*180.0/Math.PI);
        // 旋转图片
        matrix.postRotate(degrees,bitmap.getWidth()/2,bitmap.getHeight()/2);
        // 将图片中心调整到与当前位置中心重合
        matrix.postTranslate(pos[0]-bitmap.getWidth()/2,pos[1]-bitmap.getHeight()/2);
        // 绘制path
        //canvas.drawPath(path,mPaint);
    }

    /**
     * 跳转到下一个路径
     *
     * @param canvas
     */
    private void nextContour(Canvas canvas) {
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        path.addRect(-100, -100, 100, 100, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);
        Log.e(TAG, "firstLength= " + measure.getLength());
        // 跳转到下一个路径
        measure.nextContour();
        Log.e(TAG, "secondLength= " + measure.getLength());
        canvas.drawPath(path, mPaint);
    }

    /**
     * 截取path 部分路径
     *
     * @param canvas
     */
    private void pathSegment(Canvas canvas) {
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path, false);
        Path dstPath = new Path();
        measure.getSegment(200, 600, dstPath, true);
        mPaint.setColor(Color.RED);
        canvas.drawPath(dstPath, mPaint);
    }

    private void pathNormalUseOne(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(200, 0);
        path.lineTo(200, 200);
        path.lineTo(0, 200);
        path.close();

        // 测量长度
        PathMeasure pathMeasure1 = new PathMeasure(path, false);
        PathMeasure pathMeasure2 = new PathMeasure(path, true);

        // setPath
        // pathMeasure1.setPath(path,false);

        Log.e(TAG, "pathMeasure1= " + pathMeasure1.getLength());
        Log.e(TAG, "pathMeasure2= " + pathMeasure2.getLength());
        /*for (int i=0;i<5;i++){
            canvas.translate(100,0);
        }*/
        canvas.drawPath(path, mPaint);
    }
}
