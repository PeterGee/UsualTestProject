package net.gepergee.usualtestproject.activity.slideDelete;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import net.gepergee.usualtestproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿QQ侧滑操作Activity
 * @author petergee
 * @date 2018/7/19
 */
public class SlideDeleteActivity extends Activity implements SwipeItemClickListener {

    private List<String> dataList;
    private SlideDeleteAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_delete);
        init();
    }

    private void init() {

        // create data
        createData();
        SwipeMenuRecyclerView swipeMenuRecyclerView = findViewById(R.id.recycler_view);
        // layoutManager
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // itemDecoration
        swipeMenuRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color)));
        // clickListener
        swipeMenuRecyclerView.setSwipeItemClickListener(this);
        // slideDelete
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // menu item clickListener
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        // setAdapter
        mAdapter = new SlideDeleteAdapter(this);
        swipeMenuRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(dataList);
    }

    private void createData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("第" + i + "个Item");
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "clicked " + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(SlideDeleteActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_action_add)
                        .setWidth(width)
                        .setHeight(height);
                // 添加菜单到左侧。
                swipeLeftMenu.addMenuItem(addItem);

                SwipeMenuItem closeItem = new SwipeMenuItem(SlideDeleteActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(closeItem);
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(SlideDeleteActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                // 添加菜单到右侧。
                swipeRightMenu.addMenuItem(deleteItem);

                SwipeMenuItem addItem = new SwipeMenuItem(SlideDeleteActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem);
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(SlideDeleteActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                if (menuPosition == 0) {
                    dataList.remove(adapterPosition);
                    mAdapter.notifyDataSetChanged(dataList);
                }
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(SlideDeleteActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
