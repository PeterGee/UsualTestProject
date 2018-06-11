package net.gepergee.usualtestproject.activity.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/** path 基本使用
 * @author petergee
 * @date 2018/6/8
 */
public class PathNormalUseOne extends View {

    private Paint mPaint;
    private int mWidth,mHeight;

    public PathNormalUseOne(Context context) {
        this(context,null);
    }

    public PathNormalUseOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
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
        Path mPath=new Path();
        canvas.translate(mWidth/2,mHeight/2);
        // linePath(canvas, mPath);

        // 添加矩形
        rectPath(canvas, mPath);
        // path 上叠加path
        // pathAdd(canvas, mPath);
        // arcAdd(canvas, mPath);

        // 常用判断方法

        // mPath.isRect()是否为矩形

        // mPath.isEmpty() 是否为空

        // 将新的path设置为当前path
        // Path path=new Path();
        // mPath.set(path);


    }

    /**
     * 添加圆弧路径
     * @param canvas
     * @param mPath
     */
    private void arcAdd(Canvas canvas, Path mPath) {
        // 旋转y轴坐标
        canvas.scale(1,-1);
        mPath.lineTo(100,100);
        RectF rect=new RectF(0,0,200,200);

        // arcTo 当最后一个点和绘制后的圆弧终点不是同一个点的时候，会将最后一个点和圆弧的终点进行连接
        // mPath.arcTo(rect,0,270);

        // addArc 当最后一个点和绘制后的圆弧终点不是同一个点的时候，不会将最后一个点和圆弧的终点进行连接
        mPath.addArc(rect,0,270);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * path上叠加path
     * @param canvas
     * @param mPath
     */
    private void pathAdd(Canvas canvas, Path mPath) {
        mPaint.setColor(Color.RED);
        mPath.reset();
        Path path=new Path();
        mPath.addRect(-100,-100,100,100, Path.Direction.CW);
        path.addCircle(0,0,100, Path.Direction.CW);
        mPath.addPath(path);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * 添加矩形
     * @param canvas
     * @param mPath
     */
    private void rectPath(Canvas canvas, Path mPath) {
        mPaint.setColor(Color.GREEN);
        mPath.reset();
        // CW:顺时针  CCW：逆时针
        mPath.addRect(-100,-100,100,100, Path.Direction.CW);

        // 设置最终点,修改最后一个点的位置会导致整个矩形图像发生变化
        mPath.setLastPoint(-100,200);


        Path dst=new Path();
        dst.addRect(-100,-100,100,100, Path.Direction.CW);
        mPath.offset(100,100,dst); // 位移到指定位置

        canvas.drawPath(mPath,mPaint);
        canvas.drawPath(dst,mPaint);


    }

    /**
     * 连线
     * @param canvas
     * @param mPath
     */
    private void linePath(Canvas canvas, Path mPath) {
        mPath.lineTo(200,200);

        // 移动上一个点至该位置
        mPath.setLastPoint(200,60);
        mPath.lineTo(200,0);

        // 关闭path
        mPath.close();
        for (int i=0;i<6;i++){
            canvas.rotate(60);
            canvas.drawPath(mPath,mPaint);
        }
    }
}
