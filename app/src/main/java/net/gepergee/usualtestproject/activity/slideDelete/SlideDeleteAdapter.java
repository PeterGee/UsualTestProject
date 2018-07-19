package net.gepergee.usualtestproject.activity.slideDelete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;

import java.util.List;

/**
 * Adapter
 * @author petergee
 * @date 2018/7/19
 */
public class SlideDeleteAdapter extends RecyclerView.Adapter<SlideDeleteAdapter.ViewHolder> {
    private Context context;
    private List<String> mList;

    public SlideDeleteAdapter(Context context) {
        this.context = context;

    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mList = dataList;
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.slide_delete_item, null));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_name);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }
    }
}
