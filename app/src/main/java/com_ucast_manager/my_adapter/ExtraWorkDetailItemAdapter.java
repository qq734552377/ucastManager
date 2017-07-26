package com_ucast_manager.my_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.FileNameMap;
import java.util.ArrayList;

import com_ucast_manager.R;
import com_ucast_manager.entity.ExtraWorkMSg;
import com_ucast_manager.entity.WorkOrerEntity;

/**
 * Created by pj on 2017/7/19.
 */

public class ExtraWorkDetailItemAdapter extends RecyclerView.Adapter {

    ArrayList<ExtraWorkMSg> lists;
    Context mContext;

    private final static int NODATA = 0;
    private final static int EXTRADETAIL = 1;

    public ExtraWorkDetailItemAdapter(ArrayList<ExtraWorkMSg> lists, Context context) {
        this.lists = lists;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType==NODATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new MyNOMoreData(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.extral_work_detail_item, parent, false);
            holder = new ExtraWorkDetailItem(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExtraWorkDetailItem){
            ExtraWorkDetailItem v=(ExtraWorkDetailItem) holder;
            ExtraWorkMSg entity=lists.get(position);

            v.reson.setText(entity.getExtra_work_reason());
            v.start_datetime.setText(entity.getExtra_work_start_time());
            v.end_datetime.setText(entity.getExtra_work_end_time());
        }

    }

    @Override
    public int getItemViewType(int position) {
        int flag = 0;
        if (lists == null || lists.size() == 0) {
            flag =NODATA;
        } else {
            flag =EXTRADETAIL;
        }
        return flag;

    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    public class MyNOMoreData extends RecyclerView.ViewHolder {
        public MyNOMoreData(View itemView) {
            super(itemView);
        }
    }

    public class ExtraWorkDetailItem extends RecyclerView.ViewHolder {
        public TextView reson;
        public TextView start_datetime;
        public TextView end_datetime;

        public ExtraWorkDetailItem(View itemView) {
            super(itemView);
            reson = (TextView) itemView.findViewById(R.id.item_overtime_reson);
            start_datetime = (TextView) itemView.findViewById(R.id.item_start_date);
            end_datetime = (TextView) itemView.findViewById(R.id.item_end_date);
        }
    }

}
