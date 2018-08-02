package net.gepergee.usualtestproject.http;

import android.os.Handler;
import android.os.Message;
import android.telephony.VisualVoicemailService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络框架
 * @author petergee
 * @date 2018/6/27
 */
public class NetHttp {

    private static final int DOWNLOAD_PROGRESS = 0x001;
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static String BASE_URL="http://apis.baidu.com/txapi/";

    /**
     * 创建Retrofit对象
     * @return
     */
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

    /**
     * 创建okHttp对象
     * @return
     */
    private static synchronized OkHttpClient getOkHttp(){
        if (mOkHttpClient==null){
                if (mOkHttpClient==null){
                    OkHttpClient.Builder newBuilder=new OkHttpClient().newBuilder();
                    // 设置网络拦截器
                    // newBuilder.addNetworkInterceptor(new LogInterceptor());
                    newBuilder.addInterceptor(new LogInterceptor());
                    newBuilder.connectTimeout(15, TimeUnit.SECONDS);
                    newBuilder.readTimeout(15,TimeUnit.SECONDS);
                    newBuilder.writeTimeout(15,TimeUnit.SECONDS);
                    // 设置okHttp缓存
                    newBuilder.cache(new Cache(new File(""),1000));
                    mOkHttpClient=newBuilder.build();
                }
        }
        return mOkHttpClient;
    }


    /**
     * 设置okHttp缓存
     */
    private static void setCacheMethod() {
        CacheControl control=new CacheControl.Builder().maxAge(60, TimeUnit.SECONDS).build();
        Request request=new Request.Builder().url("").cacheControl(control).build();
        try {
            Response response=mOkHttpClient.newCall(request).execute();
            // 关闭响应
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void forceCache(){
        CacheControl control=new CacheControl.Builder().maxAge(60, TimeUnit.SECONDS).onlyIfCached().build();
        Request request=new Request.Builder().url("").cacheControl(control).build();
        try {
            Response response=mOkHttpClient.newCall(request).execute();
            // 关闭响应
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传文件
     * @param url 请求地址
     * @param fileKey 键
     * @param file   文件
     * @param map   其他参数
     * @param callback  okHttp回调callback
     */
    public static void upLoadFile(String url, String fileKey, File file, Map<String, String> map, Callback callback) {
        //创建RequestBody
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(null!=file){
            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            builder.addFormDataPart(fileKey, file.getName(), body);
        }
        if (map != null) {
            for (String key : map.keySet()) {
                builder.addFormDataPart(key, map.get(key));
            }
        }
        RequestBody requestBody = builder.build();
        //创建Request
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient.Builder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 下载文件
     * @param downloadUrl  下载链接
     * @param outPath      输出路径
     * @param mHandler     handler 用于接收下载进度
     * @param callback     okHttp回调
     */
    public static void download(String downloadUrl, final String outPath, final Handler mHandler, final Callback callback) {
        Request request = new Request.Builder().url(downloadUrl).build();
        Call call = new OkHttpClient.Builder().readTimeout(60 * 5, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (callback != null) {
                        callback.onResponse(call, response);
                    }
                    return;
                }
                InputStream is = response.body().byteStream();
                long length = response.body().contentLength();
                File file = new File(outPath);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] buf = new byte[1024];
                long l = 0;
                final NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
                while ((len = is.read(buf)) != -1) {
                    l += len;
                    fos.write(buf, 0, len);
                    if (null != mHandler) {
                        format.setMinimumFractionDigits(0);// 设置小数位
                        Message msg = new Message();
                        msg.what = DOWNLOAD_PROGRESS;
                        msg.obj = format.format((float) l / (float) length);
                        mHandler.sendMessage(msg);
                    }
                }
                fos.flush();
                is.close();
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }

        });
    }


}
