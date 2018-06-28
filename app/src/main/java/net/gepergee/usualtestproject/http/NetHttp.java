package net.gepergee.usualtestproject.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络框架
 * @author petergee
 * @date 2018/6/27
 */
public class NetHttp {

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static String BASE_URL="http://apis.baidu.com/txapi/";

    public static synchronized Retrofit getRetrofit(){
        if (mRetrofit==null){
                if (mRetrofit==null){
                    Retrofit.Builder builder=new Retrofit.Builder();
                    builder.baseUrl(BASE_URL);
                    // 配置Gson解析器
                    builder.addConverterFactory(GsonConverterFactory.create());
                    builder.callFactory(getOkHttp());

                    // 设置callAdapter结合RxJava使用
                    // builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

                    // 构建retrofit对象
                    mRetrofit=builder.build();
            }
        }
        return mRetrofit;
    }

    private static synchronized OkHttpClient getOkHttp(){
        if (mOkHttpClient==null){
                if (mOkHttpClient==null){
                    OkHttpClient.Builder builder=new OkHttpClient.Builder();
                    // 设置网络拦截器
                    // builder.addNetworkInterceptor(new LogInterceptor());
                    builder.addInterceptor(new LogInterceptor());
                    builder.connectTimeout(15, TimeUnit.SECONDS);
                    builder.readTimeout(15,TimeUnit.SECONDS);
                    builder.writeTimeout(15,TimeUnit.SECONDS);
                    mOkHttpClient=builder.build();
                }
        }
        return mOkHttpClient;
    }

}
