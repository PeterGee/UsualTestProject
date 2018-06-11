package net.gepergee.usualtestproject.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.activity.customview.canvas.CustomProgressOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author petergee
 * @date 2018/5/31
 */
public class CustomViewActivity extends Activity {
    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    private CustomProgressOne progressOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_one);
        initView();
    }

    private void initView() {
        progressOne = findViewById(R.id.tasks_view);
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new ProgressRunnable());

    }

    class ProgressRunnable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                progressOne.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
