package com.example.meong_gae.ui.reserve;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meong_gae.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private List<ScheduleJobBean> scheduleList;
    private Activity activity;
    private ItemClickListener clickListener;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public int classId;
        public TextView dateTime, classN, teacher, status, num;
      //  public View v;

        public MyViewHolder(View view) {
            super(view);
            //context = view.getContext();
            //  v = view;
            dateTime = (TextView) view.findViewById(R.id.cal_dateTime);
            classN = (TextView) view.findViewById(R.id.cal_classType);
            teacher=(TextView)view.findViewById(R.id.cal_teacher);
            status=(TextView)view.findViewById(R.id.cal_status);
            num = (TextView)view.findViewById(R.id.cal_num);

            view.setOnClickListener(this::onClick);
        }
        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public CalendarAdapter(List<ScheduleJobBean> scheduleList, Activity activity) {
        this.scheduleList = scheduleList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_adapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.classId = scheduleList.get(position).getClassId();
        holder.dateTime.setText(scheduleList.get(position).getDateTime());
        holder.classN.setText(scheduleList.get(position).getClassN());
        holder.teacher.setText(scheduleList.get(position).getTeacher()+" 강사");
        holder.num.setText("예약인원/최대수강인원 " + scheduleList.get(position).getNowNum()+"/"+scheduleList.get(position).getMaxNum());

        boolean able = true;
        if(scheduleList.get(position).getNowNumInt() < scheduleList.get(position).getMaxNumInt()){
            for(int i=0; i<position; i++){
                if(scheduleList.get(position).getClassId() == scheduleList.get(i).getClassId()){
                    able = false;
                    break;
                }
            }
            if(able == true){
                holder.status.setText("예약 가능");
            } else{
                holder.status.setText("예약 불가");
            }
        }
        else{
            holder.status.setText("예약 불가");
        }
        
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
