package com.yanbober.com_ucast_manager.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Allen on 2017/6/13.
 */

public class MyTools {
    public static Date StringToDate(String s) {
        Date time = null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = sd.parse(s);
        } catch (java.text.ParseException e) {
            System.out.println("输入的日期格式有误！");
            e.printStackTrace();
        }
        return time;
    }


    public static long getIntToMillis(String str) {
        String str_date = str + " " + "00:00:00";
        Date date = StringToDate(str_date);
        return date.getTime();
    }

    public static String millisToDateString(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date;
        Date curDate = new Date(time);
        date = formatter.format(curDate);
        return date;
    }

    //判断手机上所有的网络设备是否可用
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //版本号
    public static int getVersionCode(Context context, String packageName) {
        return getPackageInfo(context, packageName).versionCode;
    }

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(packageName,
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    //将屏幕旋转锁定
    public static int setRoat(Context context){
        Settings.System.putInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0);
        //得到是否开启
        int flag = Settings.System.getInt(context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0);
        return  flag;
    }
}
