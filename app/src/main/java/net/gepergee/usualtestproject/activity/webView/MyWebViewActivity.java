package net.gepergee.usualtestproject.activity.webView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import net.gepergee.usualtestproject.R;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author petergee
 * @date 2018/8/27
 */
public class MyWebViewActivity extends Activity {
    private String url="http://rcv.aiclk.com/click?CAAQJA.KAAEiPvTdFk_etdVzmeCJakuJUvTqaxDzP-Fdmk0JFJueCJ_eCeazSLuqFsJWzEqfAk2D7KFVG_HeGbLzPA_ePsWePsEApbUgtrDItrlJUTZ-EXTJhqX-C5NRCTBdOb8qaeueaAfqFApz2pKKA2AsAAJAKAAEVDLuL_jszz2hMFs7rWb9wTlzAvAArbAJqmc6FXbA0LsKAsGAUvGKlXb-8M_zZ4cQhrVgZW0eUjBdUjBdEXFgCDcyCTFgSTFJhqbxCTlymvTdFk_etdVzmeCJakuJUvTqaxDzP-Fdmk0JFJueCJ_eCeazSLuqF5OPG2ps2AYpaxaeme_qadHPCqDxFvogCMTgPDuIhJue9bMJfdOgUjldU_jeE0F-9v1-FTHA8bIJfpFJUMB-UDHzPpHKS-axSMFzFk_Q2VVxfvVgtNHqhbqgtjZgfMDgC07gt-Vd8bIJ9ql-hp0xCrOzFqHstqcRUXaRhMTyme_qPeuImsWz8beJfpogUTBJtTlzFlCQVMoRUXF-8va-8LjzmA_e8_OqmA_e8byJUMadUuTJfMOJUX9dmb0Q2ua-8TNdUqcRUWHeFpHsUqcRUXDRU0TdC5_zFGaeFA_QVqfdUutJhvTJUM_gfq7-STcgFbOQ2XTQ8pF-8v1xSjfdhLHefbEJfMOgUjldU_jeE0CemLo-UTl22sAZ2S0A22ps22LpuAASAA2ApbrKAsEPa2CeFJfzmAaePL_eFlDqu2pSVkLApLyzPJOqFxXePe_eFAOzmGfSAsR5_2KsVs0Ga7pzPbCGmb_eabOeFbfep2pe74pmUjHRUuNJE40IFA2isu7g9rWzOppgtMOgCTlLPxBePN2MTvsIk5eePA2G9r7gSGcE5rprDrvMTvsIk5eePAnL8-CiEppx8pNdr-TJlo7-K40eaxBeaJ2isoLrs0eIKpNRUoTLs-TJCociEpUdhvaRUjBIaGBeKpPR8vcgUkcqFLBeKWaeFAOIF2DLs0cJtTNdEpmJUdVxtlcqmefIFeCzAvKPa2CeFJfzmAaePL_eFlDqDb2ealXqmx0dSMVePkueUkfeFlaeFlWqSs_em-VzUMZzUvGBAVJ22X2oA0bA7LpPA2Ps22LAuApSAL2AiLpKSq_JO0admJCbAJAN2JLsAA2AP2AGASHpVLiPFsXeZWuqF2BeFA_IF2OsAs%22";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_webview_test);
        WebView webView = findViewById(R.id.my_webview);
        initWebViewSetting(webView);
        // 使用该list可以防止出现fast-fail异常
        CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<>();
    }

    private void initWebViewSetting(WebView mWebView) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.requestFocus();
        mWebView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(url);
       /* mWebView.setWebChromeClient(new WebChromeClient(){

        });*/
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //结束
                super.onPageFinished(view, url);
            }
         });
    }
}
