package net.gepergee.usualtestproject.activity.multiColumnList.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.kotlin.KotlinTestActivity;

import java.util.List;

/**
 * @author petergee
 * @date 2018/7/27
 */
public class FirstLineAdapter extends RecyclerView.Adapter<viewHolder> {
    private Context mContext;
    private List<String> mList;
    public RecyclerItemClickListener recyclerItemClickListener;

    public FirstLineAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_multi_column_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        String str=mList.get(position);
        holder.tvTextFirstLine.setText(str);

        //item listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerItemClickListener==null){
                    return;
                }
                recyclerItemClickListener.onItemClickListener(position);
            }
        });

        // item LongClickListener
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (recyclerItemClickListener!=null)
                recyclerItemClickListener.onItemLongClickListener(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size()==0?0:mList.size();
    }


    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }
}

class viewHolder extends RecyclerView.ViewHolder {
    TextView tvTextFirstLine;
    public viewHolder(View itemView) {
        super(itemView);
        tvTextFirstLine=itemView.findViewById(R.id.item_text);
    }
}

