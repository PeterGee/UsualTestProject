package net.gepergee.usualtestproject.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import net.gepergee.usualtestproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池功能使用
 *
 * @author petergee
 * @date 2018/5/24
 */
public class ThreadPoolTest extends Activity implements View.OnClickListener {

    private ThreadPoolExecutor executor;
    private ExecutorService fixedThreadPool;
    private ExecutorService singleExecutor;
    private ExecutorService cachedThreadPool;
    private ScheduledExecutorService scheduledExecutorService;
    private Runnable r;
    long t,t2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        // Looper.loop();
        // 普通线程池   会根据设置的值先创建核心线程
        executor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10));

        // 固定线程数   设定值为5，线程会5个，5个的执行，先创建5个核心线程，其他的放在队列中，核心线程空闲，依次执行队列中线程
        fixedThreadPool = Executors.newFixedThreadPool(5);

        // 单线程池   每次只创建一个核心线程，多余的放置于队列中，待核心线程空闲，依次从队列中获取消息
        singleExecutor = Executors.newSingleThreadExecutor();

        // 缓存线程池  无核心线程，超时时间为60s，适合创建大量线程时使用
        cachedThreadPool = Executors.newCachedThreadPool();

        // 定时执行的线程池,通过构造指定核心线程数量
        scheduledExecutorService = Executors.newScheduledThreadPool(5);


        initView();
    }

    private void initView() {
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("tag","click");
       /* for (int i = 0; i < 20; i++) {
            final int finalI = i;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    // SystemClock.sleep(1000);
                    Log.e("tag", "thread=" + finalI);
                }
            };
            // executor.execute(r);
            // fixedThreadPool.execute(r);
            // singleExecutor.execute(r);
            // cachedThreadPool.execute(r);
        }*/
       t=System.currentTimeMillis();
       Runnable r= new Runnable() {
           @Override
           public void run() {
            Log.e("tag","scheduled")  ;
               t2=System.currentTimeMillis();
               Log.e("tag","耗时="+(t2-t));
           }
       };


        // 设置延时1s执行
        // scheduledExecutorService.schedule(r,1,TimeUnit.SECONDS);

        // 初始延时1s，然后再每隔1s执行一次
         // scheduledExecutorService.scheduleAtFixedRate(r,2,1,TimeUnit.SECONDS);

        // 初始延迟1s，然后每隔1s执行一次，通过log查看两次执行相差1001ms，不是准确的1s
         scheduledExecutorService.scheduleWithFixedDelay(r,1,1,TimeUnit.SECONDS);
    }
}
