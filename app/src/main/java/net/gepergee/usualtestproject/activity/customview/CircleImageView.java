package net.gepergee.usualtestproject.activity.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.graphics.Bitmap.Config;

/**
 * 圆形imageView
 *
 * @author petergee
 * @date 2018/5/31
 */
public class CircleImageView extends ImageView {
    // 圆半径
    private int mRadius;
    private Paint mPaint;
    private Xfermode xfermode;
    //private Drawable mDrawable;
    private Bitmap mCircleBitmap;

    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 初始化画笔
        mPaint = new Paint();
        // 设置画笔XferMode
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        // 根据宽度和高度确定半径值
        mRadius = (height > width ? (width / 2) : (height / 2));

        // 存储测量后的宽度和高度
        setMeasuredDimension(mRadius * 2, mRadius * 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取src中图片
        Drawable mDrawable = getDrawable();
        // 获取圆形bitmap
        Bitmap mCbitmap = getCircleBitmap();
        // 将图片和bitmap进行叠加
        Bitmap addBitmap = addImgToBitmap(mDrawable, mCbitmap);
        // 绘制bitmap
        canvas.drawBitmap(addBitmap, 0, 0, mPaint);
    }

    /**
     * 创建一个实心圆形bitmap
     *
     * @return
     */
    private Bitmap getCircleBitmap() {
        if (mCircleBitmap == null) {
            mCircleBitmap = Bitmap.createBitmap(mRadius * 2, mRadius * 2, Config.ARGB_8888);
            Canvas canvas = new Canvas(mCircleBitmap);
            mPaint.reset();
            mPaint.setStyle(Paint.Style.FILL);
            // 绘制圆形
            // canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
            // 绘制弧形
            RectF rectF=new RectF(0,0,mRadius*2,mRadius*2);
            canvas.drawArc(rectF,-180,180,false,mPaint);
        }
        return mCircleBitmap;
    }

    private Bitmap addImgToBitmap(Drawable mDrawable, Bitmap mCbitmap) {
        // 获取drawable固有宽度
        int drawableWidth = mDrawable.getIntrinsicWidth();
        // 获取高度
        int drawableHeight = mDrawable.getIntrinsicHeight();
        // 根据高度和宽度创建bitmap
        Bitmap drawableBitmap = Bitmap.createBitmap(drawableWidth, drawableHeight, Config.ARGB_8888);
        // 根据bitmap创建canvas
        Canvas drawableCanvas = new Canvas(drawableBitmap);
        // 将drawable自动缩放到View的宽度和高度
        mDrawable.setBounds(0, 0, mRadius * 2, mRadius * 2);
        // drawable上添加canvas
        mDrawable.draw(drawableCanvas);

        // 根据XferMode绘制bitmap
        mPaint.reset();
        mPaint.setXfermode(xfermode);
        drawableCanvas.drawBitmap(mCbitmap, 0, 0, mPaint);
        //  设置完后一定将mode设置为null，否则会使用系统默认的mode为SRC_OVER
        mPaint.setXfermode(null);
        return drawableBitmap;
    }


}
