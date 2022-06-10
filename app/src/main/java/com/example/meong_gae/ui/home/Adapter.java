package com.example.meong_gae.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.meong_gae.R;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    LayoutInflater layoutInflater;
    Context context;

    public Adapter(List<Model> models, Context context){
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_home_item, container, false);

        TextView title, count, date;

        title = view.findViewById(R.id.cd_title);
        count = view.findViewById(R.id.cd_count);
        date = view.findViewById(R.id.cd_date);

        title.setText(models.get(position).getTitle());
        count.setText(models.get(position).getCount());
        date.setText(models.get(position).getDate());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
