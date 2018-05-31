package net.gepergee.usualtestproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @author geqipeng
 * @date 2017/11/27
 */

public class CustomTextView extends TextView {


    /**
     * 界面刷新时间(ms)
     */
    public static final int INVALIDATE_TIME = 15;
    /**
     * 每次移动的像素点(px)
     */
    public static final int INVALIDATE_STEP = 1;
    /**
     * 一次移动完成后等待的时间(ms)
     */
    public static final int WAIT_TIME = 1500;
    /**
     * 滚动文字前后的间隔
     */
    private String space = "       ";
    private String drawingText = "";
    private TextPaint paint;
    public boolean exitFlag;
    private float textWidth;
    private String mText;
    /***
     * 设置移动的起点默认从x轴0点开始
     */
    private int posX = 0;
    private float posY;
    private int width;
    private RectF rf;
    private Context mContext;
    /**
     * 设置标记防止多次屏幕变化影响移动速度
     */
    private static boolean b = true;

    private Handler mHandler = new Handler();

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
    }


    private void initView() {
        int textSize = getMatrixTextSize();
        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        rf = new RectF(0, 0, 0, 0);

    }

    /**
     * 根据屏幕尺寸设置字体大小
     *
     * @return
     */
    private int getMatrixTextSize() {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;

        //以分辨率为720*1280准，计算宽高比值
        float ratioWidth = (float) mScreenWidth / 720;
        float ratioHeight = (float) mScreenHeight / 1280;
        float ratioMetrics = Math.min(ratioWidth, ratioHeight);
        return Math.round(25 * ratioMetrics);
    }

    public void setText(String text) {
        this.mText = text;
        this.drawingText = mText;
        layoutView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        layoutView();
    }

    private void layoutView() {
        //获取控件的宽度作为矩形的宽度
        width = getWidth();
        rf.right = width;
        //获取控件的高度作为矩形的高度
        rf.bottom = getHeight();
        textWidth = paint.measureText(mText, 0, mText.length());
        posY = getTextDrawingBaseline(paint, rf);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getVisibility() != View.VISIBLE || TextUtils.isEmpty(drawingText)) {
            return;
        }
        canvas.save();
        canvas.drawText(drawingText, 0, drawingText.length(), posX, posY, paint);
        canvas.restore();
    }

    private Runnable moveRun = new Runnable() {

        @Override
        public void run() {
            //控制文本宽度大于控件宽度才进行滚动
            if (width >= textWidth) {
                return;
            }
            //绘制的全部内容为：文字+空格+文字
            drawingText = mText + space + mText;
            //左移
            posX -= INVALIDATE_STEP;
            //当移动到文字头部，等待，并刷新页面，即此处设置移动速度为1，即位于-0.5至+0.5之间时进行等待刷新
            if (posX >= -1 * INVALIDATE_STEP / 2 && posX <= INVALIDATE_STEP / 2) {
                mHandler.postDelayed(this, WAIT_TIME);
                invalidate();
                return;
            }
            //当文字和空格完全移出屏幕，x值从1开始移动
            if (posX < -1 * textWidth - paint.measureText(space, 0, space.length())) {
                posX = INVALIDATE_STEP;
            }
            invalidate();
            if (!exitFlag) {
                mHandler.postDelayed(this, INVALIDATE_TIME);
                return;
            }
            posX = 0;
        }
    };

    /**
     * 从窗口中移除时停止滚动
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b = true;
        stopMove();
    }

    /**
     * 窗口焦点变化开始滚动
     *
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        layoutView();
        if (b) {
            b = false;
            startMove();
        }

    }

    private void stopMove() {
        exitFlag = true;
        mHandler.removeCallbacksAndMessages(this);
    }

    public void startMove() {
        exitFlag = false;
        mHandler.post(moveRun);
    }


    /**
     * 获取绘制文字的baseline
     *
     * @param paint
     * @param targetRect
     * @return
     */
    public static float getTextDrawingBaseline(Paint paint, RectF targetRect) {
        if (paint == null || targetRect == null) {
            return 0;
        }
        Paint.FontMetrics fontMetric = paint.getFontMetrics();
        return targetRect.top + (targetRect.height() - fontMetric.bottom + fontMetric.top) / 2.0f - fontMetric.top;
    }
}
