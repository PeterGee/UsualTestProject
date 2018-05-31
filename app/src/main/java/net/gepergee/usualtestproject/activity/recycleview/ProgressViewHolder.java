package net.gepergee.usualtestproject.activity.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import net.gepergee.usualtestproject.R;


/**
 * @author petergee
 * @date 2018/4/19
 */

class ProgressViewHolder extends RecyclerView.ViewHolder {
    ProgressBar pb;
    public ProgressViewHolder(View view) {
        super(view);
        pb = itemView.findViewById(R.id.pb);
    }
}
