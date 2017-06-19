package com.yanbober.com_ucast_manager.manager_activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.my_adapter.SimpleMsgCanScrollAdapter;
import com.yanbober.com_ucast_manager.entity.WorkOrerEntity;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class QuerryActivity extends AppCompatActivity {


    ArrayList<WorkOrerEntity> lists;
    RecyclerView mRecyclerView;
    SimpleMsgCanScrollAdapter adapter;
    //DrawerLayout中的左侧菜单控件
    private NavigationView mNavigationView;
    //DrawerLayout控件
    private DrawerLayout mDrawerLayout;
    Toolbar toolbar;
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
                lists.remove(lists.size() - 1);
                for (int i = 15; i < 25; i++) {
                    lists.add(new WorkOrerEntity("泰国" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326",
                            "工作人员甲", "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池   拆机检查,内部硬件完好,电池膨胀,更换电池拆机检查,内部硬件完好,电池膨胀,更换电池  拆机检查,内部硬件完好,电池膨胀,更换电池 机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池 机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池 拆机检查,内部硬件完好,电池膨胀,更换电池", "2016-05-12 14:56"));
                }
                lists.add(null);
                adapter.notifyDataSetChanged();
            }
        });


        adapter.setQuerryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lists.isEmpty()){
                    lists.clear();
                }
                lists.add(null);
                for (int i = 0; i < 15; i++) {
                    lists.add(new WorkOrerEntity("SMART" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326", "工作人员甲",
                            "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池 拆", "2016-05-12 14:56"));
                }
                lists.add(null);
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(adapter);



    }

    private void initView() {
        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) this.findViewById(R.id.navigation_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

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
    }
    private NavigationView.OnNavigationItemSelectedListener naviListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            //点击NavigationView中定义的menu item时触发反应
            switch (menuItem.getItemId()) {
                case R.id.querry:

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //主界面右上角的menu菜单
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item=menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        SearchManager manager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName name=getComponentName();
        SearchableInfo info=manager.getSearchableInfo(name);
        searchView.setSearchableInfo(info);

        searchView.setQueryHint("请输入产品ID");

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

    private void doSearch(String query) {
        Snackbar.make(mDrawerLayout,query,Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
