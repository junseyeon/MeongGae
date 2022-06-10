package com.example.meong_gae.ui.board;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStateAdapter {

    //Board상단 바 switching adapter

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

}
