package com.yanbober.com_ucast_manager.my_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.entity.WorkOrerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/6/7.
 */

public class SimpleMsgAdapter extends RecyclerView.Adapter {

    ArrayList<WorkOrerEntity> lists;
    Context mContext;
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    View.OnClickListener clickListener;

    public SimpleMsgAdapter(ArrayList<WorkOrerEntity> lists, Context context) {
        this.lists = lists;
        this.mContext = context;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addMore(List<WorkOrerEntity> listMaore){
        if (lists!=null){
            lists.addAll(listMaore);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_msg_item, parent, false);
            holder = new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getmore_click_item, parent, false);
            holder = new MyProgressViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            WorkOrerEntity entity = lists.get(position);
            ViewHolder holder1 = (ViewHolder) holder;
            if (holder1.customerName != null) {
                holder1.customerName.setText(entity.getCustomerName());
                holder1.productType.setText(entity.getProductType());
                holder1.truble.setText(entity.getTruble());
                holder1.serviceman.setText(entity.getServiceman());
                holder1.date.setText(entity.getDate());
            }
        }
    }


    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return lists.get(position) != null ? VIEW_ITEM : VIEW_PROG;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView customerName;
        public TextView productType;
        public TextView truble;
        public TextView serviceman;
        public TextView date;

        public ViewHolder(View view) {
            super(view);
            customerName = (TextView) view.findViewById(R.id.custom_name);
            productType = (TextView) view.findViewById(R.id.product_model);
            truble = (TextView) view.findViewById(R.id.truble);
            serviceman = (TextView) view.findViewById(R.id.serviceman);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    public class MyProgressViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public MyProgressViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.getmore);
            if (clickListener != null) {
                tv.setOnClickListener(clickListener);
            }
        }

    }
}
