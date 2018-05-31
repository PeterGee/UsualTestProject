package net.gepergee.usualtestproject.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import net.gepergee.usualtestproject.R;

/**
 * @author petergee
 * @date 2018/5/18
 */

public class HandlerActivity extends Activity {
    private Handler mHandler;

    public HandlerActivity() {
        mHandler = new Handler(mCallBack);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        setStatusBarTranslucent();
        handlerTestMethod();
    }

    /**
     * handler 常用测试方法
     */
    private void handlerTestMethod() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("tag", "sleep");
                SystemClock.sleep(1000);
                sendMessageMethod();

            }
        });


      /*  mHandler.postAtTime(new Runnable() {
            @Override
            public void run() {
                Log.e("tag","sleep1100");
                sendMessageMethod();
            }
        },1100);
        */

      /*mHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
              Log.e("tag","postDelayed");
              sendMessageMethod();
          }
      },1200);*/
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    private void sendMessageMethod() {
        Message msg = Message.obtain();
        msg.what = 100;
        msg.obj = "hahha";
        // mHandler.sendMessage(msg);

        // 延时发送消息
        mHandler.sendMessageDelayed(msg, 1000);   // 一个消息只能发送一次，如果一个消息正在使用中，不能再次发送该消息‘
        Message message = mHandler.obtainMessage();
        Log.e("tag", "message  wat=" + message.what);   // message  wat=0

    }

  /*  Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String s= (String) msg.obj;
                    Log.e("tag",s);
                    break;
            }
        }
    };*/

    Handler.Callback mCallBack = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("tag", "messageName=" + mHandler.getMessageName(msg));
            switch (msg.what) {
                case 100:
                    String s = (String) msg.obj;
                    Log.e("tag", "收到的msg is ：" + s);
                    break;
            }

            return true;
        }
    };


}
