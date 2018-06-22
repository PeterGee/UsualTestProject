package net.gepergee.usualtestproject.customview.multiTouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import net.gepergee.usualtestproject.R;

import static android.content.ContentValues.TAG;

/**
 * 单指拖拽控件
 *
 * @author petergee
 * @date 2018/6/15
 */
public class PointerDragView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF rect;
    private Matrix matrix;
    // canDrag tag
    private boolean canDrag = false;
    // 用于记录触摸点坐标
    PointF pointF = new PointF();

    public PointerDragView(Context context) {
        this(context, null);
    }

    public PointerDragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        // 初始化options
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 400;
        options.outHeight = 400;
        // 创建bitmap
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_001, options);
        // 创建矩形
        rect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        // 创建matrix
        matrix = new Matrix();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // 限定是第一根手指拖拽才起作用
                if (event.getActionIndex() == 0 && rect.contains((int) event.getX(), (int) event.getY())) {
                    Log.e(TAG, "down");
                    canDrag = true;
                    // 记录触摸点
                    pointF.set(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    Log.e(TAG, "move");
                    // 移动图片
                    matrix.postTranslate(event.getX() - pointF.x, event.getY() - pointF.y);
                    // 记录更新后位置
                    pointF.set(event.getX(), event.getY());
                    // 刷新矩形位置
                    rect = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    matrix.mapRect(rect);

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // 添加手指index判断，防止其他手指影响拖拽
                if (event.getActionIndex() == 0)
                    canDrag = false;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }
}
