package net.gepergee.usualtestproject.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.gepergee.usualtestproject.R;



/**
 * 自定义View第一篇
 * @author petergee
 * @date 2018/4/12
 */

public class CustomViewOne extends View {

    private Paint mPaint;
    private Bitmap bitmap;
    private Bitmap bitmap2;

    public CustomViewOne(Context context) {
        this(context,null);
    }

    public CustomViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化paint
     */
    private void init() {
        mPaint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_001);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_002);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //colorMethod(canvas);
        effectMethod(canvas);

    }


    /**
     * 颜色
     * @param canvas
     */
    private void colorMethod(Canvas canvas) {
        //bitmapShaderMethod(canvas);
        // colorFilterMethod(canvas);
    }

    /**
     * 设置着色器
     * @param canvas
     */
    private void bitmapShaderMethod(Canvas canvas) {
        Log.e("tag","customViewOne");

        //模板
        Shader mShader= new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Shader mShader2= new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //镜像
        //Shader mShader= new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);

        //复制
        //Shader mShader= new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        //复合着色器
        Shader shader=new ComposeShader(mShader,mShader2, PorterDuff.Mode.OVERLAY);
        mPaint.setShader(mShader);
        canvas.drawCircle(300,300,300,mPaint);
    }

    /**
     * 颜色过滤器
     * @param canvas
     */
    private void colorFilterMethod(Canvas canvas) {
        ColorFilter colorFilter=new LightingColorFilter(0x00ffff,0x000000);
        mPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap,300,300,mPaint);
    }

    /**
     * 效果
     * @param canvas
     */
    private void effectMethod(Canvas canvas) {
        normalEffect(canvas);
    }

    /**
     * 普通效果
     * @param canvas
     */
    private void normalEffect(Canvas canvas) {
        //抗锯齿
       // mPaint.setAntiAlias(true);
        // mPaint.setStrokeWidth(10);
        // canvas.drawCircle(300,300,200,mPaint);

        //设置画笔Style

        //填充效果
        // mPaint.setStyle(Paint.Style.FILL);
        // canvas.drawCircle(300,300,200,mPaint);

        //描边效果
        // mPaint.setColor(Color.RED);
        // mPaint.setStyle(Paint.Style.STROKE);
        // canvas.drawCircle(300,300,200,mPaint);

        //描边+填充
        // mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // canvas.drawCircle(300,300,200,mPaint);

        /**
         * 设置线头形状
         * BUTT 平头、ROUND 圆头、SQUARE 方头
         */
        // mPaint.setStrokeCap(Paint.Cap.BUTT);

        // 圆头
        // mPaint.setStrokeCap(Paint.Cap.ROUND);

        // 方头
        // mPaint.setStrokeCap(Paint.Cap.SQUARE);
        //canvas.drawLine(100,100,300,300,mPaint);

        /**
         *  设置线头拐角形状
         * MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER
         */
        // mPaint.setStrokeJoin(Paint.Join.BEVEL);

        /**
         *  setStrokeMiter(float miter)
         *  设置延长线最大值
         *
         *  是对setStrokeJoin的一个补充
         */


        /**
         * 设置色彩抖动
         */
        // mPaint.setDither(false);
        // canvas.drawBitmap(bitmap,200,200,mPaint);

        /**
         * 设置双线性过滤
         */
        // mPaint.setFilterBitmap(true);

        /**
         * 设置path效果
         *
         */
        //虚线效果
        // PathEffect pathEffect=new DashPathEffect(new float[]{10,5},10);

        //圆角效果
        // PathEffect cornerEffect=new CornerPathEffect(200);

        // mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setPathEffect(pathEffect);
       // mPaint.setPathEffect(cornerEffect);
        //canvas.drawCircle(300,300,300,mPaint);


        /**
         * 组合效果
         * SumPathEffect
         */

        // PathEffect sumPathEffect=new SumPathEffect(pathEffect,cornerEffect);

        /**
         * 组合效果
         * ComposePathEffect
         */

        // PathEffect composePathEffect=new ComposePathEffect(pathEffect,cornerEffect);

        /**
         * 阴影效果
         * setShadowLayer
         */

        // mPaint.setShadowLayer(10,0,0,Color.RED);
        // mPaint.setTextSize(20);
        // canvas.drawText("Hello",100,100,mPaint);

        /**
         * maskFilter
         * 遮罩效果
         *
         */

        /**
         *  Blur  四种模糊效果
         *  NORMAL: 内外都模糊绘制
         *  SOLID: 内部正常绘制，外部模糊
         *  INNER: 内部模糊，外部不绘制
         *  OUTER: 内部不绘制，外部模糊
         */
        // mPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL));
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{0,1,1},0.2f,8,15));
        canvas.drawBitmap(bitmap,200,200,mPaint);


        // path的使用
        // mPaint.getFillPath()  获取文字填充的路径

        // drawText 绘制文字
        // reset 重置paint的所有属性
        // mPaint.setFlags();  批量设置标签
    }

}
