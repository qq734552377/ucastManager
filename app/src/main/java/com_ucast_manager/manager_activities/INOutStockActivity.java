package com_ucast_manager.manager_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.entity.ResponseEntity;
import com_ucast_manager.tools.BitmapUtils;
import com_ucast_manager.tools.MyDialog;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;


/**
 * Created by Allen on 2017/6/14.
 */
@ContentView(R.layout.activity_in_out_stock)
public class INOutStockActivity extends AppCompatActivity {
    @ViewInject(R.id.tool_bar)
    Toolbar toolbar;
    @ViewInject(R.id.product_model)
    private Spinner product_model;
    @ViewInject(R.id.stock_type)
    private Spinner stock_type;
    @ViewInject(R.id.last_four_number)
    private TextView qrcode;
    @ViewInject(R.id.scan)
    private ImageButton btn_scan;
    @ViewInject(R.id.upload)
    private Button upload;



    private SavePasswd save;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        save = SavePasswd.getInstace();
        initToolbar(getString(R.string.INOutStockActivity));

        initview();

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(INOutStockActivity.this, ScanActivity.class), 1);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qrcodes = qrcode.getText().toString().trim();
                String productmodel="";
                String stocktype="";
                if (product_model.getSelectedItemPosition() != 0) {
                    productmodel = product_model.getSelectedItem().toString().trim();
                }
                if (stock_type.getSelectedItemPosition() != 0) {
                    stocktype = stock_type.getSelectedItem().toString().trim();
                }

                if(qrcodes.equals("")||productmodel.equals("")||stocktype.equals("")){
                    showDialog(getResources().getString(R.string.error_insert));
                }else {
                    String[] productidarray=qrcodes.split(" ");
                    for (int i = 0; i <productidarray.length ; i++) {
                        if (productidarray[i].trim().equals("")){
                            continue;
                        }
                        doInsertPost(productmodel,stocktype,productidarray[i].trim());
                    }
                }

            }
        });

    }

    public void initview(){
        progressDialog= MyDialog.createProgressDialog(this,getString(R.string.uploading));
        setMySelectSpinner(product_model, save.get(MyTools.RETURN__ALL_PRODUCT_MODLE));
        setMySelectSpinner(stock_type, save.get(MyTools.STOCK_TYPES));
    }

    public void doInsertPost(String productmodel,String stocktype,String q){
        progressDialog.show();
        final RequestParams requestParams=new RequestParams(MyTools.ADD_STOCK_URL);
        requestParams.addHeader("Authorization","Basic " + save.get("info"));
        requestParams.addBodyParameter("product_modle",productmodel);
        requestParams.addBodyParameter("qrcode",q);
        requestParams.addBodyParameter("stockType",stocktype);

        x.http().post(requestParams, new Callback.CommonCallback<ResponseEntity>() {
            @Override
            public void onSuccess(ResponseEntity result) {
                BaseReturnMsg base= JSON.parseObject(result.getResult(),BaseReturnMsg.class);
                if (base==null){
                    return;
                }
                showMsg(base.getMsg());

                if (base.getResult().equals("true")){
                    qrcode.setText("");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                showMsg(getResources().getString(R.string.error_insert_upload));
                showDialog(ex.getMessage());
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

    private void initToolbar(String s) {
        toolbar.setTitle(s);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setMySelectSpinner(Spinner spinner, String msgs) {
        String[] strs = msgs.split(",");
        strs[0] = getResources().getString(R.string.select);
        // 建立Adapter并且绑定SSID数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                strs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                String msg = data.getStringExtra("text");
                qrcode.append(msg + " ");
            }catch (Exception e){

            }

        }
    }

    public void showDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton(this.getResources().getString(R
                .string.queding), null).create();
        alertDialog.setTitle(this.getResources().getString(R.string.tishi));
        alertDialog.setMessage(s);
        alertDialog.show();
    }

    public void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
