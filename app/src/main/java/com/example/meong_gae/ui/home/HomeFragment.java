package com.example.meong_gae.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.meong_gae.Global;
import com.example.meong_gae.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabAdapter tabAdapter;
    private Tab1Fragment tab1Fragment;
    private Tab2Fragment tab2Fragment;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    String tTitle, maxTimes, times, startDate, endDate, cancel;
    int ticketId[];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getData();       //firebase로 값 가져오고 model ArrayList에 add하기..

        models = new ArrayList<>();
       //models.add(new Model(tTitle,times.toString()+"/"+maxTimes.toString()+" 회권 남음", startDate + " ~ " + endDate ));
        //models.add(new Model("중형견 10회권 수강","10/ 30 회권 남음", "2022. 05. 19 ~ 2022. 09. 19"));
        adapter = new Adapter(models, root.getContext());

        viewPager = binding.viewPager;
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                Log.v("티켓 뷰페이저 onPageSelected",String.valueOf(position));
                //여기서 강아지 확정짓기
                  ((Global)getActivity().getApplication()).setTicketId(ticketId[position]);    //현재 유저 여러 애완견들의 ticketId를 하나로 셋팅
                  Log.v("앱 티켓 아이디", String.valueOf(ticketId[position]));
                  Log.v("앱 스크롤 위치 값",String.valueOf( ((Global)getActivity().getApplication()).getTicketId()));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        createFragment();
        createViewpager();
        settingTabLayout();

        return root;
    }

    public void getData(){
        email = ((Global)getActivity().getApplication()).getEmail();
        Log.v("홈 엑티비티 이메일",email);

        db.collection("Ticket")
                .whereEqualTo("email",email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ticketId= new int[task.getResult().size()];
                            int i =0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                ((Global)getActivity().getApplication()).setTicketId(ticketId[0]);

                                ticketId[i++] = Integer.valueOf(document.get("ticketId").toString());
                                tTitle = String.valueOf(document.get("tTitle"));
                                cancel = String.valueOf(document.get("cancel"));
                                maxTimes = String.valueOf(document.get("maxTimes"));
                                times = String.valueOf(document.get("times"));
                                startDate = String.valueOf(document.get("startDate"));
                                endDate = String.valueOf(document.get("endDate"));

                                models.add(new Model(tTitle,times.toString()+"/"+maxTimes.toString()+" 회권 남음", startDate + " ~ " + endDate ));
                            }
                            adapter.notifyDataSetChanged();
                     //       ((Global)getActivity().getApplication()).setTicketId(ticketId[0]);   //ticketId 초기값 지정
                            Log.v("ticketid", String.valueOf(((Global)getActivity().getApplication()).getTicketId()));
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void createFragment(){
        tab1Fragment = new Tab1Fragment();
        tab2Fragment = new Tab2Fragment();
    }

    public void createViewpager(){
        viewPager2 = binding.viewpager2;    //viewPager2참조

        tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager(), getLifecycle());  //setAdapter생성        tabAdapter.addFragment(tab1Fragment);
        tabAdapter.addFragment(tab1Fragment);
        tabAdapter.addFragment(tab2Fragment);

        viewPager2.setAdapter(tabAdapter);
        viewPager2.setUserInputEnabled(false);
    }

    public void settingTabLayout(){
        tabLayout = binding.tabLayout;

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