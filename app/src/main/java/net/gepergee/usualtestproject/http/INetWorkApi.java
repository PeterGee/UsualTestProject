package net.gepergee.usualtestproject.http;

import net.gepergee.usualtestproject.bean.NetBean;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author petergee
 * @date 2018/6/27
 */
public interface INetWorkApi {

    // 基本使用 form表单形式请求
    @FormUrlEncoded
    @POST
    Call<NetBean> normalPost(@FieldMap Map<String,String>map);


    // get方法使用结合Rxjava
    @Headers("apikey:81bf9da930c7f9825a3c3383f1d8d766")
    @GET("word/word")
    Observable<Result<NetBean>> getNews(@Query("num") String num, @Query("page")String page);

    // post方法使用结合Rxjava
    @FormUrlEncoded
    @POST("world/world")
    Observable<Result<NetBean>> requestNet(@FieldMap Map<String,String>map);


}
