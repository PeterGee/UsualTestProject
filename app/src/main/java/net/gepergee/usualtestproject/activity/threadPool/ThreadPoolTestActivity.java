package net.gepergee.usualtestproject.activity.threadPool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import net.gepergee.usualtestproject.R;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author petergee
 * @date 2018/8/7
 */
public class ThreadPoolTestActivity extends Activity implements View.OnClickListener {

    private Runnable r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        initView();
        ActivityManager manager= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }

    private void initView() {
        Button btnStartThreadPoolOne = findViewById(R.id.btn_thread_pool_one);
        Button btnStartThreadPoolTwo = findViewById(R.id.btn_thread_pool_two);
        Button btnStartThreadPoolThree = findViewById(R.id.btn_thread_pool_three);
        Button btnStartThreadPoolFour = findViewById(R.id.btn_thread_pool_four);
        btnStartThreadPoolOne.setOnClickListener(this);
        btnStartThreadPoolTwo.setOnClickListener(this);
        btnStartThreadPoolThree.setOnClickListener(this);
        btnStartThreadPoolFour.setOnClickListener(this);
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("tag","current time= "+System.currentTimeMillis());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_thread_pool_one:
                threadPoolTestOne();
                break;
            case R.id.btn_thread_pool_two:
                threadPoolTestTwo();
                break;
            case R.id.btn_thread_pool_three:
                threadPoolTestThree();
                break;
            case R.id.btn_thread_pool_four:
                threadPoolTestFour();
                break;
            default:
                break;
        }
    }


    private void threadPoolTestOne() {
        // normalThreadPoolExecutor
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10000,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10));
        long t1 = System.currentTimeMillis();
        threadPoolExecutor.execute(r);
        threadPoolExecutor.execute(r);
        threadPoolExecutor.execute(r);
        threadPoolExecutor.execute(r);
        long t2 = System.currentTimeMillis();
        Log.e("tag", "耗时=" + (t2 - t1) + "ms");
    }

    private void threadPoolTestTwo() {
        // scheduledPoolExecutor
        long t1 = System.currentTimeMillis();
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.execute(r);
        long t2 = System.currentTimeMillis();
        Log.e("tag", "耗时=" + (t2 - t1) + "ms");
    }

    /**
     * 缓存线程池
     */
    private void threadPoolTestThree() {
        // cacheThreadPool
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        long t1 = System.currentTimeMillis();
        cachedThreadPool.execute(r);
        long t2 = System.currentTimeMillis();
        Log.e("tag", "耗时=" + (t2 - t1) + "ms");
    }

    /**
     * 定时线程池
     */
    private void threadPoolTestFour() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        long t1 = System.currentTimeMillis();

        // 第一次延迟1s，后面每隔2s执行1次
        // scheduledExecutorService.scheduleWithFixedDelay(r, 1, 1, TimeUnit.SECONDS);

        // 每隔1s执行1次
        // scheduledExecutorService.schedule(r, 1, TimeUnit.SECONDS);

        // 第一次延迟1s，后面每隔1s执行依次
        scheduledExecutorService.scheduleAtFixedRate(r, 1, 1, TimeUnit.SECONDS);

        long t2 = System.currentTimeMillis();
        Log.e("tag", " method five 耗时=" + (t2 - t1) + "ms");
        // 关闭线程池
        // scheduledExecutorService.shutdown();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
