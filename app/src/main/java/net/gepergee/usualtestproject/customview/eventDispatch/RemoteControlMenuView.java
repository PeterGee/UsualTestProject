package net.gepergee.usualtestproject.customview.eventDispatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 远程控制按钮
 *
 * @author petergee
 * @date 2018/6/14
 */
public class RemoteControlMenuView extends View {

    private Path upPath;
    private Path downPath;
    private Path leftPath;
    private Path rightPath;
    private Path centerPath;
    private Region upRegion;
    private Region downRegion;
    private Region leftRegion;
    private Region rightRegion;
    private Region centerRegion;
    private Matrix mMatrix;
    private int mWidth, mHeight;

    int mDefaultColor = 0xFF4E5268;
    int mTouchedColor = 0xFFDF9C81;

    // 位置Flag标记
    int CENTER = 0;
    int UP = 1;
    int RIGHT = 2;
    int DOWN = 3;
    int LEFT = 4;
    int touchFlag = -1;
    int currentFlag = -1;
    private MenuListener menuListener;
    private Paint mPaint;


    public RemoteControlMenuView(Context context) {
        this(context, null);
    }

    public RemoteControlMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化画笔、path、region
     */
    private void init() {
        // 上、下、左、右、中心路径
        upPath = new Path();
        downPath = new Path();
        leftPath = new Path();
        rightPath = new Path();
        centerPath = new Path();

        // 上、下、左、右、中心 region
        upRegion = new Region();
        downRegion = new Region();
        leftRegion = new Region();
        rightRegion = new Region();
        centerRegion = new Region();

        // 设置画笔
        mPaint = new Paint();
        mPaint.setColor(mDefaultColor);
        mPaint.setAntiAlias(true);
        // 设置matrix
        mMatrix = new Matrix();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置view的大小
        mWidth = w;
        mHeight = h;
        Region globalRegion = new Region(-w, -h, w, h);
        int minWidth = Math.min(w, h);
        minWidth *= 0.8;

        //创建两个圆、半径分别为宽度的二分之一、四分之一
        int r1 = minWidth / 2;
        RectF rectOne = new RectF(-r1, -r1, r1, r1);
        int r2 = minWidth / 4;
        RectF rectTwo = new RectF(-r2, -r2, r2, r2);

        // 创建两个弧形角度
        float bigSweepAngel = 84;
        float smallSweepAngel = -80;

        // path上添加路径
        // 中心圆
        centerPath.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        centerRegion.setPath(centerPath, globalRegion);

        // 右
        rightPath.addArc(rectOne, -40, bigSweepAngel);
        rightPath.arcTo(rectTwo, 40, smallSweepAngel);
        rightPath.close();
        rightRegion.setPath(rightPath, globalRegion);

        // 下
        downPath.addArc(rectOne, 50, bigSweepAngel);
        downPath.arcTo(rectTwo, 130, smallSweepAngel);
        downPath.close();
        downRegion.setPath(downPath, globalRegion);

        // 左
        leftPath.addArc(rectOne, 140, bigSweepAngel);
        leftPath.arcTo(rectTwo, 220, smallSweepAngel);
        leftPath.close();
        leftRegion.setPath(leftPath, globalRegion);

        // 上
        upPath.addArc(rectOne, 230, bigSweepAngel);
        upPath.arcTo(rectTwo, 310, smallSweepAngel);
        upPath.close();
        upRegion.setPath(upPath, globalRegion);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] pts = new float[2];
        pts[0] = event.getRawX();
        pts[1] = event.getRawY();

        // 测量点
        mMatrix.mapPoints(pts);

        int x = (int) pts[0];
        int y = (int) pts[1];

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchFlag(x, y);
                Log.e("tag","down  touchFlag== "+touchFlag);
                currentFlag = touchFlag;
                break;
            case MotionEvent.ACTION_MOVE:
                currentFlag = getTouchFlag(x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchFlag(x, y);
                // 设置回调
                if (currentFlag == touchFlag && currentFlag != -1 && menuListener != null) {
                    if (currentFlag == CENTER) {
                        menuListener.onCenterClicked();
                    } else if (currentFlag == LEFT) {
                        menuListener.onLeftClicked();
                    } else if (currentFlag == UP) {
                        menuListener.onTopClicked();
                    } else if (currentFlag == RIGHT) {
                        menuListener.onRightClicked();
                    } else if (currentFlag == DOWN) {
                        menuListener.onDownClicked();
                    }
                }
                // 复位flag
                touchFlag = currentFlag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = currentFlag = -1;
                break;

        }
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 移动canvas
        canvas.translate(mWidth / 2, mHeight / 2);
        // 获取测量matrix
        if (mMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMatrix);
        }
        // 绘制默认颜色
        canvas.drawPath(centerPath, mPaint);
        canvas.drawPath(leftPath, mPaint);
        canvas.drawPath(upPath, mPaint);
        canvas.drawPath(rightPath, mPaint);
        canvas.drawPath(downPath, mPaint);

        // 绘制触摸区域颜色
        mPaint.setColor(mTouchedColor);
        if (currentFlag == CENTER) {
            canvas.drawPath(centerPath, mPaint);
        } else if (currentFlag == LEFT) {
            canvas.drawPath(leftPath, mPaint);
        } else if (currentFlag == UP) {
            canvas.drawPath(upPath, mPaint);
        } else if (currentFlag == RIGHT) {
            canvas.drawPath(rightPath, mPaint);
        } else if (currentFlag == DOWN) {
            canvas.drawPath(downPath, mPaint);
        }
        // 复位颜色
        mPaint.setColor(mDefaultColor);

    }

    /**
     * 根据位置设置flag
     *
     * @param pt2
     * @param pt1
     * @return
     */
    private int getTouchFlag(int pt1, int pt2) {
        if (centerRegion.contains(pt1, pt2)) {
            return 0;
        } else if (upRegion.contains(pt1, pt2)) {
            return 1;
        } else if (rightRegion.contains(pt1, pt2)) {
            return 2;
        } else if (downRegion.contains(pt1, pt2)) {
            return 3;
        } else if (leftRegion.contains(pt1, pt2)) {
            return 4;
        }
        return -1;
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public interface MenuListener {
        void onLeftClicked();

        void onRightClicked();

        void onTopClicked();

        void onDownClicked();

        void onCenterClicked();
    }
}
