package net.gepergee.usualtestproject.activity.customview.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import net.gepergee.usualtestproject.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * canvas 的基本使用
 *
 * @author petergee
 * @date 2018/6/7
 */
public class CanvasNormalUseOne extends View {

    // 画笔
    private Paint mPaint;
    // 保留画布宽度和高度
    private int mWidth, mHeight;
    // 创建pic对象实例
    private Picture mPic = new Picture();
    private Context mContext;

    public CanvasNormalUseOne(Context context) {
        this(context, null);
    }

    public CanvasNormalUseOne(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        // 开始记录
        recording();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
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
        // 设置半径值
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        canvas.save();
        // 设置矩形区域
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < 5; i++) {
            canvas.scale(0.9f, 0.9f);
            canvas.drawRect(rectF, mPaint);
        }
        // 画圆
        canvas.drawCircle(0, 0, 300, mPaint);
        canvas.drawCircle(0, 0, 400, mPaint);
        for (int i = 0; i < 360; i += 10) {
            // 画线
            canvas.drawLine(0, 300, 0, 400, mPaint);
            canvas.rotate(10);
        }
        canvas.restore();


        // drawPic(canvas);

        // 使用canvas将记录的图像进行绘制
        //canvas.drawPicture(mPic,new Rect(0,0,mPic.getWidth(),mPic.getHeight()));

        //使用drawable将记录的图片进行包装
        PictureDrawable pictureDrawable = new PictureDrawable(mPic);
        pictureDrawable.setBounds(0, 0, 100, 100);
        pictureDrawable.draw(canvas);

        // drawBitmapMethod(canvas);
        drawTextMethod(canvas);

    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawTextMethod(Canvas canvas) {
        String str = "HELLOWORLD";
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(40);
        // 方式1
        //  canvas.drawText(str, 0, 0, mPaint);

        // 方式2
        // canvas.drawText(str,0,3,0,0,mPaint);

        //方式3
        // char[] arr=str.toCharArray();
        // canvas.drawText(arr,0,4,0,0,mPaint);



    }

    /**
     * 绘制bitmap
     *
     * @param canvas
     */
    private void drawBitmapMethod(Canvas canvas) {
        //获取bitmap的几种方式
        // 1、资源文件获取
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_001);

        // 2、assets获取
        Bitmap bitmap2 = null;
        try {
            InputStream is = mContext.getAssets().open("bg_002.jpg");
            bitmap2 = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3、内存卡文件
        Bitmap bitmap3 = BitmapFactory.decodeFile("/sdcard/bg_002.jpg");

        // 绘制bitmap
        //canvas.drawBitmap(bitmap2,0,0,mPaint);
        // canvas.drawBitmap(bitmap1,new Matrix(),mPaint);

        /**
         * src 指定图片绘制区域
         * dst 指定图片在屏幕上显示的区域
         */
        Rect src = new Rect(0, 0, mPic.getWidth(), mPic.getHeight());
        Rect dst = new Rect(0, 0, 200, 200);
        canvas.drawBitmap(bitmap2, src, dst, mPaint);

    }

    /**
     * 记录
     *
     * @param
     */
    private void recording() {
        mPaint.setColor(Color.RED);
        Canvas mCanvas = mPic.beginRecording(500, 500);
        mCanvas.rotate(50);
        mCanvas.translate(100, 100);
        mCanvas.drawCircle(0, 0, 100, mPaint);
        mPic.endRecording();


    }

    /**
     * 使用picture将记录的图像绘制到canvas上
     *
     * @param canvas
     */
    private void drawPic(Canvas canvas) {
        mPic.draw(canvas);
    }

}
