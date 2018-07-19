package net.gepergee.usualtestproject.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.designmode.singleton.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/** glide test
 * @author petergee
 * @date 2018/6/19
 */
public class GlideTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);
         initView();
    }

    private void initView() {
        ImageView imgPic=findViewById(R.id.img_agt_pic);
        String url="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3532486166,3197709206&fm=27&gp=0.jpg";
        RequestOptions options=new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.circleCrop();
        options.skipMemoryCache(true);// 设置是或否跳过内存缓存
        options.timeout(1000);
        options.circleCrop();


        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("tag","loadFailed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("tag","onResourceReady");
                        return false;
                    }
                })
                .apply(options) // 设置RequestOptions
                .into(imgPic);

      // Glide.with(this).asGif().preload(400,400);
      /*  FutureTarget<File> target = Glide.with(this).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            File file = target.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        handlerTestMethod(imgPic);

        //设置lruCache缓存大小
        int maxLruCache= (int) (Runtime.getRuntime().maxMemory()/8);
        LruCache<String,Bitmap> lruCache=new LruCache<String, Bitmap>(maxLruCache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };

    }

    /**
     * handler 测试方法
     * @param imgPic
     */
    private void handlerTestMethod(ImageView imgPic) {
        Handler mHandler=new Handler();
        Message message=Message.obtain();
        message.what=1;
        mHandler.sendMessage(message);

        // handler.post
        mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        // View.post
        imgPic.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }



}
