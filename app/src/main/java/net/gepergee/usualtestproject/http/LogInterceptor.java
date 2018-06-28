package net.gepergee.usualtestproject.http;

import android.util.Log;

import java.io.IOException;

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
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(),response.body().toString())).build();
    }
}
