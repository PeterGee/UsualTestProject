package net.gepergee.usualtestproject.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义饼状图
 *
 * @author petergee
 * @date 2018/6/7
 */
public class CustomPieView extends View {
    /**
     * 颜色数组
     */
    private int[] colors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    /**
     * 数据列表
     */
    private float[] datas ;
    // 角度集合
    private List<Float> angleList = new ArrayList<>();

    // 初始角度
    private int mStartAngle = 0;
    // 宽度和高度
    private int mWidth, mHeight;
    private Paint mPaint;

    public CustomPieView(Context context) {
        this(context, null);
    }

    public CustomPieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 设置数据
     */
    private void initData() {
        int sum = 0;
        for (int i = 0; i < datas.length; i++) {
            sum += datas[i];
        }
        // 将角度占比添加到集合中
        for (int i = 0; i < datas.length; i++) {
            float percent = (datas[i] / sum) * 360;
            angleList.add(percent);
        }

    }

    /**
     * 初始化变量
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 大小发生变化后保存宽度和高度
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 防止数据为空
        if (datas == null) {
            return;
        }
        // 起始角度
        float currentStartAngle = mStartAngle;
        // 设置圆心点
        canvas.translate(mWidth / 2, mHeight / 2);
        // 设置半径值
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        // 设置矩形区域
        RectF rectF = new RectF(-r, -r, r, r);
        // 开始绘制
        for (int i = 0; i < datas.length; i++) {
            // 设置颜色
            mPaint.setColor(colors[i]);
            // 开始绘制
            canvas.drawArc(rectF, currentStartAngle, angleList.get(i), true, mPaint);
            // 改变起始角度
            currentStartAngle += angleList.get(i);
        }
    }

    /**
     * 设置起始角度
     *
     * @param startAngle
     */
    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
    }

    /**
     * 设置数据
     *
     * @param datas
     */
    public void setData(float[] datas) {
        this.datas = datas;
        initData();
        invalidate();
    }
}
