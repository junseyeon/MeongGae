package com.example.meong_gae.ui.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meong_gae.R;

import java.util.ArrayList;

public class Tab1Adapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<BoardTab1Item> data;

    Tab1Adapter(Context context, ArrayList<BoardTab1Item> board){
        mContext = context;
        data = board;
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
        View v = mLayoutInflater.inflate(R.layout.fragment_board_tab1_item, null);

        TextView tab1Title = (TextView)v.findViewById(R.id.tab1_title);
        TextView tab1Contents = (TextView)v.findViewById(R.id.tab1_contents);

        tab1Title.setText(data.get(i).getTitle());
        tab1Contents.setText(data.get(i).getContents());


        return v;
    }
}
