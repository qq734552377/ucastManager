package com.yanbober.com_ucast_manager.sample_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.my_adapter.SimpleMsgAdapter;
import com.yanbober.com_ucast_manager.entity.WorkOrerEntity;

import java.util.ArrayList;

/**
 * Author       : yanbo
 * Date         : 2015-06-01
 * Time         : 15:09
 * Description  :
 */
public class ShareFragment extends Fragment {
    private View mParentView;
    private RecyclerView mRecyclerView;
    ArrayList<WorkOrerEntity> lists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.share_fragment, container, false);
        return mParentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mParentView.findViewById(R.id.recycler_view1);

        LinearLayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lists.add(new WorkOrerEntity("SMART" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326", "工作人员甲",
                    "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池", "2016-05-12 14:56"));
        }
        lists.add(null);
        final SimpleMsgAdapter adapter = new SimpleMsgAdapter(lists, getActivity());
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists.remove(lists.size() - 1);
                for (int i = 15; i < 25; i++) {
                    lists.add(new WorkOrerEntity("泰国" + (i + 1), "MDT-700-001", "无法开机黑屏", "维修", "P0022016050326",
                            "工作人员甲", "跟换电池", "拆机检查,内部硬件完好,电池膨胀,更换电池", "2016-05-12 14:56"));
                }
                lists.add(null);
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }
}
