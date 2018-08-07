package net.gepergee.usualtestproject.customview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 弹性ScrollView
 *
 * @author petergee
 * @date 2018/8/3
 */
public class ElasticScrollView extends ScrollView {
    private ViewGroup innerView;
    private float y;
    private Rect normalRect = new Rect();

    public ElasticScrollView(Context context) {
        this(context, null);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 获取innerView
        if (getChildCount() > 0) {
            innerView = (ViewGroup) getChildAt(0);
        }
        // 取消5.0效果
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commOnTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取y坐标
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float preY = y;
                float nowY = ev.getY();
                int moveY = (int) Math.sqrt(Math.abs(nowY - preY) * 2);
                // 随着滑动更新y坐标
                y = nowY;
                // 设置layout
                if (isNeedMove()) {
                    if (normalRect.isEmpty()) {
                        // 保存当前位置
                        normalRect.set(innerView.getLeft(), innerView.getTop(), innerView.getRight(), innerView.getBottom());
                    }
                    if (nowY > preY) {
                        innerView.layout(innerView.getLeft(), innerView.getTop() + moveY, innerView.getRight(), innerView.getBottom() + moveY);
                    } else if (nowY < preY) {
                        innerView.layout(innerView.getLeft(), innerView.getTop() - moveY, innerView.getRight(), innerView.getBottom() - moveY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // 播放动画
                if (isNeedAnimation()) {
                    animation();
                }
                break;
            default:
                break;
        }
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = innerView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normalRect.isEmpty();
    }

    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, innerView.getTop(), 0);
        ta.setDuration(300);
        Interpolator in = new DecelerateInterpolator();
        ta.setInterpolator(in);
        innerView.startAnimation(ta);
        // 设置回到正常的布局位置
        innerView.layout(normalRect.left, normalRect.top, normalRect.right, normalRect.bottom);
        normalRect.setEmpty();
    }

}
