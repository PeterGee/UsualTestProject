package net.gepergee.usualtestproject.customview.eventDispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * motionEvent 事件分发机制探究
 *
 * @author petergee
 * @date 2018/6/14
 */
public class MotionEventTestMethod extends View {
    public MotionEventTestMethod(Context context) {
        this(context, null);
    }

    public MotionEventTestMethod(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            // 手指按下
            case MotionEvent.ACTION_DOWN:
                break;
            // 手指移动
            case MotionEvent.ACTION_MOVE:
                break;
            // 手指抬起
            case MotionEvent.ACTION_UP:
                break;
            // 取消
            case MotionEvent.ACTION_CANCEL:
                break;
            // 超出区域
            case MotionEvent.ACTION_OUTSIDE:
                break;
             // 手指按下（手指按下之前已有手指在屏幕上）
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
             // 手指抬起（在手指抬起后仍然有手指在屏幕上）
            case MotionEvent.ACTION_POINTER_UP:
                break;
            default:
                break;
        }

        // 试用于多点触控时获取事件类型
        // event.getActionMasked();

        // 用于获取多点触控时手指的索引值，只在 down 和 up 时有效，move 时是无效的。
        // event.getActionIndex();

        // 用于获取手指的id
        // event.getPointerId(event.getActionIndex());

        return super.onTouchEvent(event);


    }
}
