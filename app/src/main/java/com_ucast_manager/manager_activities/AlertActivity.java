package com_ucast_manager.manager_activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com_ucast_manager.R;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;



public class AlertActivity extends AppCompatActivity {

    Button bt_success;
    EditText content;
    Spinner spinner;
    int type;
    String title;
    String[] spinner_msgs;
    SavePasswd save;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        type = intent.getIntExtra("type", 0);
        save= SavePasswd.getInstace();
        switch (type) {
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.CONSUMER_NAME:
                title=getResources().getString(R.string.consumer_name1);
                key=MyTools.RETURN_ALL_CUSTOMER;
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.WOKE_ORDER_TYPE:
                title=getResources().getString(R.string.woke_order_type1);
                key=MyTools.RETURN__ALL_WORKORDER_TYPE;
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.PRODUCT_MODEL:
                title=getResources().getString(R.string.product_model1);
                key=MyTools.RETURN__ALL_PRODUCT_MODLE;
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.PRODUCT_ID:
                title=getResources().getString(R.string.product_id1);
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.TROUBLES:
                title=getResources().getString(R.string.troubles1);
                key=MyTools.RETURN__TROUBLES;
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.HANDLE_WAYS:
                title=getResources().getString(R.string.handle_ways1);
                key=MyTools.RETURN__HANDLES;
                break;
            case com_ucast_manager.manager_activities.WorkOrderDetialActivity.HANDLE_MSGS:
                title=getResources().getString(R.string.handle_msgs1);
                break;
        }

        if (key!=null) {
            spinner_msgs = save.get(key).split(",");
            spinner_msgs[0] = getResources().getString(R.string.select);
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
        if (spinner_msgs!=null) {
            setMySpinner(spinner, spinner_msgs);
        }



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
    public void setMySpinner(Spinner spinner, String[] msgs) {
        // 建立Adapter并且绑定SSID数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                msgs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
    }
    public void closeAc(int type, String msg) {
        Intent intent = new Intent();
        intent.putExtra("result", msg);
        setResult(type, intent);
        AlertActivity.this.finish();
    }
}
