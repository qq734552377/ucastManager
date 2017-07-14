package com_ucast_manager.my_adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.alibaba.fastjson.JSON;
import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;
import com_ucast_manager.entity.BaseSpinnerReturnMsg;
import com_ucast_manager.entity.WorkOrerEntity;
import com_ucast_manager.entity.WorkorderMSg;
import com_ucast_manager.manager_activities.QuerryActivity;
import com_ucast_manager.manager_activities.SplashActivity;
import com_ucast_manager.manager_activities.WorkOrderDetialActivity;
import com_ucast_manager.tools.MyTools;
import com_ucast_manager.tools.SavePasswd;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.R.attr.cursorVisible;
import static android.R.attr.id;
import static android.R.attr.longClickable;
import static android.R.attr.positiveButtonText;
import static android.R.attr.scaleX;
import static android.R.attr.subtypeExtraValue;
import static com.alibaba.fastjson.JSON.parseObject;
import static com_ucast_manager.R.id.custom_name;
import static com_ucast_manager.R.id.end_date;
import static com_ucast_manager.R.id.fab;
import static com_ucast_manager.R.id.product_model;
import static com_ucast_manager.R.id.troubles;

/**
 * Created by Allen on 2017/6/7.
 */

public class SimpleMsgCanScrollAdapter extends RecyclerView.Adapter {

    ArrayList<WorkorderMSg> lists;
    Context mContext;
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_PROG = 1;
    View.OnClickListener getMoreClickListener;
    View.OnClickListener querryClickListener;

    public MyHeader header;

    public SimpleMsgCanScrollAdapter(ArrayList<WorkorderMSg> lists, Context context) {
        this.lists = lists;
        this.mContext = context;
    }

    public void setGetMoreClickListener(View.OnClickListener clickListener) {
        this.getMoreClickListener = clickListener;

    }

    public void setQuerryClickListener(View.OnClickListener clickListener) {
        this.querryClickListener = clickListener;
    }

    public void addMore(List<WorkorderMSg> listMaore) {
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
            header = (MyHeader) holder;
//            ((MyHeader)holder).doQuerry();
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new MyNOMoreData(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            final WorkorderMSg entity = lists.get(position);
            ViewHolder holder1 = (ViewHolder) holder;
            if (holder1.customerName != null) {
                holder1.customerName.setText(entity.getCustomer_name());
                holder1.productType.setText(entity.getProduct_modle());
                holder1.truble.setText(entity.getTroubles());
                holder1.serviceman.setText(entity.getEmp_name());
                holder1.date.setText(entity.getCreate_date());
                ((ViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().postSticky(entity);
                        Intent intent = new Intent(mContext, WorkOrderDetialActivity.class);
                        ((QuerryActivity) mContext).startActivityForResult(intent, 200);
                    }
                });

            }
        } else if (holder instanceof MyHeader) {

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
        SavePasswd save;
        String customer = "";
        String productModle = "";
        String trouble = "";
        String servicem = "";
        String startd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());
        String endd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());

        ProgressDialog dialog2;


        public MyHeader(View itemView) {
            super(itemView);
            dialog2 = new ProgressDialog(mContext);
            dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            dialog2.setCancelable(false);// 设置是否可以通过点击Back键取消
            dialog2.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//            dialog2.setTitle(mContext.getResources().getString(R.string.tishi));
            dialog2.setMessage(mContext.getResources().getString(R.string.querrying));


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

            save = SavePasswd.getInstace();

            String role = save.get("role");
            if (role.equals("admin")) {
                ll_serviceman.setVisibility(View.VISIBLE);
            }
            setMySpinner(consumer_name, save.get(MyTools.RETURN_ALL_CUSTOMER));
            setMySpinner(product_model, save.get(MyTools.RETURN__ALL_PRODUCT_MODLE));
            setMySpinner(troubles, save.get(MyTools.RETURN__TROUBLES));
            setMySelectSpinner(serviceman, save.get(MyTools.RETURN__ALL_EMP_NAME));

            date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 3) {
                        goneToVisible(date_pick);
                    } else {
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
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, mdateListener_start, mYear,
                            mMonth - 1, mDay);
                    datePickerDialog.show();
                }
            });
            end_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, mdateListener_end, mYear,
                            mMonth, mDay);
                    datePickerDialog.show();
                }
            });


//            if (querryClickListener != null) {
//                bt_querry.setOnClickListener(querryClickListener);
//            }

            bt_querry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doQuerry(true);
                }
            });

        }

        public void doQuerry(final boolean isShowDialog) {

            dialog2.show();

            if (consumer_name.getSelectedItemPosition() != 0) {
                customer = consumer_name.getSelectedItem().toString();
            } else {
                customer = "";
            }
            if (product_model.getSelectedItemPosition() != 0) {
                productModle = product_model.getSelectedItem().toString();
            } else {
                productModle = "";
            }
            if (troubles.getSelectedItemPosition() != 0) {
                trouble = troubles.getSelectedItem().toString();
            } else {
                trouble = "";
            }


            if (ll_serviceman.getVisibility() == View.VISIBLE && serviceman.getSelectedItemPosition() != 0) {
                servicem = serviceman.getSelectedItem().toString();
            } else {
                servicem = "";
            }
            long todaytime = MyTools.getIntToMillis(end_date.getText().toString());
            startd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());
            endd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());

            if (date.getSelectedItemPosition() == 1) {
                startd = MyTools.millisToDateStringOnlyYMD(todaytime - 7L * 24L * 60L * 60L * 1000L);
                endd = MyTools.millisToDateStringOnlyYMD(todaytime);
            } else if (date.getSelectedItemPosition() == 2) {
                startd = MyTools.millisToDateStringOnlyYMD(todaytime - 30L * 24L * 3600L * 1000L);
                endd = MyTools.millisToDateStringOnlyYMD(todaytime);
            } else if (date.getSelectedItemPosition() == 3) {
                startd = MyTools.millisToDateStringOnlyYMD(MyTools.getIntToMillis(start_date.getText().toString()
                        .trim()));
                endd = MyTools.millisToDateStringOnlyYMD(MyTools.getIntToMillis(end_date.getText().toString().trim()));
            }
//                    showDialog(customer + " " + productModle + " " + trouble + " " + servicem + " " + startd + " " +
//                            endd);
            RequestParams requestParams = new RequestParams(MyTools.QUERY_URL);

            //todo 转换
            requestParams.addHeader("Authorization", "Basic " + save.get("info"));

            requestParams.addBodyParameter("customer_name", customer);
            requestParams.addBodyParameter("product_modle", productModle);
            requestParams.addBodyParameter("troubles", trouble);
            requestParams.addBodyParameter("emp_name", servicem);
            requestParams.addBodyParameter("start_date", startd);
            requestParams.addBodyParameter("end_date", endd);
            requestParams.addBodyParameter("product_id", "");
            requestParams.addBodyParameter("login_id", save.get("login_id"));
            requestParams.addBodyParameter("list_size", "0");

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                            showDialog(result);
                    BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);
                    if (base.getResult().equals("true") && Integer.parseInt(base.getCount()) > 0) {

                        List<WorkorderMSg> msg = JSON.parseArray(base.getDate(), WorkorderMSg.class);

                        if (!lists.isEmpty()) {
                            lists.clear();
                        }
                        lists.add(null);
                        for (int i = 0; i < msg.size(); i++) {
                            lists.add(msg.get(i));
                        }
                        lists.add(null);
                        notifyDataSetChanged();
                    } else {
                        if (isShowDialog) {
                            showDialog(base.getMsg());
                        }
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
                    dialog2.dismiss();
                }
            });

        }


        public void getMoreData(String productID) {
            dialog2.show();

            RequestParams requestParams = new RequestParams(MyTools.QUERY_URL);

            //todo 转换
            requestParams.addHeader("Authorization", "Basic " + save.get("info"));

            requestParams.addBodyParameter("customer_name", customer);
            requestParams.addBodyParameter("product_modle", productModle);
            requestParams.addBodyParameter("troubles", trouble);
            requestParams.addBodyParameter("emp_name", servicem);
            requestParams.addBodyParameter("start_date", startd);
            requestParams.addBodyParameter("end_date", endd);
            requestParams.addBodyParameter("product_id", productID);
            requestParams.addBodyParameter("login_id", save.get("login_id"));
            requestParams.addBodyParameter("list_size", (lists.size() - 2) + "");

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                            showDialog(result);
                    BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);
                    if (base.getResult().equals("true") && Integer.parseInt(base.getCount()) > 0) {

                        List<WorkorderMSg> msg = JSON.parseArray(base.getDate(), WorkorderMSg.class);

                        lists.remove(lists.size() - 1);

                        for (int i = 0; i < msg.size(); i++) {
                            lists.add(msg.get(i));
                        }
                        lists.add(null);
                        notifyDataSetChanged();
                    } else {
                        showDialog(base.getMsg());
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
                    dialog2.dismiss();
                }
            });
        }

        public void getQuerryMsgByProductID(String productID) {
            dialog2.show();


            if (consumer_name.getSelectedItemPosition() != 0) {
                customer = consumer_name.getSelectedItem().toString();
            }
            if (product_model.getSelectedItemPosition() != 0) {
                productModle = product_model.getSelectedItem().toString();
            }
            if (troubles.getSelectedItemPosition() != 0) {
                trouble = troubles.getSelectedItem().toString();
            }


            if (ll_serviceman.getVisibility() == View.VISIBLE && serviceman.getSelectedItemPosition() != 0) {
                servicem = serviceman.getSelectedItem().toString();
            }
            long todaytime = MyTools.getIntToMillis(end_date.getText().toString());
            startd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());
            endd = MyTools.millisToDateStringOnlyYMD(System.currentTimeMillis());

            if (date.getSelectedItemPosition() == 1) {
                startd = MyTools.millisToDateStringOnlyYMD(todaytime - 7L * 24L * 60L * 60L * 1000L);
                endd = MyTools.millisToDateStringOnlyYMD(todaytime);
            } else if (date.getSelectedItemPosition() == 2) {
                startd = MyTools.millisToDateStringOnlyYMD(todaytime - 30L * 24L * 3600L * 1000L);
                endd = MyTools.millisToDateStringOnlyYMD(todaytime);
            } else if (date.getSelectedItemPosition() == 3) {
                startd = MyTools.millisToDateStringOnlyYMD(MyTools.getIntToMillis(start_date.getText().toString()
                        .trim()));
                endd = MyTools.millisToDateStringOnlyYMD(MyTools.getIntToMillis(end_date.getText().toString().trim()));
            }
//                    showDialog(customer + " " + productModle + " " + trouble + " " + servicem + " " + startd + " " +
//                            endd);
            RequestParams requestParams = new RequestParams(MyTools.UQUERY_ID_URL);

            //todo 转换
            requestParams.addHeader("Authorization", "Basic " + save.get("info"));

            requestParams.addBodyParameter("customer_name", customer);
            requestParams.addBodyParameter("product_modle", productModle);
            requestParams.addBodyParameter("troubles", trouble);
            requestParams.addBodyParameter("emp_name", servicem);
            requestParams.addBodyParameter("start_date", startd);
            requestParams.addBodyParameter("end_date", endd);
            requestParams.addBodyParameter("product_id", productID);
            requestParams.addBodyParameter("login_id", save.get("login_id"));
            requestParams.addBodyParameter("list_size", "0");

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                            showDialog(result);
                    BaseReturnMsg base = JSON.parseObject(result, BaseReturnMsg.class);
                    if (base.getResult().equals("true") && Integer.parseInt(base.getCount()) > 0) {

                        List<WorkorderMSg> msg = JSON.parseArray(base.getDate(), WorkorderMSg.class);

                        if (!lists.isEmpty()) {
                            lists.clear();
                        }
                        lists.add(null);
                        for (int i = 0; i < msg.size(); i++) {
                            lists.add(msg.get(i));
                        }
                        lists.add(null);
                        notifyDataSetChanged();
                    } else {
                        if (!lists.isEmpty()) {
                            lists.clear();
                        }
                        lists.add(null);
                        lists.add(null);
                        showDialog(base.getMsg());
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
                    dialog2.dismiss();
                }
            });
        }


        private void showDate() {
            if (mMonth == 0) {
                start_date.setText((mYear - 1) + "-" + (mMonth + 12) + "-" + mDay);
            }
            start_date.setText(mYear + "-" + mMonth + "-" + mDay);
            end_date.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        }

        private DatePickerDialog.OnDateSetListener mdateListener_start = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (MyTools.getIntToMillis(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth) > MyTools
                        .getIntToMillis(end_date.getText().toString().trim())) {
                    showDialog(mContext.getResources().getString(R.string.kaishi));
                    return;
                }

                start_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };
        private DatePickerDialog.OnDateSetListener mdateListener_end = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (MyTools.getIntToMillis(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth) < MyTools
                        .getIntToMillis(start_date.getText().toString().trim())) {
                    showDialog(mContext.getResources().getString(R.string.jieshu));
                    return;
                }

                if (MyTools.getIntToMillis(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth) > System
                        .currentTimeMillis()) {
                    showDialog(mContext.getResources().getString(R.string.dangqian));
                    return;
                }
                end_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };

        public void goneToVisible(final View view) {
            view.setVisibility(View.VISIBLE);
            view.setPivotY(0);
            view.animate().scaleY(1f).setDuration(800).start();
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleY).setDuration(1000);
            objectAnimator.setInterpolator(new OvershootInterpolator());
            objectAnimator.start();
        }

        public void visibleToGone(final View view) {
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

        public void setMySpinner(Spinner spinner, String msgs) {
            String[] strs = msgs.split(",");
            // 建立Adapter并且绑定SSID数据源
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                    strs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spinner.setAdapter(adapter);
        }

        public void setMySelectSpinner(Spinner spinner, String msgs) {
            String[] strs = msgs.split(",");
            strs[0] = mContext.getResources().getString(R.string.select);
            // 建立Adapter并且绑定SSID数据源
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                    strs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //绑定 Adapter到控件
            spinner.setAdapter(adapter);
        }

        public void showDialog(String s) {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext).setPositiveButton(mContext.getResources()
                    .getString(R.string.queding), null).create();
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
