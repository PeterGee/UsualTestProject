package net.gepergee.usualtestproject.activity.widget;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;

/**
 * @author petergee
 * @date 2018/5/29
 */
public class AsyncTaskTestActivity extends Activity {
    // 线程变量
    private myTask mTask;

    // 主布局中的UI组件
    private Button button, cancel; // 加载、取消按钮
    private TextView text; // 更新的UI组件
    private ProgressBar progressBar; // 进度条

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        button = findViewById(R.id.button);
        cancel = findViewById(R.id.cancel);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progress_bar);

        /**
         * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
         * 注：AsyncTask子类的实例必须在UI线程中创建
         */
        mTask = new myTask();

        // 加载按钮按按下时，则启动AsyncTask
        // 任务完成后更新TextView的文本
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.execute();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });

    }

    private class myTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            Log.e("tag","onPreExecute");
            text.setText("加载中...");

        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("tag","doInBackground");
            try {
                int count = 0;
                int length = 1;
                while (count < 99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e("tag","onProgressUpdate");
            progressBar.setProgress(values[0]);
            text.setText("已经加载" + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("tag","onPostExecute");
            text.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            text.setText("取消加载");
        }
    }
}
