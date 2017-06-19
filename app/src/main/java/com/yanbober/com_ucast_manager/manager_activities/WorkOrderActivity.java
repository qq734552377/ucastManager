package com.yanbober.com_ucast_manager.manager_activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yanbober.com_ucast_manager.R;


public class WorkOrderActivity extends AppCompatActivity {

    private Button btn_suc;
    private ImageButton btn_scan;

    private TextView last_four_number;

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
        collapsingToolbar.setTitle("工单填写");


        btn_suc = (Button) findViewById(R.id.sumbit);
        btn_scan = (ImageButton) findViewById(R.id.scan);
        last_four_number = (TextView) findViewById(R.id.last_four_number);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WorkOrderActivity.this, ScanActivity.class), 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String msg = data.getStringExtra("text");
            last_four_number.append(msg + " ");
        }
    }

}
