package net.gepergee.usualtestproject.customview.IndexView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import net.gepergee.usualtestproject.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author petergee
 * @date 2018/8/29
 */
public class CustomIndexViewActivity extends Activity implements IndexView.onWordsChangeListener, AbsListView.OnScrollListener {

    private TextView tv;
    private Handler handler = new Handler();
    private ArrayList<Person> list;
    private ListView listView;
    private IndexView word;
    private String[] dataArray = {
            "鞍山", "案场", "白宫", "白云", "白俄", "长沙", "常州", "常熟", "大厂", "大娜迦",
            "福州", "福建", "富豪", "广州", "湖南", "湖北", "胡同", "加州", "加拉大", "家具",
            "开门", "开始", "可能", "连接", "利用", "煤化工", "密度", "漫画", "你好", "你的",
            "哪些", "欧版", "排行", "贫困", "平时", "请问", "确认", "其他", "染发", "让他",
            "头像", "是个", "数据", "天空", "退出", "提示", "为空", "维护", "新建", "想到",
            "用户", "阅读", "知道", "这本", "足球"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_view);
        initData();
        initView();
    }

    /**
     * 初始化联系人列表信息
     */
    private void initData() {
        list = new ArrayList<>();
        int length = dataArray.length;
        for (int i = 0; i < length; i++) {
            list.add(new Person(dataArray[i]));
        }

        //对集合排序
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                //根据拼音进行排序
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });

    }

    private void initView() {
        tv = findViewById(R.id.tv);
        word = findViewById(R.id.words);
        word.setOnWordsChangeListener(this);
        listView = findViewById(R.id.list);
        listView.setOnScrollListener(this);
        listView.setAdapter(new MyAdapter(this, list));

    }

    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        // tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //500ms后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }

    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < list.size(); i++) {
            String headerWord = list.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                listView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当滑动列表的时候，更新右侧字母列表的选中状态
        word.setTouchIndex(list.get(firstVisibleItem).getHeaderWord());
    }
}
