package net.gepergee.usualtestproject.http;

import android.util.Log;

import net.gepergee.usualtestproject.bean.NetBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

/**
 * @author petergee
 * @date 2018/6/27
 */
public class HttpRequest {
    private static String num="1", page="1";

    // 普通方式请求
    public static void normalRequest(Map<String,String> map){
        NetHttp.getRetrofit().create(INetWorkApi.class).normalPost(map).enqueue(new Callback<NetBean>() {
            @Override
            public void onResponse(Call<NetBean> call, Response<NetBean> response) {
                Log.e("tag","response="+response.body().toString());
            }

            @Override
            public void onFailure(Call<NetBean> call, Throwable t) {
                Log.e("tag","error= "+t.getMessage());
            }
        });
    }



    // get方法结合RxJava
    public static void requestNet(Observer<Result<NetBean>>observer) {
        INetWorkApi service = NetHttp.getRetrofit().create(INetWorkApi.class);
        service.getNews(num, page)
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    // post 方法结合RxJava
    public static void requestNetPost(Observer<Result<NetBean>>observer){
        INetWorkApi inetWorkTest = NetHttp.getRetrofit().create(INetWorkApi.class);
        Map map=new HashMap();
        map.put("num","1");
        map.put("page","1");
        inetWorkTest.requestNet(map).subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
