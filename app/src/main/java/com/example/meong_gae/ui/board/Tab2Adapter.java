package com.example.meong_gae.ui.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meong_gae.R;
import com.example.meong_gae.ui.home.Hisotry;

import java.util.ArrayList;

public class Tab2Adapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<BoardTab2Item> data;

    Tab2Adapter(Context context, ArrayList<BoardTab2Item> items){
        mContext = context;
        data = items;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.fragment_board_tab2_item, null);

        TextView tab2Title = (TextView)v.findViewById(R.id.tab2_title);
        TextView tab2Contents = (TextView)v.findViewById(R.id.tab2_contents);

        tab2Title.setText(data.get(i).getTitle());
        tab2Contents.setText(data.get(i).getContents());

        return v;
    }
}
