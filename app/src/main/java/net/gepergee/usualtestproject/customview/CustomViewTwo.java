package net.gepergee.usualtestproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * 自定义View第二篇
 *
 * @author petergee
 * @date 2018/4/13
 */

public class CustomViewTwo extends View {

    private Paint mPaint;

    public CustomViewTwo(Context context) {
        this(context, null);
    }

    public CustomViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTextMethod(canvas);
    }

    private void drawTextMethod(Canvas canvas) {
        measureTextMethod(canvas);
        // staticLayoutMethod(canvas);
        // drawNormalText(canvas);



    }

    /**
     * 测量文字尺寸
     *
     * @param canvas
     */
    private void measureTextMethod(Canvas canvas) {
        String text = "hello world this is my custom view test";

        // getTextBounds(String text, int start, int end, Rect bounds) 获取文字的显示范围
        // Rect rect=new Rect();
        // mPaint.getTextBounds(text,0,text.length(),rect);

        canvas.drawText(text, 100, 100, mPaint);
        float textWidth = mPaint.measureText(text);
        Log.e("tag", "text width is: " + textWidth);

        // getTextWidths(String text, float[] widths) 获取每个字符的宽度
        int width = mPaint.getTextWidths(text, new float[text.length()]);
        Log.e("tag","width = "+width);


    }

    /**
     * staticLayout 的使用
     *
     * @param canvas
     */
    private void staticLayoutMethod(Canvas canvas) {
        String text = "hello world this is my custom view test";
        String text2 = "hello \nworld \nthis is my custom view test";
        TextPaint textPaint = new TextPaint(mPaint);
        /**
         * staticLayout 具有自动换行和手动通过\n方式换行两种功能
         */
        StaticLayout staticLayout = new StaticLayout(text, textPaint, 500, Layout.Alignment.ALIGN_NORMAL,
                1, 0, true);
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint, 500, Layout.Alignment.ALIGN_NORMAL,
                1, 0, true);
        canvas.save();
        canvas.translate(500, 500);
        staticLayout.draw(canvas);
        canvas.translate(0, 200);
        staticLayout2.draw(canvas);
        canvas.restore();
    }

    /**
     * 绘制普通文字
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawNormalText(Canvas canvas) {

        /**
         * 设置字体
         * mPaint.setTypeface()
         */
        mPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText("拂尘:扫去所有烦恼", 300, 300, mPaint);

        mPaint.setTypeface(Typeface.MONOSPACE);
        canvas.drawText("拂尘:扫去所有烦恼", 300, 400, mPaint);
        // canvas.clipRect()   帮助系统识别可见区域，只有在该区域内才会绘制

       // canvas.quickReject()   来判断是否没和某个矩形相交，从而跳过那些非矩形区域内的绘制操作。

        // setFakeBoldText(boolean fakeBoldText) 设置是否显示伪粗体
        // mPaint.setFakeBoldText(true);
        // canvas.drawText("Coding Change The World",300,500,mPaint);

        // setStrikeThruText(boolean strikeThruText)  设置显示删除线
        // mPaint.setStrikeThruText(true);
        // canvas.drawText("Coding Change The World",300,600,mPaint);

        // setUnderlineText(boolean underlineText)    设置显示下划线
        // mPaint.setUnderlineText(true);
        // canvas.drawText("Coding Change The World",300,700,mPaint);

        // setTextSkewX(float skewX)   设置文字倾斜度
        mPaint.setTextSkewX(-0.3f);
        canvas.drawText("Coding Change The World", 300, 800, mPaint);

        // setTextScaleX(float scaleX)  设置文字X轴缩放
        mPaint.setTextScaleX(1.5f);
        canvas.drawText("Coding Change The World", 300, 900, mPaint);

        // setLetterSpacing(float letterSpacing)  设置字符间距
        mPaint.setLetterSpacing(0.5f);
        canvas.drawText("Coding Change The World", 300, 900, mPaint);

        // setTextAlign(Paint.Align align)  设置文字对齐方式
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Coding Change The World", 100, 600, mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Coding Change The World", 100, 1000, mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Coding Change The World", 100, 1100, mPaint);

        // setTextLocale(Locale locale) / setTextLocales(LocaleList locales)
        // 设置文字地区

        mPaint.setTextLocale(Locale.CANADA);
        canvas.drawText("设置文字显示的地区", 100, 1200, mPaint);

        //  setSubpixelText(boolean subpixelText) 设置像素级抗锯齿


    }

}
