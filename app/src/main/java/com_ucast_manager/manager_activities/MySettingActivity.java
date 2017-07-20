package com_ucast_manager.manager_activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

import com_ucast_manager.R;
import com_ucast_manager.app.ExceptionApplication;
import com_ucast_manager.tools.MyDialog;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;

import static com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L;


public class MySettingActivity extends AppCompatActivity {

    LinearLayout apk_version;
    LinearLayout update;
    LinearLayout clear_cache;
    LinearLayout exit;

    Long fileSize = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar_work_order);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        apk_version = (LinearLayout) findViewById(R.id.apk_version);
        update = (LinearLayout) findViewById(R.id.update);
        clear_cache = (LinearLayout) findViewById(R.id.clear_cache);
        exit = (LinearLayout) findViewById(R.id.exit);


        apk_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfo packageInfo = MyTools.getPackageInfo(ExceptionApplication.context, "com.ucast.manager");
                String data = MySettingActivity.this.getResources().getString(R.string.banbengming) + packageInfo
                        .versionName;
                showDialog(data);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTools.getApkVersion(MySettingActivity.this, MyTools.UPDATE_APK_URL, true);
//                MyTools.showPadIsUpdate(MySettingActivity.this,"sdsdsdsad");
            }
        });


        clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clera_cache();
            }


        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIsueDialog(getResources().getString(R.string.isue_exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavePasswd.getInstace().save("info", "");
                        Intent intent = new Intent(MySettingActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void clera_cache() {
        File file = new File(WorkOrderActivity.SAVED_IMAGE_DIR_PATH);
        if (!file.exists() || file.list().length==0) {
            MyDialog.showToast(this, getString(R.string.no_cache));
            return;
        }
        fileSize = 0L;
        showIsueDialog(getString(R.string.is_sure_clear_cache), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = delAllFile(WorkOrderActivity.SAVED_IMAGE_DIR_PATH);
                if (result) {
                    showDialog(MySettingActivity.this.getString(R.string.clear_cache_size) + fileSize / 1024 / 1024 +
                            " M");
                } else {
                    showDialog(MySettingActivity.this.getString(R.string.clear_cache_falut));
                }
            }
        });

    }

    public void showDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton(this.getResources().getString(R
                .string.queding), null).create();
        alertDialog.setTitle(this.getResources().getString(R.string.tishi));
        alertDialog.setMessage(s);
        alertDialog.show();
    }

    public void showIsueDialog(String s, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton(this.getResources().getString(R
                .string.queding), listener).setNegativeButton(this.getResources().getString(R
                .string.cancle), null).create();
        alertDialog.setTitle(this.getResources().getString(R.string.tishi));
        alertDialog.setMessage(s);
        alertDialog.show();
    }


    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                fileSize += temp.length();
                temp.delete();
            }
        }
        flag = true;
        return flag;
    }

}
