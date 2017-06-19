package com.yanbober.com_ucast_manager.manager_activities;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.Result;
import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.entity.WorkOrerEntity;
import com.yanbober.com_ucast_manager.sample_activities.MainActivity;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static android.R.id.list;


public class WorkOrderDetialActivity extends AppCompatActivity implements View.OnClickListener {

    TextView consumer_name;
    TextView woke_order_type;
    TextView serviceman;
    TextView product_model;
    TextView product_id;
    TextView troubles;
    TextView handle_ways;
    TextView date;
    TextView handle_msgs;

    Button detial_alter;
    Button sumbit;
    Button cancle;

    ImageButton alert_consumer_name;
    ImageButton alert_woke_order_type;
    ImageButton alert_product_model;
    ImageButton alert_product_id;
    ImageButton alert_troubles;
    ImageButton alert_handle_ways;
    ImageButton alert_handle_msgs;


    public static final int CONSUMER_NAME = 1001;
    public static final int WOKE_ORDER_TYPE = 1003;
    public static final int PRODUCT_MODEL = 1005;
    public static final int PRODUCT_ID = 1006;
    public static final int TROUBLES = 1007;
    public static final int HANDLE_WAYS = 1009;
    public static final int HANDLE_MSGS = 1010;


    public WorkOrerEntity workOrerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order_detial);
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

//
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        workOrerEntity= (WorkOrerEntity) bundle.getSerializable("msg");

        init();


//        showMsg();
//        consumer_name.setText(list.get(0));
//        woke_order_type.setText(list.get(1));
//        serviceman.setText(list.get(2));
//        product_model.setText(list.get(3));
//        product_id.setText(list.get(4));
//        troubles.setText(list.get(5));
//        handle_ways.setText(list.get(6));
//        date.setText(list.get(7));
//        handle_msgs.setText(list.get(8));


    }
    public void showMsg(){
        consumer_name.setText(workOrerEntity.getCustomerName());
        woke_order_type.setText(workOrerEntity.getWorkOrderType());
        serviceman.setText(workOrerEntity.getServiceman());
        product_model.setText(workOrerEntity.getProductType());
        product_id.setText(workOrerEntity.getId());
        troubles.setText(workOrerEntity.getTruble());
        handle_ways.setText(workOrerEntity.getHandleWays());
        date.setText(workOrerEntity.getDate());
        handle_msgs.setText(workOrerEntity.getHandleMsgs());
    }

    @Subscribe(threadMode = ThreadMode.MainThread,sticky = true)
    public void showMsg(WorkOrerEntity entity){
        workOrerEntity=entity;
        showViewMsg();
    }

    private void showViewMsg() {
        consumer_name.setText(workOrerEntity.getCustomerName());
        woke_order_type.setText(workOrerEntity.getWorkOrderType());
        serviceman.setText(workOrerEntity.getServiceman());
        product_model.setText(workOrerEntity.getProductType());
        product_id.setText(workOrerEntity.getId());
        troubles.setText(workOrerEntity.getTruble());
        handle_ways.setText(workOrerEntity.getHandleWays());
        date.setText(workOrerEntity.getDate());
        handle_msgs.setText(workOrerEntity.getHandleMsgs());
    }

    private void init() {

        consumer_name = (TextView) findViewById(R.id.consumer_name);
        woke_order_type = (TextView) findViewById(R.id.woke_order_type);
        serviceman = (TextView) findViewById(R.id.serviceman);
        product_model = (TextView) findViewById(R.id.product_model);
        product_id = (TextView) findViewById(R.id.product_id);
        troubles = (TextView) findViewById(R.id.troubles);
        handle_ways = (TextView) findViewById(R.id.handle_ways);
        date = (TextView) findViewById(R.id.date);
        handle_msgs = (TextView) findViewById(R.id.handle_msgs);


        detial_alter = (Button) findViewById(R.id.detial_alter);
        sumbit = (Button) findViewById(R.id.sumbit);
        cancle = (Button) findViewById(R.id.cancle);


        alert_consumer_name = (ImageButton) findViewById(R.id.alert_consumer_name);
        alert_woke_order_type = (ImageButton) findViewById(R.id.alert_woke_order_type);
        alert_product_model = (ImageButton) findViewById(R.id.alert_product_model);
        alert_product_id = (ImageButton) findViewById(R.id.alert_product_id);
        alert_troubles = (ImageButton) findViewById(R.id.alert_troubles);
        alert_handle_ways = (ImageButton) findViewById(R.id.alert_handle_ways);
        alert_handle_msgs = (ImageButton) findViewById(R.id.alert_handle_msgs);

        detial_alter.setOnClickListener(this);
        sumbit.setOnClickListener(this);
        cancle.setOnClickListener(this);

        alert_consumer_name.setOnClickListener(this);
        alert_woke_order_type.setOnClickListener(this);
        alert_product_model.setOnClickListener(this);
        alert_product_id.setOnClickListener(this);
        alert_troubles.setOnClickListener(this);
        alert_handle_ways.setOnClickListener(this);
        alert_handle_msgs.setOnClickListener(this);


        hiddenAll();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //toolbar修改
            case R.id.detial_alter:
                showAll();
                break;

            //toolbar提交
            case R.id.sumbit:
                if (isAlter()){
                    doAlter();
                    finish();
                }else{
                    Snackbar.make(sumbit,"没有瞎改任何内容",Snackbar.LENGTH_LONG).show();
                    hiddenAll();
                }
                break;

            //toolbar取消
            case R.id.cancle:
                hiddenAll();
                showViewMsg();
                break;

            //客户名修改
            case R.id.alert_consumer_name:
                startAc(CONSUMER_NAME, consumer_name.getText().toString().trim());
                break;

            //工单类型修改
            case R.id.alert_woke_order_type:
                startAc(WOKE_ORDER_TYPE, woke_order_type.getText().toString().trim());
                break;

            //产品型号修改
            case R.id.alert_product_model:
                startAc(PRODUCT_MODEL, product_model.getText().toString().trim());
                break;

            //产品ID修改
            case R.id.alert_product_id:
                startAc(PRODUCT_ID, product_id.getText().toString().trim());
                break;

            //问题现象修改
            case R.id.alert_troubles:
                startAc(TROUBLES, troubles.getText().toString().trim());
                break;

            //处理方式修改
            case R.id.alert_handle_ways:
                startAc(HANDLE_WAYS, handle_ways.getText().toString().trim());
                break;

            //处理信息修改
            case R.id.alert_handle_msgs:
                startAc(HANDLE_MSGS, handle_msgs.getText().toString().trim());
                break;
        }
    }

    private void doAlter() {
        Snackbar.make(sumbit,"修改",Snackbar.LENGTH_LONG).show();
    }


    public void showAll() {
        detial_alter.setVisibility(View.GONE);

        sumbit.setVisibility(View.VISIBLE);
        cancle.setVisibility(View.VISIBLE);
        alert_consumer_name.setVisibility(View.VISIBLE);
        alert_woke_order_type.setVisibility(View.VISIBLE);
        alert_product_model.setVisibility(View.VISIBLE);
        alert_product_id.setVisibility(View.VISIBLE);
        alert_troubles.setVisibility(View.VISIBLE);
        alert_handle_ways.setVisibility(View.VISIBLE);
        alert_handle_msgs.setVisibility(View.VISIBLE);

    }

    public void hiddenAll() {
        detial_alter.setVisibility(View.VISIBLE);

        sumbit.setVisibility(View.GONE);
        cancle.setVisibility(View.GONE);
        alert_consumer_name.setVisibility(View.GONE);
        alert_woke_order_type.setVisibility(View.GONE);
        alert_product_model.setVisibility(View.GONE);
        alert_product_id.setVisibility(View.GONE);
        alert_troubles.setVisibility(View.GONE);
        alert_handle_ways.setVisibility(View.GONE);
        alert_handle_msgs.setVisibility(View.GONE);
    }

    public void startAc(int type, String msg) {
        Intent intent = new Intent(WorkOrderDetialActivity.this, AlertActivity.class);
        intent.putExtra("msg", msg);
        intent.putExtra("type", type);
        startActivityForResult(intent, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case CONSUMER_NAME:
                consumer_name.setText(data.getStringExtra("result"));
                break;
            case WOKE_ORDER_TYPE:
                woke_order_type.setText(data.getStringExtra("result"));
                break;
            case PRODUCT_MODEL:
                product_model.setText(data.getStringExtra("result"));
                break;
            case PRODUCT_ID:
                product_id.setText(data.getStringExtra("result"));
                break;
            case TROUBLES:
                troubles.setText(data.getStringExtra("result"));
                break;
            case HANDLE_WAYS:
                handle_ways.setText(data.getStringExtra("result"));
                break;
            case HANDLE_MSGS:
                handle_msgs.setText(data.getStringExtra("result"));
                break;
        }
    }

    public boolean isAlter(){
        boolean result= !consumer_name.getText().toString().trim().equals(workOrerEntity.getCustomerName())
                ||!woke_order_type.getText().toString().trim().equals(workOrerEntity.getWorkOrderType())
                ||!product_model.getText().toString().trim().equals(workOrerEntity.getProductType())
                ||!product_id.getText().toString().trim().equals(workOrerEntity.getId())
                ||!troubles.getText().toString().trim().equals(workOrerEntity.getTruble())
                ||!handle_ways.getText().toString().trim().equals(workOrerEntity.getHandleWays())
                ||!handle_msgs.getText().toString().trim().equals(workOrerEntity.getHandleMsgs());
        return result;
    }

}


