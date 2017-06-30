package com.yanbober.com_ucast_manager.tools;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.app.ExceptionApplication;
import com.yanbober.com_ucast_manager.entity.BaseReturnMsg;
import com.yanbober.com_ucast_manager.entity.BaseSpinnerReturnMsg;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 2017/6/13.
 */

public class MyTools {
//    public static final String LOGIN_URL="http://192.168.0.132/mydemo/login.php";
//    public static final String RETURN_ALL_CUSTOMER="http://192.168.0.132/mydemo/returnallcustomer.php";
//    public static final String RETURN__ALL_PRODUCT_MODLE="http://192.168.0.132/mydemo/returnallproductmodle.php";
//    public static final String RETURN__ALL_WORKORDER_TYPE="http://192.168.0.132/mydemo/returnallworkordertype.php";
//    public static final String RETURN__ALL_EMP_NAME="http://192.168.0.132/mydemo/returnempname.php";
//    public static final String RETURN__HANDLES="http://192.168.0.132/mydemo/returnhandles.php";
//    public static final String RETURN__TROUBLES="http://192.168.0.132/mydemo/returntroubles.php";
//    public static final String QUERY_URL="http://192.168.0.132/mydemo/query.php";
//    public static final String INSERT_URL="http://192.168.0.132/mydemo/insert.php";

    public static final String LOGIN_URL="http://192.168.0.113:12306/Track/Login";
    public static final String RETURN_ALL_CUSTOMER="http://192.168.0.113:12306/Customer/GetCustomerName";
    public static final String RETURN__ALL_PRODUCT_MODLE="http://192.168.0.113:12306/ProductModle/GetProductModle";
    public static final String RETURN__ALL_WORKORDER_TYPE="http://192.168.0.113:12306/WorkOrderType/GetWorkOrderType";
    public static final String RETURN__ALL_EMP_NAME="http://192.168.0.113:12306/EmpName/GetEmpName";
    public static final String RETURN__HANDLES="http://192.168.0.113:12306/HandleWays/GetHandleWays";
    public static final String RETURN__TROUBLES="http://192.168.0.113:12306/Trouble/GetTrouble";
    public static final String QUERY_URL="http://192.168.0.113:12306/MaintenanceOrder/GetList";
    public static final String INSERT_URL="http://192.168.0.113:12306/MaintenanceOrder/Add";
    public static final String UPDATE_URL="http://192.168.0.113:12306/MaintenanceOrder/Update";
    public static final String UQUERY_ID_URL="http://192.168.0.113:12306/MaintenanceOrder/QueryID";






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
    public static String millisToDateStringOnlyYMD(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

    public static void doSpinnerPost(final String url){
        RequestParams requestParams = new RequestParams(url);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);

                if (base.getResult().equals("true")) {
                    List<BaseSpinnerReturnMsg> msg = JSON.parseArray(base.getDate(), BaseSpinnerReturnMsg.class);
                    String cusmsg = ExceptionApplication.getInstance().getResources().getString(R.string.all);
                    for (int i = 0; i < msg.size(); i++) {
                        if (i == msg.size() - 1) {
                            cusmsg = cusmsg + msg.get(i).getHeader_msg();
                        } else {
                            cusmsg = cusmsg + msg.get(i).getHeader_msg() + ",";
                        }
                    }
                    Log.e("   ", "onSuccess: "+cusmsg );
                    SavePasswd.getInstace().save(url,cusmsg);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 判断手机GPS是否开启
     * @param
     * @return
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位,定位级别到街
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或者移动网络确定位置
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 开启手机GPS
     */
    public static  void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvide");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));

        try {
            //使用PendingIntent发送广播告诉手机去开启GPS功能
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
    /**
     * GPS功能已经打开-->根据GPS去获取经纬度
     */
    public static String getGPSConfi(Context context) {
        Location location;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位,定位级别到街
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或者移动网络确定位置
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return latitude + "-" + longitude;
        } else {
            return "00-00";
        }
    }



}
