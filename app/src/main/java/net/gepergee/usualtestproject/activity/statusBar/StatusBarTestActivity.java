package net.gepergee.usualtestproject.activity.statusBar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import net.gepergee.usualtestproject.R;

/**
 * 沉浸式状态栏
 *
 * @author petergee
 * @date 2018/6/1
 */
public class StatusBarTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);

        // 获取decorView
        // View decorView=getWindow().getDecorView();
        // methodOne(decorView);
        // methodTwo(decorView);
        // methodThree(decorView);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 重写该方法，实现真正的沉浸式状态栏效果
        // 4.4及以上版本才支持沉浸式状态栏效果
        if (hasFocus && Build.VERSION.SDK_INT > 19) {
            View decorView = getWindow().getDecorView();
            int flag = View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flag);
        }

    }

    /**
     * 方式三
     * <p>
     * 设置透明导航栏和透明状态栏
     *
     * @param decorView
     */
    private void methodThree(View decorView) {
        int Flag = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(Flag);
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 方式二
     * 设置隐藏导航栏
     *
     * @param decorView
     */
    private void methodTwo(View decorView) {
        int Flag = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(Flag);

    }

    /**
     * 方式一
     *
     * @param decorView
     */
    private void methodOne(View decorView) {
        // 系统版本大于5.0生效
        if (Build.VERSION.SDK_INT > 21) {
            // 设置全屏选项
            int Flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            // 设置flag为fullScreen
            decorView.setSystemUiVisibility(Flag);
            // 设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
