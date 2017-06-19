package com.yanbober.com_ucast_manager.my_adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.yanbober.com_ucast_manager.R;
import com.yanbober.com_ucast_manager.entity.WorkOrerEntity;
import com.yanbober.com_ucast_manager.manager_activities.SplashActivity;
import com.yanbober.com_ucast_manager.manager_activities.WorkOrderDetialActivity;
import com.yanbober.com_ucast_manager.tools.MyTools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.R.attr.positiveButtonText;
import static android.R.attr.scaleX;
import static com.yanbober.com_ucast_manager.R.id.end_date;
import static com.yanbober.com_ucast_manager.R.id.fab;

/**
 * Created by Allen on 2017/6/7.
 */

public class SimpleMsgCanScrollAdapter extends RecyclerView.Adapter {

    ArrayList<WorkOrerEntity> lists;
    Context mContext;
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    View.OnClickListener getMoreClickListener;
    View.OnClickListener querryClickListener;

    public SimpleMsgCanScrollAdapter(ArrayList<WorkOrerEntity> lists, Context context) {
        this.lists = lists;
        this.mContext = context;
    }

    public void setGetMoreClickListener(View.OnClickListener clickListener) {
        this.getMoreClickListener = clickListener;
    }

    public void setQuerryClickListener(View.OnClickListener clickListener) {
        this.querryClickListener = clickListener;
    }

    public void addMore(List<WorkOrerEntity> listMaore) {
        if (lists != null) {
            lists.addAll(listMaore);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_msg_item, parent, false);
            holder = new ViewHolder(view);
        } else if (viewType == VIEW_PROG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getmore_click_item, parent, false);
            holder = new MyProgressViewHolder(view);
        } else if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_msg_header, parent, false);
            holder = new MyHeader(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new MyNOMoreData(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            final WorkOrerEntity entity = lists.get(position);
            ViewHolder holder1 = (ViewHolder) holder;
            if (holder1.customerName != null) {
                holder1.customerName.setText(entity.getCustomerName());
                holder1.productType.setText(entity.getProductType());
                holder1.truble.setText(entity.getTruble());
                holder1.serviceman.setText(entity.getServiceman());
                holder1.date.setText(entity.getDate());
                ((ViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ArrayList<String> list = new ArrayList<String>();
//                        list.add(entity.getCustomerName());
//                        list.add(entity.getWorkOrderType());
//                        list.add(entity.getServiceman());
//                        list.add(entity.getProductType());
//                        list.add(entity.getId());
//                        list.add(entity.getTruble());
//                        list.add(entity.getHandleWays());
//                        list.add(entity.getDate());
//                        list.add(entity.getHandleMsgs());

                        EventBus.getDefault().postSticky(entity);
                        Intent intent = new Intent(mContext, WorkOrderDetialActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("msg",entity);
//                        intent.putExtras(bundle);

                        mContext.startActivity(intent);
                    }
                });

            }
        }else if(holder instanceof MyHeader){

        }
    }


    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        int flag = 0;
        if (lists.get(position) != null) {
            flag = VIEW_ITEM;
        } else {
            flag = VIEW_PROG;
        }
        if (position == 0) {
            flag = 3;
        }
        if (position == 1 && lists.get(position) == null) {
            flag = 4;
        }

        return flag;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView customerName;
        public TextView productType;
        public TextView truble;
        public TextView serviceman;
        public TextView date;

        public ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.simple_msg_item);
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
            if (getMoreClickListener != null) {
                tv.setOnClickListener(getMoreClickListener);
            }
        }

    }

    public class MyHeader extends RecyclerView.ViewHolder {
        public Spinner consumer_name;
        public Spinner product_model;
        public Spinner troubles;
        public Spinner serviceman;
        public Spinner date;
        public Button bt_querry;
        public LinearLayout ll_serviceman;
        public LinearLayout date_pick;
        public Button start_date;
        public Button end_date;

        int mYear, mMonth, mDay;

        public MyHeader(View itemView) {
            super(itemView);
            consumer_name = (Spinner) itemView.findViewById(R.id.consumer_name);
            product_model = (Spinner) itemView.findViewById(R.id.product_model);
            troubles = (Spinner) itemView.findViewById(R.id.troubles);
            serviceman = (Spinner) itemView.findViewById(R.id.serviceman);
            date = (Spinner) itemView.findViewById(R.id.date);
            bt_querry = (Button) itemView.findViewById(R.id.bt_querry);
            date_pick = (LinearLayout) itemView.findViewById(R.id.date_pick);
            ll_serviceman = (LinearLayout) itemView.findViewById(R.id.ll_serviceman);
            start_date = (Button) itemView.findViewById(R.id.start_date);
            end_date = (Button) itemView.findViewById(R.id.end_date);

            ll_serviceman.setVisibility(View.GONE);

            date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position==3){
                        goneToVisible(date_pick);
                    }else{
                        visibleToGone(date_pick);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            final Calendar ca = Calendar.getInstance();
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);


            showDate();
            start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog=new DatePickerDialog(mContext, mdateListener_start, mYear, mMonth-1, mDay);
                    datePickerDialog.show();
                }
            });
            end_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog=new DatePickerDialog(mContext, mdateListener_end, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });








            if (querryClickListener != null) {
                bt_querry.setOnClickListener(querryClickListener);
            }
        }

        private void showDate() {
            if (mMonth==0){
                start_date.setText((mYear-1)+"-"+(mMonth+12)+"-"+mDay);
            }
            start_date.setText(mYear+"-"+mMonth+"-"+mDay);
            end_date.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
        }

        private DatePickerDialog.OnDateSetListener mdateListener_start = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (MyTools.getIntToMillis(year+"-"+(monthOfYear+1)+"-"+dayOfMonth) > MyTools.getIntToMillis(end_date.getText().toString().trim())){
                    showDialog(mContext.getResources().getString(R.string.kaishi));
                    return;
                }

                start_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        };
        private DatePickerDialog.OnDateSetListener mdateListener_end = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (MyTools.getIntToMillis(year+"-"+(monthOfYear+1)+"-"+dayOfMonth) < MyTools.getIntToMillis(start_date.getText().toString().trim())){
                    showDialog(mContext.getResources().getString(R.string.jieshu));
                    return;
                }

                if (MyTools.getIntToMillis(year+"-"+(monthOfYear+1)+"-"+dayOfMonth) > System.currentTimeMillis()){
                    showDialog(mContext.getResources().getString(R.string.dangqian));
                    return;
                }
                end_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        };
        public void goneToVisible(final View view){
            view.setVisibility(View.VISIBLE);
            view.setPivotY(0);
            view.animate().scaleY(1f).setDuration(800).start();
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleY).setDuration(1000);
            objectAnimator.setInterpolator(new OvershootInterpolator());
            objectAnimator.start();
        }

        public void visibleToGone(final View view){
            view.setPivotY(0);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleY).setDuration(800);
            objectAnimator.setInterpolator(new AnticipateInterpolator());
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });

            objectAnimator.start();
        }

        public void setMySpinner(Spinner spinner,ArrayList<String> lists){
            // 建立Adapter并且绑定SSID数据源
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, lists);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spinner.setAdapter(adapter);
        }
        public void showDialog(String s){
            AlertDialog alertDialog=new AlertDialog.Builder(mContext).setPositiveButton(mContext.getResources().getString(R.string.queding),null).create();
            alertDialog.setTitle(mContext.getResources().getString(R.string.tishi));
            alertDialog.setMessage(s);
            alertDialog.show();
        }

    }



    public class MyNOMoreData extends RecyclerView.ViewHolder {
        public MyNOMoreData(View itemView) {
            super(itemView);
        }
    }
}
