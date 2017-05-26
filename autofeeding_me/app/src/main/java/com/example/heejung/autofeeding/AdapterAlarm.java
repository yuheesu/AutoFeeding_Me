package com.example.heejung.autofeeding;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.heejung.autofeeding.LinearLayoutSingleAlarmItem.OnRemoveButtonClickListner;

import java.util.ArrayList;

/**
 * Created by uhees on 2017-05-17.
 */

public class AdapterAlarm extends BaseAdapter {

    Context mContext;
    ArrayList<String> mData;
    LayoutInflater mInflate;
    ArrayList<AlarmData> arrayListAlarmDatas;


    public AdapterAlarm(Context context, ArrayList<AlarmData> arrayListAlarmDatas) {
        mContext = context;
        this.arrayListAlarmDatas = arrayListAlarmDatas;
        mInflate = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return arrayListAlarmDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return arrayListAlarmDatas.get(position).reqCode;
    }

    public boolean removeData(int position){
        arrayListAlarmDatas.remove(position);
        notifyDataSetChanged();
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayoutSingleAlarmItem layoutSingleAlarmItem = (LinearLayoutSingleAlarmItem) convertView;

        if(layoutSingleAlarmItem==null){
            layoutSingleAlarmItem = new LinearLayoutSingleAlarmItem(mContext);
            layoutSingleAlarmItem.setOnRemoveButtonClickListner(onRemoveButtonClickListner);
        }
        layoutSingleAlarmItem.setData(arrayListAlarmDatas.get(position), position);
        return layoutSingleAlarmItem;
    }
    OnRemoveButtonClickListner onRemoveButtonClickListner = new OnRemoveButtonClickListner() {

        @Override
        public void onClicked(int hh, int mm, int reqCode, int position) {
//            Toast.makeText(mContext, "position : "+position + " reqCode :"+reqCode, Toast.LENGTH_SHORT).show();
            AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
//			    Intent i = new Intent(mContext ,AlarmTestForHaruActivity.class);
            Intent intent = new Intent(mContext, MultiAlarm.class);
//            Toast.makeText(mContext, "reqCode : "+reqCode, Toast.LENGTH_SHORT).show();
            PendingIntent pi = PendingIntent.getActivity(mContext, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pi);
            removeData(position);
        }
    };

}
