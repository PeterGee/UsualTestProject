package net.gepergee.usualtestproject.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.gepergee.usualtestproject.R;

/**
 * @author petergee
 * @date 2018/6/7
 */
public class CustomViewActivityTwo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_two);
        init();
    }

    private void init() {
        CustomPieView pieView = findViewById(R.id.custom_pie);
        pieView.setStartAngle(-90);
        float[] datas = {100, 200, 300, 200, 500};
        pieView.setData(datas);
    }

}
