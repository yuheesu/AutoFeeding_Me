package com.example.heejung.autofeeding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearLayoutSingleAlarmItem extends LinearLayout {
	Context mContext;
	TextView textViewTime;
	Button btnSingleAlarmItemCancel;

	AlarmData alarmData;

	private int position;
	
	public LinearLayoutSingleAlarmItem(Context context) {
		super(context);
		mContext = context;
		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.single_alarm_data_layout, this);
		textViewTime = (TextView)layout.findViewById(R.id.textViewTime_1);
		btnSingleAlarmItemCancel = (Button)findViewById(R.id.btnDeleteAlarm);
		
		btnSingleAlarmItemCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onRemoveButtonClickListner != null)
				onRemoveButtonClickListner.onClicked(alarmData.hh, alarmData.mm, alarmData.reqCode, position);
			}
		});
	}
	
	public interface OnRemoveButtonClickListner{
		void onClicked(int hh, int mm, int reqCode, int position);
	}
	
	OnRemoveButtonClickListner onRemoveButtonClickListner;
	
	void setOnRemoveButtonClickListner(OnRemoveButtonClickListner onRemoveButtonClickListner){
		this.onRemoveButtonClickListner = onRemoveButtonClickListner;
	}
	
	public boolean setData(AlarmData alarmData, int position){
		
		this.alarmData = alarmData;
		this.position = position;
		
		textViewTime.setText(alarmData.hh+":"+alarmData.mm);

		return true;
	}
	
	

}
