package com.yanbober.com_ucast_manager.manager_activities;

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

import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.app.ExceptionApplication;
import com.yanbober.com_ucast_manager.tools.MyTools;
import com.yanbober.com_ucast_manager.tools.SavePasswd;



public class MySettingActivity extends AppCompatActivity {

    LinearLayout apk_version;
    LinearLayout update;
    LinearLayout exit;


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
        exit = (LinearLayout) findViewById(R.id.exit);


        apk_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfo packageInfo = MyTools.getPackageInfo(ExceptionApplication.context, "com.ucast.manager");
                String data=MySettingActivity.this.getResources().getString(R.string.banbengming)+packageInfo.versionName;
                showDialog(data);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTools.getApkVersion(MySettingActivity.this,MyTools.UPDATE_APK_URL,true);
//                MyTools.showPadIsUpdate(MySettingActivity.this,"sdsdsdsad");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIsueDialog(getResources().getString(R.string.isue_exit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SavePasswd.getInstace().save("info","");
                        Intent intent=new Intent(MySettingActivity.this,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
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

    public void showIsueDialog(String s, DialogInterface.OnClickListener listener){
        AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton(this.getResources().getString(R
                .string.queding), listener).setNegativeButton(this.getResources().getString(R
                .string.cancle),null).create();
        alertDialog.setTitle(this.getResources().getString(R.string.tishi));
        alertDialog.setMessage(s);
        alertDialog.show();
    }

}
