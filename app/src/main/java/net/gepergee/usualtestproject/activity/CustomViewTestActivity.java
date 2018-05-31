package net.gepergee.usualtestproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.bean.AbilityBean;
import net.gepergee.usualtestproject.view.CustomPolygon;



/**
 * @author geqipeng
 * @date 2017/11/25
 */

public class CustomViewTestActivity extends Activity {

    //  private CustomPolygon pv;
    // private SeekBar s1,s2,s3,s4,s5,s6,s7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        init();
    }

    private void init() {
        CustomPolygon mCustomPolygon=findViewById(R.id.pv);
        mCustomPolygon.setData(new AbilityBean(65, 70, 80, 70, 80, 80, 80));

       /* pv=findViewById(R.id.pv);

        s1=findViewById(R.id.sb1);
        s1.setOnSeekBarChangeListener(this);

        s2=findViewById(R.id.sb2);
        s2.setOnSeekBarChangeListener(this);

        s3=findViewById(R.id.sb3);
        s3.setOnSeekBarChangeListener(this);

        s4=findViewById(R.id.sb4);
        s4.setOnSeekBarChangeListener(this);

        s5=findViewById(R.id.sb5);
        s5.setOnSeekBarChangeListener(this);

        s6=findViewById(R.id.sb6);
        s6.setOnSeekBarChangeListener(this);

        s7=findViewById(R.id.sb7);
        s7.setOnSeekBarChangeListener(this);*/
    }

    /*@Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float value=(float)(seekBar.getProgress()/10.0);
        switch (seekBar.getId()){
            case R.id.sb1:
                pv.setValue1(value);
                break;
            case R.id.sb2:
                pv.setValue2(value);
                break;
            case R.id.sb3:
                pv.setValue3(value);
                break;
            case R.id.sb4:
                pv.setValue4(value);
                break;
            case R.id.sb5:
                pv.setValue5(value);
                break;
            case R.id.sb6:
                pv.setValue6(value);
                break;
            case R.id.sb7:
                pv.setValue7(value);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }*/
}
