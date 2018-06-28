package net.gepergee.usualtestproject.http;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.bean.NetBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.Result;

/**
 * Retrofit+OkHttp+RxJava使用
 * @author petergee
 * @date 2018/6/27
 */
public class NetTestActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
        init();
    }

    private void init() {
        textView = findViewById(R.id.tv_net_text);
         okHttpMethod();
        // rxJavaMethod();
        // rxJavaMethodTwo();
        // rxJavaMethodThree();
        rxJavaMethodFour();
    }

    // okHttp方式
    private void okHttpMethod() {
        Map<String,String> map=new HashMap();
        map.put("num","1");
        map.put("page","1");
        HttpRequest.normalRequest(map);
    }

    /** 使用方式四
     * Observable使用
     */
    private void rxJavaMethodFour() {
        Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        });


        Observer<Integer>observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("tag","onSubscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.e("tag","onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("tag","onError");
            }

            @Override
            public void onComplete() {
                Log.e("tag","onComplete");
            }
        };
        observable.subscribe(observer);
    }

    // RxJava使用方式三 结合retrofit的Post方法
    private void rxJavaMethodThree() {
        HttpRequest.requestNetPost(new Observer<Result<NetBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<NetBean> value) {
                Log.e("tag","postMethod next");
                textView.setText("哈哈哈哈  请求成功啦");
            }

            @Override
            public void onError(Throwable e) {
                textView.setText("真讨厌，请求失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    // rxJava抽取
    private void rxJavaMethodTwo() {
        HttpRequest.requestNet(new Observer<Result<NetBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Result<NetBean> value) {
                Log.e("tag","next");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("tag","error");
            }

            @Override
            public void onComplete() {
                Log.e("tag","complete");
            }
        });
    }

    // 结合RxJava使用
    private void rxJavaMethod() {
        INetWorkApi service = NetHttp.getRetrofit().create(INetWorkApi.class);
        service.getNews("1","1").subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<NetBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(Result<NetBean> value) {
                        System.out.println("success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });



    }
}
