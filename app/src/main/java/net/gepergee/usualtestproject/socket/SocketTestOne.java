package net.gepergee.usualtestproject.socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.gepergee.usualtestproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket 基本使用
 *
 * @author petergee
 * @date 2018/5/30
 */
public class SocketTestOne extends Activity implements View.OnClickListener {

    private ExecutorService executorService;
    private Socket mSocket;
    private Handler mHandler;
    private BufferedReader br;
    private OutputStream os;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test_one);
        init();

    }

    private void init() {
        initView();
        initField();
    }

    private void initField() {
        // 创建线程池
        executorService = Executors.newCachedThreadPool();
        mHandler = new Handler();
    }

    private void initView() {
        Button btnConnect = findViewById(R.id.btn_connect);
        btnConnect.setOnClickListener(this);

        Button btnDisConnect = findViewById(R.id.btn_disconnect);
        btnDisConnect.setOnClickListener(this);

        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        Button btnReceive = findViewById(R.id.btn_receive);
        btnReceive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                Log.e("tag", "连接socket");
                // 连接
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mSocket = new Socket("192.168.50.8", 8088);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_send:
                // 发送消息
                Log.e("tag", "发送消息");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 创建输出流
                            os = mSocket.getOutputStream();
                            os.write("socket out \n".getBytes("utf-8"));
                            os.flush();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_receive:
                // 接收消息
                Log.e("tag", "接收消息");
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 创建输入流
                            InputStream is = mSocket.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            br = new BufferedReader(isr);
                            String response = br.readLine();
                            Log.e("tag","response="+response);

                            // 通过handler将数据发送出去
                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = response;
                            mHandler.sendMessage(msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
            case R.id.btn_disconnect:
                // 断开连接
                try {
                    // 关闭服务器发送到客户端的链接
                    br.close();
                    // 关闭客户端发送到服务器的链接
                    os.close();
                    // 关闭socket
                    mSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }
}
