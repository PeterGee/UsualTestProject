package net.gepergee.usualtestproject.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 拦截器
 * @author petergee
 * @date 2018/6/27
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e("tag","request= "+request.method()+request.url()+request.body()+request.headers());
        Response response=chain.proceed(request);
        Log.e("tag","response= "+response.body().string());

        // addParams(request);
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(),response.body().toString())).build();
    }

    /**
     * 通过interceptor添加参数
     * @param request
     */
    private void addParams(Request request) {
        HttpUrl httpUrl=request.url().newBuilder().addQueryParameter("key","value").build();
        request.newBuilder().addHeader("header","header").url(httpUrl).build();
    }
}
