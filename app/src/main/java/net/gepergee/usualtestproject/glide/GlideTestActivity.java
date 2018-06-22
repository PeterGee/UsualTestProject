package net.gepergee.usualtestproject.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.gepergee.usualtestproject.R;

import java.util.ArrayList;
import java.util.List;

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
        Glide.with(this)
                .load(url)
                .into(imgPic);
        Glide.with(this).asGif().preload(400,400);




    }

    public static void main(String[] args) {
        String str = "abcdabc";
        char[] arr = str.toCharArray();
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (list.contains(arr[i])) {
                continue;
            }
            list.add(arr[i]);
        }
        System.out.println(list.toString());
    }
}
