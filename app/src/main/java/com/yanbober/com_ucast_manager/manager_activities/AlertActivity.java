package com.yanbober.com_ucast_manager.manager_activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.yanbober.com_ucast_manager.R;

import static android.R.attr.data;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.CONSUMER_NAME;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.HANDLE_MSGS;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.HANDLE_WAYS;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.PRODUCT_ID;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.PRODUCT_MODEL;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.TROUBLES;
import static com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity.WOKE_ORDER_TYPE;


public class AlertActivity extends AppCompatActivity {

    Button bt_success;
    EditText content;
    Spinner spinner;
    int type;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        type = intent.getIntExtra("type", 0);

        switch (type) {
            case WorkOrderDetialActivity.CONSUMER_NAME:
                title=getResources().getString(R.string.consumer_name1);
                break;
            case WorkOrderDetialActivity.WOKE_ORDER_TYPE:
                title=getResources().getString(R.string.woke_order_type1);
                break;
            case WorkOrderDetialActivity.PRODUCT_MODEL:
                title=getResources().getString(R.string.product_model1);
                break;
            case WorkOrderDetialActivity.PRODUCT_ID:
                title=getResources().getString(R.string.product_id1);
                break;
            case WorkOrderDetialActivity.TROUBLES:
                title=getResources().getString(R.string.troubles1);
                break;
            case WorkOrderDetialActivity.HANDLE_WAYS:
                title=getResources().getString(R.string.handle_ways1);
                break;
            case WorkOrderDetialActivity.HANDLE_MSGS:
                title=getResources().getString(R.string.handle_msgs1);
                break;
        }

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.tool_bar_work_order);
        toolbar.setTitle(getResources().getString(R.string.alter)+title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bt_success = (Button) findViewById(R.id.success);
        content = (EditText) findViewById(R.id.content);
        spinner = (Spinner) findViewById(R.id.alert_spinner);






        if ((type % 2) != 0) {
            spinner.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        } else {
            spinner.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
            content.setText(msg);
        }


        bt_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                if ((type % 2) != 0) {
                    msg = spinner.getSelectedItem().toString().trim();
                } else {
                    msg = content.getText().toString().trim();
                }
                closeAc(type,msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void closeAc(int type, String msg) {
        Intent intent = new Intent();
        intent.putExtra("result", msg);
        setResult(type, intent);
        AlertActivity.this.finish();
    }
}
