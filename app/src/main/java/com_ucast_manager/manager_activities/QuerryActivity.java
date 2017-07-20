package com_ucast_manager.manager_activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.entity.WorkorderMSg;
import com_ucast_manager.my_adapter.SimpleMsgCanScrollAdapter;
import com_ucast_manager.entity.WorkOrerEntity;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import static com_ucast_manager.R.layout.item;


public class QuerryActivity extends AppCompatActivity {


    ArrayList<WorkorderMSg> lists;
    RecyclerView mRecyclerView;
    SimpleMsgCanScrollAdapter adapter;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;

    private SavePasswd save;
    private static final String TAG = "QuerryActivity";


    private TextView username;
    private TextView count;
    private TextView work_state;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querry);


        initView();
        initData();


        lists = new ArrayList<>();
        lists.add(null);
        lists.add(null);

        adapter = new SimpleMsgCanScrollAdapter(lists, this);
        adapter.setGetMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                lists.remove(lists.size() - 1);
//                for (int i = 15; i < 25; i++) {
//                    lists.add(new WorkOrerEntity("泰国" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326",
//                            "工作人员甲", "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池   拆机检查,内部硬件完好,电池膨胀,更换电池拆机检查,内部硬件完好,电池膨胀,更换电池  " +
//                            "拆机检查,内部硬件完好,电池膨胀,更换电池 机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池
// " +
//                            "机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池", "2016-05-12 14:56"));
//                }
//                lists.add(null);
//                adapter.notifyDataSetChanged();
                adapter.header.getMoreData("");

            }
        });


//        adapter.setQuerryClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!lists.isEmpty()) {
//                    lists.clear();
//                }
//                lists.add(null);
//              for (int i = 0; i < 15; i++) {
//                   lists.add(new WorkOrerEntity("SMART" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326",
//                           "工作人员甲",
//                           "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池 拆", "2016-05-12 14:56"));
//                }
//                lists.add(null);
//                adapter.notifyDataSetChanged();
//            }
//        });
        mRecyclerView.setAdapter(adapter);


    }

    private void initView() {
        save = SavePasswd.getInstace();

        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        username = (TextView) findViewById(R.id.username);
        count = (TextView) findViewById(R.id.today_counts);
        work_state = (TextView) findViewById(R.id.header_extra_state);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("查询");
        toolbar.setOnCreateContextMenuListener(this);

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mNavigationView.setNavigationItemSelectedListener(naviListener);
    }

    private void initData() {
        username.setText(getResources().getString(R.string.hi) + save.get("emp_name"));
        MyTools.getApkVersion(this, MyTools.UPDATE_APK_URL, false);


        MyTools.openGPS(this);
        MyTools.getGPSConfi(this);

    }


    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView
            .OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.querry:
//                    showDialog(save.get("role"));

                    break;
                case R.id.extra_work:
                    startActivity(new Intent(QuerryActivity.this, ExtraWorkActivity.class));
                    break;
                case R.id.work_order:
                    startActivity(new Intent(QuerryActivity.this, WorkOrderActivity.class));
                    break;
                case R.id.setting:
                    startActivity(new Intent(QuerryActivity.this, MySettingActivity.class));
                    break;
            }
            //关闭DrawerLayout回到主界面选中的tab的fragment页
            mDrawerLayout.closeDrawer(mNavigationView);
            return false;
        }
    };
    SearchView searchView;
    MenuItem scan;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item0 = menu.findItem(R.id.menu_item_search);
        scan = menu.findItem(R.id.scan);
        scan.setVisible(false);
        searchView = (SearchView) MenuItemCompat.getActionView(item0);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName name = getComponentName();
        SearchableInfo info = manager.getSearchableInfo(name);
        searchView.setSearchableInfo(info);
        searchView.setSubmitButtonEnabled(true);

        searchView.setQueryHint(getResources().getString(R.string.input_product_id));

        ImageView gobtn = (ImageView) searchView.findViewById(R.id.search_go_btn);
        gobtn.setImageResource(R.drawable.go);

        scan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(new Intent(QuerryActivity.this, ScanActivity.class), 1);
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(QuerryActivity.this, ScanActivity.class), 1);
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan.setVisible(true);
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                scan.setVisible(false);
                return false;
            }
        });

//        searchView.setQuery("21546",false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        RequestParams requestParams = new RequestParams(MyTools.QUERY_URL);
        //todo 转换
        requestParams.addHeader("Authorization", "Basic " + save.get("info"));

        requestParams.addBodyParameter("customer_name", "");
        requestParams.addBodyParameter("product_modle", "");
        requestParams.addBodyParameter("troubles", "");
        requestParams.addBodyParameter("emp_name", "");
        requestParams.addBodyParameter("start_date", MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis()));
        requestParams.addBodyParameter("end_date", MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis()));
        requestParams.addBodyParameter("product_id", "");
        requestParams.addBodyParameter("login_id", save.get("login_id"));

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);
                if (base.getResult().equals("true")) {
                    count.setText(base.getCount());
                    work_state.setText(save.get(MyTools.WORK_STATE));
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });


    }

    private void doSearch(String query) {
//        Snackbar.make(mDrawerLayout, query, Snackbar.LENGTH_LONG).show();
        if (adapter != null) {
            if (adapter.header != null)
                adapter.header.getQuerryMsgByProductID(query);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setPositiveButton(this.getResources().getString(R
                .string.queding), null).create();
        alertDialog.setTitle(this.getResources().getString(R.string.tishi));
        alertDialog.setMessage(s);
        alertDialog.show();
    }

    public void showMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (adapter.header != null)
                adapter.header.doQuerry(false);
        }
        if (resultCode == RESULT_OK) {
            String msg = data.getStringExtra("text");
            searchView.setQuery(msg, false);
        }
    }
}
