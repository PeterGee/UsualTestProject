package net.gepergee.usualtestproject.activity.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.gepergee.usualtestproject.R;


/**
 * @author petergee
 * @date 2018/4/19
 */

class MyHolder extends RecyclerView.ViewHolder {

    TextView tvItem;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        tvItem = itemView.findViewById(R.id.tv_item);
    }
}