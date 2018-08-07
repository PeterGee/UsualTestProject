package net.gepergee.usualtestproject.activity.window;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.FirstLineAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author petergee
 * @date 2018/8/3
 */
public class WindowActivity extends Activity  {

    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        initData();
        initView();
    }

    private void initData() {
        list=new ArrayList<>();
        //add data
        for (int i=0;i<50;i++){
            list.add("this is item"+String.valueOf(i));
        }
    }

    private void initView() {
        // TextView tvShowWindow = findViewById(R.id.tv_window_pop);
        // tvShowWindow.setOnClickListener(this);
        RecyclerView recyclerView=findViewById(R.id.rv_recycler_view);
        // layoutManager
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // itemDecoration
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_line));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new FirstLineAdapter(this,list));
    }

    /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        // alertDialogPop(this);
        // dialogPop(this);
        // showPopUpWindow(this, v);
    }*/


    /**
     * alertDialog 使用
     *
     * @param context
     */
    private void alertDialogPop(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("AlertDialog");
        // View view= LayoutInflater.from(this).inflate(R.layout.layout_alert_dialog,null);
        // dialog.setView(view);

        Message message = Message.obtain();
        message.what = 1;
        message.obj = "alertDialog";
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "confirm clicked");
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "cancel clicked");
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "neutral clicked");
            }
        });
        dialog.setIcon(R.mipmap.ic_action_add);
        dialog.show();
        dialog.setCancelable(false);
    }

    /**
     * dialog 使用
     *
     * @param context
     */
    private void dialogPop(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.select_dialog_item);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPopUpWindow(Context context, View view) {
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.bg_001));
        View v = LayoutInflater.from(context).inflate(R.layout.layout_alert_dialog, null);
        popupWindow.setContentView(v);
        popupWindow.setElevation(1f);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 1000);
    }
}
