package net.gepergee.usualtestproject.toolBar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.FirstLineAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * ToolBar实践
 *
 * @author petergee
 * @date 2018/8/1
 */
public class ToolBarActivity extends Activity {

    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        initData();
        initView();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add("条目" + String.valueOf(i));
        }
    }

    private void initView() {
        // translucent statusBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        RecyclerView toolBarRecycleView = findViewById(R.id.toolbar__recycler_view);
        // layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        toolBarRecycleView.setLayoutManager(layoutManager);
        // item divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        // adapter
        toolBarRecycleView.addItemDecoration(dividerItemDecoration);
        toolBarRecycleView.setAdapter(new FirstLineAdapter(this, mList));
    }
}
