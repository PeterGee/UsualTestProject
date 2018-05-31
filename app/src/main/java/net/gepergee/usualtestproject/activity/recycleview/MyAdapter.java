package net.gepergee.usualtestproject.activity.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import net.gepergee.usualtestproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author petergee
 * @date 2018/4/19
 */

public class MyAdapter extends RecyclerView.Adapter {
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROGRESS = 2;
    private List<String> list = null;
    private recyclerViewItemClickListener mListener;
    private LoadMoreDataListener moreDataListener;
    private Context mContext;
    private boolean loaded;
    private RecyclerView mRecyclerView;
    private int totalCount;
    private int lastVisiblePosition;

    public MyAdapter(Context context, List list, RecyclerView recyclerView) {
        if (list == null) {
            list = new ArrayList();
        }
        this.list = list;
        this.mContext = context;
        this.mRecyclerView = recyclerView;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager mLinerLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            // 添加滑动事件
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    // 获取条目总数
                    totalCount = mLinerLayoutManager.getItemCount();
                    // 最后一个可见条目
                    lastVisiblePosition = mLinerLayoutManager.findLastVisibleItemPosition();
                    // 设置刷新状态  在最后一个条目可见时进行加载更多
                    if (!loaded && totalCount <= lastVisiblePosition + 1) {
                        if (moreDataListener != null) {
                            moreDataListener.loadMoreData();
                            loaded = true;
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mHolder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
            mHolder = new MyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item2, parent, false);
            mHolder = new ProgressViewHolder(view);
        }
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyHolder) {
            if (((MyHolder) holder).tvItem != null) {
                String s = list.get(position);
                ((MyHolder) holder).tvItem.setText(s);
            }
        } else if (holder instanceof ProgressViewHolder) {
            if (((ProgressViewHolder) holder).pb != null)
                ((ProgressViewHolder) holder).pb.setIndeterminate(true);
        }

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    interface recyclerViewItemClickListener {
        void onItemClick(int position);

        void onItemLongClick(int position);
    }

    public void setOnClickListener(recyclerViewItemClickListener listener) {
        this.mListener = listener;

    }

    public void setLoadMoreListener(LoadMoreDataListener loadMoreListener) {
        this.moreDataListener = loadMoreListener;
    }

}

