package net.gepergee.usualtestproject.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author geqipeng
 * @date 2017/11/27
 */

public class CustomMarqueeView extends TextView implements Runnable {

    private int currentScrollX;// 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;

    public CustomMarqueeView(Context context) {
        super(context);
    }

    public CustomMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }

    /**
     * 获取文字宽度
     */
    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
    }

    @Override
    public void run() {
        currentScrollX += 2;// 滚动速度
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
        if (getScrollX() >= (this.getWidth())) {
            scrollTo(-textWidth/2, 0);
            currentScrollX = -textWidth/2;
        }
        postDelayed(this, 10);
    }

    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        startScroll();
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();

    }
}
