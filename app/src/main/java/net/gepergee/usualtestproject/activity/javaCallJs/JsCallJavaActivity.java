package net.gepergee.usualtestproject.activity.javaCallJs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;


/**
 * @author geqipeng
 * @date 2017/11/14
 */

public class JsCallJavaActivity extends Activity {

    private WebView mWebView = null;
    private TextView msgView = null;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_call_java);
        initView();

    }

    private void initView() {
        mWebView = findViewById(R.id.webview);
        msgView = findViewById(R.id.msg);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(btnClickListener);
        initWebViewSetting();
    }

    /**
     * webView设置
     */
    private void initWebViewSetting() {
        // 启用javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        mWebView.loadUrl("file:///android_asset/js_test.html");
        mWebView.addJavascriptInterface(this, "callJava");
    }

    /**
     * @JavascriptInterface 添加js调用注解
     */
    @JavascriptInterface
    public void Function4Js() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                msgView.setText(msgView.getText() + "\njs调用了java函数");

            }
        });
    }
    @JavascriptInterface
    public void Function4Js(final String str) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);


            }
        });
    }

    private View.OnClickListener btnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {

            // 无参调用
             mWebView.loadUrl("javascript:javaCallJs()");
            // 传参调用
            //mWebView.loadUrl("javascript:javaCallJsWithArgs(" + "'transfer parameter '" + ")");
            String str="hello";
           // mWebView.loadUrl(String.format("javascript:javaCallJsWithArgs(" + str + ")"));
            String call="javascript:javaCallJsWithArgs(\"" + str + "\")";
            mWebView.loadUrl(call);
        }
    };
}
