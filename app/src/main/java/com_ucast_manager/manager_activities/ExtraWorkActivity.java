package com_ucast_manager.manager_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.tools.BitmapUtils;
import com_ucast_manager.tools.MyDialog;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;

import static android.R.attr.data;
import static android.R.attr.id;
import static com_ucast_manager.manager_activities.WorkOrderActivity.SAVED_IMAGE_DIR_PATH;

@ContentView(R.layout.activity_extra_work)
public class ExtraWorkActivity extends AppCompatActivity {


    @ViewInject(R.id.tool_bar)
    Toolbar toolbar;

    @ViewInject(R.id.overtime_reson)
    EditText overtime_reson;
    @ViewInject(R.id.iv_image)
    ImageView iv_image;
    @ViewInject(R.id.overtime_signin)
    Button overtime_signin;
    @ViewInject(R.id.overtime_signout)
    Button overtime_signout;

    private String imagePath = "";
    private String extarwork_reson = "";
    private SavePasswd save;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        save = SavePasswd.getInstace();
        initToolbar(getString(R.string.ExtraWorkActivity));

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

    private boolean checkBeforePost() {

        if (overtime_reson.getVisibility()==View.VISIBLE) {
            extarwork_reson = overtime_reson.getText().toString().trim();
            if (extarwork_reson.equals("")) {
                MyDialog.showDialog(this, getString(R.string.check_reson_promot));
                return false;
            }else {
                File file = new File(imagePath);
                if (!file.exists()) {
                    MyDialog.showDialog(this, getString(R.string.check_image_promot));
                    return false;
                }else {
                    return true;
                }
            }
        }else {
            File file = new File(imagePath);
            if (!file.exists()) {
                MyDialog.showDialog(this, getString(R.string.check_image_promot));
                return false;
            }else {
                return true;
            }
        }
    }


    @Event(R.id.overtime_signin)
    private void sign_in(View v) {
        if (!checkBeforePost()){
            return;
        }
        progressDialog=MyDialog.createProgressDialog(this,getString(R.string.overtime_sign_in_ing));
        progressDialog.show();

        RequestParams requestParams = new RequestParams(MyTools.OVERTIME_SIGN_IN_URL);
        requestParams.setMultipart(true);
        requestParams.addHeader("Authorization", "Basic " + save.get(MyTools.TOKEN));
        requestParams.addBodyParameter("login_id", save.get(MyTools.LOGIN_ID));
        if (!MyTools.isOpenGPS(ExtraWorkActivity.this)) {
            MyTools.openGPS(ExtraWorkActivity.this);
        }
        requestParams.addBodyParameter("extra_work_start_gps", MyTools.getGPSConfi(ExtraWorkActivity.this));
        requestParams.addBodyParameter("extra_work_reason", extarwork_reson);
        requestParams.addBodyParameter("extra_work_start_time", "");
        if (new File(imagePath).exists()) {
            requestParams.addBodyParameter("extra_work_start_time_image", new File(imagePath));
        }else{
            requestParams.addBodyParameter("extra_work_start_time_image","");
            return;
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg baseReturnMsg = JSON.parseObject(result, BaseReturnMsg.class);
                if (baseReturnMsg.getResult().equals("true")) {
                    imagePath = "";
                    save.save(MyTools.WORK_STATE,"1");
                    save.save(MyTools.OVERTIME_ID, baseReturnMsg.getMsg().trim());
                    overtime_reson.setVisibility(View.GONE);
                    overtime_signin.setVisibility(View.GONE);
                    overtime_signout.setVisibility(View.VISIBLE);
                    if (new File(imagePath).exists()){
                        iv_image.setVisibility(View.VISIBLE);
                        iv_image.setImageDrawable(Drawable.createFromPath(imagePath));
                    } else {
                        iv_image.setVisibility(View.GONE);
                    }

                    MyDialog.showDialog(ExtraWorkActivity.this,ExtraWorkActivity.this.getString(R.string.overtime_sign_in_sucess));

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyDialog.showDialog(ExtraWorkActivity.this,ex.getMessage());
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


    @Event(R.id.overtime_signout)
    private void sign_out(View v) {
        if (!checkBeforePost()){
            return;
        }
        progressDialog=MyDialog.createProgressDialog(this,getString(R.string.overtime_sign_out_ing));
        progressDialog.show();

        RequestParams requestParams = new RequestParams(MyTools.OVERTIME_OUT_URL);
        requestParams.setMultipart(true);
        requestParams.addHeader("Authorization", "Basic " + save.get(MyTools.TOKEN));
        requestParams.addBodyParameter("login_id", save.get(MyTools.LOGIN_ID));
        if (!MyTools.isOpenGPS(ExtraWorkActivity.this)) {
            MyTools.openGPS(ExtraWorkActivity.this);
        }
        requestParams.addBodyParameter("id",save.get(MyTools.OVERTIME_ID));
        requestParams.addBodyParameter("extra_work_end_time_gps", MyTools.getGPSConfi(ExtraWorkActivity.this));
        requestParams.addBodyParameter("extra_work_end_time", "");
        if (new File(imagePath).exists()) {
            requestParams.addBodyParameter("extra_work_end_time_image", new File(imagePath));
        }else{
            requestParams.addBodyParameter("extra_work_end_time_image","");
        }

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg baseReturnMsg = JSON.parseObject(result, BaseReturnMsg.class);
                if (baseReturnMsg!=null&&baseReturnMsg.getResult().equals("true")){
                    imagePath = "";
                    save.save(MyTools.WORK_STATE, "0");
                    save.save(MyTools.OVERTIME_ID, "");
                    overtime_reson.setVisibility(View.VISIBLE);
                    overtime_signin.setVisibility(View.VISIBLE);
                    overtime_signout.setVisibility(View.GONE);
                    if (new File(imagePath).exists()){
                        iv_image.setVisibility(View.VISIBLE);
                        iv_image.setImageDrawable(Drawable.createFromPath(imagePath));
                    } else {
                        iv_image.setVisibility(View.GONE);
                    }

                    MyDialog.showDialog(ExtraWorkActivity.this,ExtraWorkActivity.this.getString(R.string.overtime_sign_out_sucess));
                    ExtraWorkActivity.this.finish();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyDialog.showDialog(ExtraWorkActivity.this,ex.getMessage());
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

    @Event(R.id.ib_camera_image)
    private void start_camera(View v) {

        imagePath = SAVED_IMAGE_DIR_PATH + save.get(MyTools.EMP_NAME) +
                MyTools.millisToDateStringNoSpace(System.currentTimeMillis()) + ".JPG";
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
        startActivityForResult(intent, WorkOrderActivity.CAMERA);

    }

    private static final String TAG = "ExtraWorkActivity";
    @Override
    protected void onResume() {
        String s = save.get(MyTools.WORK_STATE);
        if (s.equals("") || s.equals("0")) {
            overtime_reson.setVisibility(View.VISIBLE);
            overtime_signin.setVisibility(View.VISIBLE);
            overtime_signout.setVisibility(View.GONE);
        } else{
            overtime_reson.setVisibility(View.GONE);
            overtime_signin.setVisibility(View.GONE);
            overtime_signout.setVisibility(View.VISIBLE);
        }

        if (new File(imagePath).exists()){
            iv_image.setVisibility(View.VISIBLE);
            iv_image.setImageDrawable(Drawable.createFromPath(imagePath));
        } else {
            iv_image.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WorkOrderActivity.CAMERA && resultCode == RESULT_OK) {
            imagePath = BitmapUtils.compressImageUpload(imagePath);
            iv_image.setVisibility(View.VISIBLE);
            this.iv_image.setImageDrawable(Drawable.createFromPath(imagePath));
            return;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_msg_detials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.detail:
                //主界面左上角的icon点击反应
                startActivityForResult(new Intent(ExtraWorkActivity.this, ExtraWorkDetailActivity.class), 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
