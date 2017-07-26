package com_ucast_manager.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com_ucast_manager.R;
import com_ucast_manager.app.ExceptionApplication;
import com_ucast_manager.entity.AppUpdateMsg;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.entity.BaseSpinnerReturnMsg;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com_ucast_manager.app.ExceptionApplication.context;

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

    public static final String LOGIN_URL = "http://www.ucastcomputer.com:12842/Track/Login";
    public static final String RETURN_ALL_CUSTOMER = "http://www.ucastcomputer.com:12842/Customer/GetCustomerName";
    public static final String RETURN__ALL_PRODUCT_MODLE = "http://www.ucastcomputer.com:12842/ProductModle/GetProductModle";
    public static final String RETURN__ALL_WORKORDER_TYPE = "http://www.ucastcomputer.com:12842/WorkOrderType/GetWorkOrderType";
    public static final String RETURN__ALL_EMP_NAME = "http://www.ucastcomputer.com:12842/EmpName/GetEmpName";
    public static final String RETURN__HANDLES = "http://www.ucastcomputer.com:12842/HandleWays/GetHandleWays";
    public static final String RETURN__TROUBLES = "http://www.ucastcomputer.com:12842/Trouble/GetTrouble";
    public static final String QUERY_URL = "http://www.ucastcomputer.com:12842/MaintenanceOrder/GetList";
    public static final String INSERT_URL = "http://www.ucastcomputer.com:12842/MaintenanceOrder/Add";
    public static final String UPDATE_URL = "http://www.ucastcomputer.com:12842/MaintenanceOrder/Update";
    public static final String UQUERY_ID_URL = "http://www.ucastcomputer.com:12842/MaintenanceOrder/QueryID";
    public static final String UPDATE_APK_URL = "http://www.ucastcomputer.com:12842/Upgrade/GetUpgrade";
    public static final String OVERTIME_SIGN_IN_URL = "http://www.ucastcomputer.com:12842/Extra/Start";
    public static final String OVERTIME_OUT_URL = "http://www.ucastcomputer.com:12842/Extra/End";
    public static final String OVERTIME_QUERRY_URL = "http://www.ucastcomputer.com:12842/Extra/GetMouthList";



    public static final String TOKEN ="info";
    public static final String LOGIN_ID ="login_id";
    public static final String PASSWORD ="password";
    public static final String EMP_NAME ="emp_name";
    public static final String COMPANY_NAME ="company_name";
    public static final String GROUP_ID ="group_id";
    public static final String ROLE ="role";
    public static final String EMP_PHONENUMBER ="emp_phonenumber";
    public static final String EMP_EMIAL ="emp_emial";
    public static final String CREATE_DATE ="create_date";
    public static final String WORK_STATE ="work_state";
    public static final String OVERTIME_ID ="overtime_id";


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


    public static String millisToDateStringNoSpace(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
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
    public static int setRoat(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        //得到是否开启
        int flag = Settings.System.getInt(context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0);
        return flag;
    }

    public static void doSpinnerPost(final String url) {
        RequestParams requestParams = new RequestParams(url);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);

                if (base.getResult().equals("true")) {
                    List<BaseSpinnerReturnMsg> msg = JSON.parseArray(base.getDate(), BaseSpinnerReturnMsg.class);
                    String cusmsg = ExceptionApplication.getInstance().getResources().getString(R.string.all)+",";
                    for (int i = 0; i < msg.size(); i++) {
                        if (i == msg.size() - 1) {
                            cusmsg = cusmsg + msg.get(i).getHeader_msg();
                        } else {
                            cusmsg = cusmsg + msg.get(i).getHeader_msg() + ",";
                        }
                    }
                    SavePasswd.getInstace().save(url, cusmsg);
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
     *
     * @param
     * @return
     */
    public static boolean isOpenGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //通过GPS卫星定位,定位级别到街
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //通过WLAN或者移动网络确定位置
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps && network) {
            return true;
        }
        return false;
    }

    /**
     * 开启手机GPS
     */
    public static void openGPS(Context context) {
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

    public static void getApkVersion(final Context context, String getVersionUrl, final boolean isShow) {
        if (!isNetworkAvailable(ExceptionApplication.getInstance())) {
            return;
        }
        RequestParams params = new RequestParams(getVersionUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                BaseReturnMsg base = JSON.parseObject(s, BaseReturnMsg.class);
                AppUpdateMsg appUpdateMsg = JSON.parseObject(base.getDate(), AppUpdateMsg.class);
                String new_version = appUpdateMsg.getVersion();
                int new_size = Integer.parseInt(appUpdateMsg.getSize().trim());
                String url = appUpdateMsg.getUrl();
                String packageName = MyAppManager.getAppProcessName(ExceptionApplication.getInstance());
                PackageInfo packageInfo = getPackageInfo(ExceptionApplication.getInstance(), packageName);

                String app_version = packageInfo.versionName;
//                showPadIsUpdate(context,new_size+"  "+new_version+"  "+url);
                if (!app_version.equals(new_version)) {
                    long apk_size = 0;
                    String path_base = Environment.getExternalStorageDirectory().toString() + "/Ucast/ucast_manager"
                            + packageName
                            + ".apk";
                    try {
                        apk_size = MyTools.getFileSizes(path_base);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String apk_version = MyTools.getApkversion(path_base);
//                    showPadIsUpdate(context, "onSuccess1  " + path_base + " " + apk_version + "   " + new_version);
                    if (new_size == apk_size) {
                        if (apk_version.equals(new_version)) {
                            showPadIsUpdate(context, new File(path_base));
                        } else {
                            //版本不对重新下载
                            MyTools.downApkFile(context, url, path_base);
                        }
                    } else {
                        MyTools.downApkFile(context, url, path_base);
                    }
                } else {
                    if (isShow) {
                        showPadIsUpdate(context, context.getResources().getString(R.string.the_new_version) +
                                app_version);
                    }
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    //下载apk文件
    public static void downApkFile(final Context context, final String url, final String path) {
        if (!isNetworkAvailable(ExceptionApplication.getInstance())) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(context);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestParams requestParams = new RequestParams(url);
                requestParams.setSaveFilePath(path);
                x.http().get(requestParams, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onWaiting() {
                    }

                    @Override
                    public void onStarted() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        if (!progressDialog.isShowing()) {
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setMessage(context.getResources().getString(R.string.downloading));
                            progressDialog.show();
                            progressDialog.setMax((int) total);
                        }

                        progressDialog.setProgress((int) current);

                    }

                    @Override
                    public void onSuccess(File result) {
                        showPadIsUpdate(context, result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ex.printStackTrace();

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                        progressDialog.cancel();
                    }
                });
            }
        };

        showPadIsUpdate(context, onClickListener);
    }

    public static void showPadIsUpdate(final Context context, DialogInterface.OnClickListener
            positiveOnclickListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.tishi));
        builder.setMessage(ExceptionApplication.getInstance().getResources().getString(R.string
                .is_download_new_apk));
        builder.setPositiveButton(context.getResources().getString(R.string.queding), positiveOnclickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.cancle), null);
        builder.create().show();

    }


    public static void showPadIsUpdate(final Context context, final File file) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.tishi));
        builder.setMessage( ExceptionApplication.getInstance().getResources().getString(R.string
                .is_update_new_version));
        builder.setPositiveButton(context.getResources().getString(R.string.queding), new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                //跳转到系统的安装应用页面
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancle), null);
        builder.create().show();

    }

    public static void showPadIsUpdate(Context context, final String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.tishi));
        builder.setMessage(msg);
        builder.setPositiveButton(context.getResources().getString(R.string.queding), new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        builder.setNegativeButton(context.getResources().getString(R.string.cancle), null);
        builder.create().show();
    }


    /***
     * 获取文件大小
     ***/
    public static long getFileSizes(String apkPath) throws Exception {
        File f = new File(apkPath);
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
        }
        return s;
    }

    /***
     * 获取apk文件的的版本号
     ***/
    public static String getApkversion(String path) {

        File f = new File(path);
        if (!f.exists()) {
            return "0.0";
        }
        PackageInfo packageInfo;
        try {
            PackageManager packageManager = ExceptionApplication.getInstance().getPackageManager();
            packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            return "1.0";
        }
        return packageInfo == null ? "0.0" : packageInfo.versionName;
    }
}
