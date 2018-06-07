package net.gepergee.usualtestproject.activity.scroller;

/**
 * @author petergee
 * @date 2018/6/1
 */


import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 纵向ViewGroup
 * @author petergee
 * @date 2018/6/4
 */
public class VerticalScrollView extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mYDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mYMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mYLastMove;

    // 滚动的上下边界
    private int topBorder,bottomBorder;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(0, i*childView.getMeasuredHeight(), childView.getMeasuredWidth(), (i + 1) *childView.getMeasuredHeight());
            }
            // 初始化上下边界
            topBorder = getChildAt(0).getTop();
            bottomBorder = getChildAt(getChildCount() - 1).getBottom();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                  mYDown = ev.getRawY();
                  mYLastMove = mYDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getRawY();
                float diff = Math.abs(mYMove - mYDown);
                mYLastMove = mYMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mYMove = event.getRawY();
                // 标记滑动的距离
                int scrolledY = (int) (mYLastMove - mYMove);
                // 限定边界
                if (getScrollY() + scrolledY < topBorder) {
                    scrollTo(0, topBorder);
                    return true;
                } else if (getScrollY() + getHeight() + scrolledY > bottomBorder) {
                    scrollTo(0, bottomBorder - getHeight());
                    return true;
                }
                scrollBy(0, scrolledY);
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollY() + getHeight() / 2) /getHeight();
                int dy = targetIndex * getHeight() - getScrollY();
                //调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(0, getScrollY(), 0, dy);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
