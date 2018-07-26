package net.gepergee.usualtestproject.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.mvp.presenter.MvpPresenter;

/**
 * @author petergee
 * @date 2018/7/25
 */
public class MvpTestActivity extends Activity implements IGetInfoView{

    private TextView tvAge;
    private TextView tvName;
    private MvpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);
        initPresenter();
        initView();
    }

    private void initView() {
        tvAge = findViewById(R.id.text_mvp_age);
        tvName = findViewById(R.id.text_mvp_name);
        Button btnSetInfo=findViewById(R.id.btn_setInfo);
        btnSetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getNetInfo();
            }
        });
    }

    private void initPresenter() {
        presenter = new MvpPresenter(this);
    }

    @Override
    public void updateAge(final int age) {
      tvAge.setText(String.valueOf(age));
    }

    @Override
    public void updateName(final String name) {
        tvName.setText(name);
    }
}
