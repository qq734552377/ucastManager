package com_ucast_manager.manager_activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.entity.ResponseEntity;
import com_ucast_manager.sample_activities.MainActivity;
import com_ucast_manager.tools.BitmapUtils;
import com_ucast_manager.tools.MyDialog;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.handle;
import static android.R.attr.id;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static com_ucast_manager.R.id.overtime_reson;
import static com_ucast_manager.R.id.overtime_signin;
import static com_ucast_manager.R.id.overtime_signout;


public class WorkOrderActivity extends AppCompatActivity {

    private Button btn_suc;
    private Button cancle;
    private ImageButton btn_scan;
    private ImageButton btn_camera;
    private ImageView iv_image;

    private EditText last_four_number;
    private EditText handle_msgs;


    private Spinner consumer_name;
    private Spinner product_model;
    private Spinner woke_order_type;
    private Spinner troubles;
    private Spinner handle_ways;

    private SavePasswd save;
    private String  imagePath="";

    ProgressDialog dialog2 ;
    public static final int CAMERA = 777;
    public static final String SAVED_IMAGE_DIR_PATH = Environment.getExternalStorageDirectory().toString()+"/Ucast/ucast_manager/camera/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_order);

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

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_work_order);
        collapsingToolbar.setTitle(getResources().getString(R.string.WorkOrderActivity));


        initview();




        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WorkOrderActivity.this, ScanActivity.class), 1);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePath = SAVED_IMAGE_DIR_PATH +save.get(MyTools.EMP_NAME)+
                        MyTools.millisToDateStringNoSpace(System.currentTimeMillis())+".JPG" ;
                Intent intent = new Intent();
                // 指定开启系统相机的Action
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                String out_file_path = SAVED_IMAGE_DIR_PATH;
                File dir = new File(out_file_path);
                if (!dir.exists()) {
                    dir.mkdirs();
                } // 把文件地址转换成Uri格式
                Uri uri = Uri.fromFile(new File(imagePath));
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent,CAMERA);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customername = "";
                String producttype = "";
                String workordertype = "";
                String productid = last_four_number.getText().toString().trim();
                String trouble = "";
                String handleway = "";
                String handlemsg = handle_msgs.getText().toString().trim();


                if (consumer_name.getSelectedItemPosition() != 0) {
                    customername = consumer_name.getSelectedItem().toString().trim();
                }

                if (product_model.getSelectedItemPosition() != 0) {
                    producttype = product_model.getSelectedItem().toString().trim();
                }
                if (woke_order_type.getSelectedItemPosition() != 0) {
                    workordertype = woke_order_type.getSelectedItem().toString().trim();
                }
                if (troubles.getSelectedItemPosition() != 0) {
                    trouble = troubles.getSelectedItem().toString().trim();
                }
                if (handle_ways.getSelectedItemPosition() != 0) {
                    handleway = handle_ways.getSelectedItem().toString().trim();
                }


                if(customername.equals("")||producttype.equals("")||workordertype.equals("")
                        ||trouble.equals("")||handleway.equals("")||productid.equals("")
                        ){
                        showDialog(getResources().getString(R.string.error_insert));
                }else{
                        String[] productidarray=productid.split(" ");
                        for (int i = 0; i <productidarray.length ; i++) {
                            if (productidarray[i].trim().equals("")){
                                continue;
                            }
                            doInsertPost(customername,producttype,workordertype,productidarray[i].trim(),trouble,handleway,handlemsg);
                        }
                }


            }
        });

    }

    private void initview() {
        dialog2 = new ProgressDialog(this);
        dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog2.setCancelable(false);// 设置是否可以通过点击Back键取消
        dialog2.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        dialog2.setTitle(this.getResources().getString(R.string.tishi));
        dialog2.setMessage(this.getResources().getString(R.string.adding));


        btn_suc = (Button) findViewById(R.id.sumbit);
        cancle = (Button) findViewById(R.id.cancle);
        btn_scan = (ImageButton) findViewById(R.id.scan);
        btn_camera = (ImageButton) findViewById(R.id.ib_camera_image);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_image.setVisibility(View.GONE);
        last_four_number = (EditText) findViewById(R.id.last_four_number);
        handle_msgs = (EditText) findViewById(R.id.handle_msgs);

        consumer_name = (Spinner) findViewById(R.id.consumer_name);
        product_model = (Spinner) findViewById(R.id.product_model);
        woke_order_type = (Spinner) findViewById(R.id.woke_order_type);
        troubles = (Spinner) findViewById(R.id.troubles);
        handle_ways = (Spinner) findViewById(R.id.handle_ways);

        save = SavePasswd.getInstace();

        setMySelectSpinner(consumer_name, save.get(MyTools.RETURN_ALL_CUSTOMER));
        setMySelectSpinner(product_model, save.get(MyTools.RETURN__ALL_PRODUCT_MODLE));
        setMySelectSpinner(woke_order_type, save.get(MyTools.RETURN__ALL_WORKORDER_TYPE));
        setMySelectSpinner(troubles, save.get(MyTools.RETURN__TROUBLES));
        setMySelectSpinner(handle_ways, save.get(MyTools.RETURN__HANDLES));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==CAMERA&&resultCode==RESULT_OK){
            String path=  BitmapUtils.compressImageUpload(imagePath);
            SavePasswd.getInstace().save("work_order_image",path);
            iv_image.setVisibility(View.VISIBLE);
            this.iv_image.setImageDrawable(Drawable.createFromPath(path));
            return;

        }

        if (resultCode == RESULT_OK) {
            try {
                String msg = data.getStringExtra("text");
                last_four_number.append(msg + " ");
            }catch (Exception e){

            }

        }
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


    public void doInsertPost(String customername,String producttype,String workordertype,String productid,String trouble,String handleway,String handlemsg){
        dialog2.show();
        final RequestParams requestParams=new RequestParams(MyTools.INSERT_URL);
        requestParams.addHeader("Authorization","Basic " + save.get("info"));
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("customer_name",customername);
        requestParams.addBodyParameter("product_modle",producttype);
        requestParams.addBodyParameter("work_order_type",workordertype);
        requestParams.addBodyParameter("product_id",productid);
        requestParams.addBodyParameter("troubles",trouble);
        requestParams.addBodyParameter("handle_ways",handleway);
        requestParams.addBodyParameter("login_id",save.get("login_id"));
        requestParams.addBodyParameter("create_date",MyTools.millisToDateString(System.currentTimeMillis()));
        requestParams.addBodyParameter("handle_message",handlemsg);
        requestParams.addBodyParameter("alter_login_id",save.get("login_id"));
        String s=save.get(MyTools.WORK_STATE);
        if (s.equals("") || s.equals("0")) {
           s="0";
        } else{
            s="1";
        }
        requestParams.addBodyParameter("work_order_extra",s);

        if(new File(imagePath).exists()){
            requestParams.addBodyParameter("work_order_image",new File(imagePath));
        }else{
            requestParams.addBodyParameter("work_order_image","");
        }
        if (!MyTools.isOpenGPS(WorkOrderActivity.this)){
            MyTools.openGPS(WorkOrderActivity.this);
        }
        requestParams.addBodyParameter("gps",MyTools.getGPSConfi(WorkOrderActivity.this));

        x.http().post(requestParams, new Callback.CommonCallback<ResponseEntity>() {
            @Override
            public void onSuccess(ResponseEntity result) {
                BaseReturnMsg base= JSON.parseObject(result.getResult(),BaseReturnMsg.class);
                if (base==null){
                    return;
                }
                showMsg(base.getMsg());

                if (base.getResult().equals("true")){
                    finish();
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
                dialog2.cancel();
            }
        });
    }

}
