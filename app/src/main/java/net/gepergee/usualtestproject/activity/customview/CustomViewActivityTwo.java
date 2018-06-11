package net.gepergee.usualtestproject.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

        // 贝塞尔曲线
        final ThirdOrderBezierView bezierView=findViewById(R.id.bezier_view);
        CheckBox checkBox1=findViewById(R.id.cb1);
        CheckBox checkBox2=findViewById(R.id.cb2);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bezierView.setCotrolMode(1);
                }else {
                    bezierView.setCotrolMode(2);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bezierView.setCotrolMode(2);
                }else {
                    bezierView.setCotrolMode(1);
                }
            }
        });
    }

}
