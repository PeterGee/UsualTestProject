package net.gepergee.usualtestproject.activity.recycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import net.gepergee.usualtestproject.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author petergee
 * @date 2018/4/19
 */

public class RecycleViewActivity extends Activity {
    private List<String> list = new ArrayList();
    private  List<String> loadMoreDataList = new ArrayList();
    private  List<String> refreshData = new ArrayList();
    private Handler handler=new Handler();
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 下拉刷新
                        list.addAll(0, refreshData);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },1000);

            }
        });

        adapter.setLoadMoreListener(new LoadMoreDataListener() {
            @Override
            public void loadMoreData() {
                list.add(null);
                adapter.notifyDataSetChanged();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //移除刷新的progressBar
                        list.remove(list.size() - 1);
                        adapter.notifyDataSetChanged();
                        // 加载更多
                        list.addAll(loadMoreDataList);
                        adapter.notifyDataSetChanged();
                        adapter.setLoaded(false);
                    }
                },1000);

            }
        });
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            list.add("i am item :" + i);
        }

        for (int i=0;i<5;i++){
            loadMoreDataList.add("load more data:"+i);
        }

        for (int i=0;i<2;i++){
            refreshData.add("refresh data:"+i);
        }
    }

    private void initView() {
        // init swipe
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF4081"));

        // init recyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 自定义分割线
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
        });

        adapter = new MyAdapter(this, list,recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new MyAdapter.recyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast("click");
            }

            @Override
            public void onItemLongClick(int position) {
                showToast("longClick");
            }
        });
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
