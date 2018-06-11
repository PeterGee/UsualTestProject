package net.gepergee.usualtestproject.activity.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 三阶贝塞尔曲线
 * 两个控制点
 * @author petergee
 * @date 2018/6/11
 */
public class ThirdOrderBezierView extends View {

    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint,controlPoint2;
    // 中心点的x、y坐标
    private int centerX, centerY;
    private Paint mPaint;
    private int type;

    public ThirdOrderBezierView(Context context) {
        this(context, null);
    }

    public ThirdOrderBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        // 设置笔画宽度
        mPaint.setStrokeWidth(10);
        // 设置字体大小
        mPaint.setTextSize(60);

        // 起始点、终点、控制点
        startPoint = new PointF(0, 0);
        endPoint = new PointF(0, 0);
        controlPoint = new PointF(0, 0);
        controlPoint2 = new PointF(0, 0);
    }

    public   void setCotrolMode(int type){
        this.type=type;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 设置起点、终点、控制点坐标
        startPoint.x = centerX - 200;
        endPoint.x = centerX + 200;
        controlPoint.x = centerX;
        controlPoint2.x=centerX;

        startPoint.y = centerY;
        endPoint.y = centerY;
        controlPoint.y = centerY - 100;
        controlPoint2.y=centerY-100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 通过触摸事件设置控制点位置
        if (type==1){
            controlPoint.x=event.getX();
            controlPoint.y=event.getY();
        }else {
            controlPoint2.x = event.getX();
            controlPoint2.y = event.getY();
        }
        // 刷新
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制起点、终点、控制点
        canvas.drawPoint(startPoint.x, startPoint.y, mPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, mPaint);
        canvas.drawPoint(controlPoint.x, controlPoint.y, mPaint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, mPaint);
        // 绘制辅助线
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(startPoint.x, startPoint.y, controlPoint.x, controlPoint.y, mPaint);
        canvas.drawLine(controlPoint.x, controlPoint.y,controlPoint2.x, controlPoint2.y, mPaint);
        canvas.drawLine( endPoint.x, endPoint.y,controlPoint2.x, controlPoint2.y, mPaint);
        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        Path mPath = new Path();
        mPath.moveTo(startPoint.x, startPoint.y);
        mPath.cubicTo(controlPoint.x, controlPoint.y,controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);
        canvas.drawPath(mPath, mPaint);

    }
}
