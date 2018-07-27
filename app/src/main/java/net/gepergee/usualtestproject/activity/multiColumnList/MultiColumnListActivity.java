package net.gepergee.usualtestproject.activity.multiColumnList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.FirstLineAdapter;
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.RecyclerItemClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 多列list
 * @author petergee
 * @date 2018/7/26
 */
public class MultiColumnListActivity extends Activity {

    private List<String> list;
    private List<String> list2;
    private List<String> list3;
    private List<String> list4;
    private FirstLineAdapter firstLineAdapter,secondLineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_column);
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("于谦");
        list.add("郭德纲");
        list.add("小岳岳");


        list2 = new ArrayList<>();
        list2.add("抽烟");
        list2.add("喝酒");
        list2.add("烫头");


        list3 = new ArrayList<>();
        list3.add("主持");
        list3.add("说相声");
        list3.add("拍电影");


        list4 = new ArrayList<>();
        list4.add("拍电影");
        list4.add("唱歌");
        list4.add("说相声");
        list4.add("做采访");


    }

    private void initView() {
        RecyclerView recyclerView=findViewById(R.id.rv_recyclerview_one);
        final RecyclerView recyclerViewTwo=findViewById(R.id.rv_recyclerview_two);
        // layoutManager
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // set layoutManager
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this));
        // item Decoration
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerViewTwo.addItemDecoration(dividerItemDecoration);
        // item Animator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTwo.setItemAnimator(new DefaultItemAnimator());
        // adapter
        firstLineAdapter = new FirstLineAdapter(this,list);
        secondLineAdapter = new FirstLineAdapter(this,list2);
        recyclerView.setAdapter(firstLineAdapter);
        // childAdapter
        recyclerViewTwo.setAdapter(secondLineAdapter);
        // add listener
        firstLineAdapter.setRecyclerItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (position==0){
                    secondLineAdapter=new FirstLineAdapter(getApplicationContext(),list2);
                    recyclerViewTwo.setAdapter(secondLineAdapter);
                }

                if (position==1){
                    secondLineAdapter=new FirstLineAdapter(getApplicationContext(),list3);
                    recyclerViewTwo.setAdapter(secondLineAdapter);
                }

                if (position==2){
                    secondLineAdapter=new FirstLineAdapter(getApplicationContext(),list4);
                    recyclerViewTwo.setAdapter(secondLineAdapter);
                }
            }

            @Override
            public void onItemLongClickListener(int position) {
                Toast.makeText(getApplicationContext(),"LongClicked "+list.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
