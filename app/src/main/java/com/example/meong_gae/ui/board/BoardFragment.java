package com.example.meong_gae.ui.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.meong_gae.databinding.FragmentBoardBinding;
import com.google.android.material.tabs.TabLayout;

public class BoardFragment extends Fragment {

    private FragmentBoardBinding binding;

    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;
    private ViewPager2 viewPager2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createFragment();
        createViewpager();
        settingTabLayout();

        return root;
    }

    public void createFragment(){
        tab1Fragment = new com.example.meong_gae.ui.board.Tab1Fragment();
        tab2Fragment = new com.example.meong_gae.ui.board.Tab2Fragment();
    }

    public void createViewpager(){
        viewPager2 = binding.boardViewpager2;    //viewPager2참조

        tabAdapter = new com.example.meong_gae.ui.board.TabAdapter(getActivity().getSupportFragmentManager(), getLifecycle());  //setAdapter생성성        tabAdapter.addFragment(tab1Fragment);
        tabAdapter.addFragment(tab1Fragment);
        tabAdapter.addFragment(tab2Fragment);

        viewPager2.setAdapter(tabAdapter);
        viewPager2.setUserInputEnabled(false);
    }

    public void settingTabLayout(){
        tabLayout = binding.boardTablayout;

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0:
                        viewPager2.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager2.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}