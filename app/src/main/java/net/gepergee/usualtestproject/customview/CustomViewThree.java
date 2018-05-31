package net.gepergee.usualtestproject.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import net.gepergee.usualtestproject.R;


/**
 * 自定义View第三篇
 *
 * @author petergee
 * @date 2018/4/13
 */

public class CustomViewThree extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    public CustomViewThree(Context context) {
        this(context, null);
    }

    public CustomViewThree(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(10);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_001);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cameraChange(canvas);
        // matrixChange(canvas);
        // canvasTranslate(canvas);
        // canvasClipMethod(canvas);
    }

    /**
     * 相机变化
     * @param canvas
     */
    private void cameraChange(Canvas canvas) {
        Camera camera=new Camera();
        /*canvas.save();
        // 旋转
        camera.save();
        camera.rotateX(30);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.drawBitmap(mBitmap,100,100,mPaint);
        canvas.restore();
*/

        canvas.save();

        camera.save(); // 保存 Camera 的状态
        camera.rotateX(30); // 旋转 Camera 的三维空间
        canvas.translate(100, 100); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-100, -100); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore(); // 恢复 Camera 的状态

        canvas.drawBitmap(mBitmap, 100,100, mPaint);
        canvas.restore();

    }

    /**
     * 使用matrix进行图形变换
     *
     *  Canvas.setMatrix(matrix) 和 Canvas.concat(matrix)。
     *
     * @param canvas
     */
    private void matrixChange(Canvas canvas) {

       // Matrix.setPolyToPoly(float[] src, int srcIndex, float[] dst, int dstIndex, int pointCount) 用点对点映射的方式设置变换
        Matrix matrix = new Matrix();
        float left=10f,top=0f,right=100f,bottom=200f;
        // 原始位置
        float[] pointsSrc = new float[]{left, top, right, top, left, bottom, right, bottom};
        // 变化后效果
        float[] pointsDst = new float[]{left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};

        matrix.reset();
        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(mBitmap, 100, 100, mPaint);
        canvas.restore();
    }

    /**
     * canvas 二维变化
     * @param canvas
     */
    private void canvasTranslate(Canvas canvas) {
        canvas.save();
        // 位移
        canvas.translate(300,300);
        canvas.drawBitmap(mBitmap,100,100,mPaint);
        canvas.restore();

        // 旋转
        canvas.save();
        canvas.rotate(30);
        canvas.drawBitmap(mBitmap,200,200,mPaint);
        canvas.restore();

        // 缩放  sx sy 是横向和纵向的放缩倍数； px py 是放缩的轴心。
        canvas.save();
        canvas.scale(1.5f,1.5f);
        canvas.drawBitmap(mBitmap,300,300,mPaint);
        canvas.restore();

        // skew(float sx, float sy) 错切
        // 参数里的 sx 和 sy 是 x 方向和 y 方向的错切系数。
        canvas.save();
        canvas.skew(0,0.5f);
        canvas.drawBitmap(mBitmap,600,600,mPaint);
        canvas.restore();
    }

    /**
     * 图片裁剪
     * @param canvas
     */
    private void canvasClipMethod(Canvas canvas) {
        canvas.save();
        // 按照矩形范围进行裁剪
        canvas.clipRect(100,100,300,300);
        canvas.drawBitmap(mBitmap,100,100,mPaint);
        canvas.restore();

        Path path=new Path();
        canvas.save();
        canvas.clipPath(path);
        canvas.drawBitmap(mBitmap,200,200,mPaint);
        canvas.restore();
    }
    

}
