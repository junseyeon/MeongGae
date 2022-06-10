package com.example.meong_gae.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meong_gae.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Hisotry> data;

    HistoryAdapter(Context context, ArrayList<Hisotry> history){
        mContext = context;
        data = history;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.fragment_home_tab2_item, null);

        TextView classType = (TextView)v.findViewById(R.id.card_classType);
        TextView dateTime = (TextView)v.findViewById(R.id.card_dateTime);
        TextView status = (TextView)v.findViewById(R.id.card_status);
        TextView teacher = (TextView)v.findViewById(R.id.card_teacher);

        classType.setText(data.get(i).getClassN());
        dateTime.setText(data.get(i).getDateTime());
        status.setText(data.get(i).getStatus());
        teacher.setText(data.get(i).getTeacher());

        return v;
    }
}
