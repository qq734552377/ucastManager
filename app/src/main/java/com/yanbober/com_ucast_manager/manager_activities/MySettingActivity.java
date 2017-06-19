package com.yanbober.com_ucast_manager.manager_activities;

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

import static com.yanbober.com_ucast_manager.tools.MyTools.getPackageInfo;


public class MySettingActivity extends AppCompatActivity {

    LinearLayout apk_version;
    LinearLayout update;


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

}
