package net.gepergee.usualtestproject.blueTooth.Utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import net.gepergee.usualtestproject.R;

/**
 * @author geqipeng
 * @date 2017/6/27
 * @time 14:11
 */

public class ManagerUtil {
    /**
     * 获取连接状态
     * @return
     */
    public static  boolean getConnectivityState(Context context){
        ConnectivityManager mConnectManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectManager!=null){
            //获取网络信息
            NetworkInfo mNetWorkInfo=mConnectManager.getActiveNetworkInfo();
            if (mNetWorkInfo!=null&&mNetWorkInfo.isConnected()){
                //判断网络状态
                if (mNetWorkInfo.getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 显示通知
     * @param context
     */
    public static void  showNotification(Context context){
        NotificationManager mNotificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent=new Intent();
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,mIntent,0);
        Notification.Builder builder= new Notification.Builder(context)
                .setTicker("有新通知了")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("标题")
                .setContentText("内容")
                .setWhen(0)
                .setContentIntent(pendingIntent);
        mNotificationManager.notify(0,builder.build());

    }
    public  static  void alarmManager(Context context){
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //设置时钟时间
        alarmManager.setTime(10000);
        //设置时区
        alarmManager.setTimeZone("8");

//        AlarmManager.ELAPSED_REALTIME：表示闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始）。
//        AlarmManager.ELAPSED_REALTIME_WAKEUP：表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间。
//        AlarmManager.RTC：表示闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间。
//        AlarmManager.RTC_WAKEUP：表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间。
//        AlarmManager.POWER_OFF_WAKEUP：表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一，该状态下闹钟也是用绝对时间。
    }


}
