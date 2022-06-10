package com.example.meong_gae.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meong_gae.Global;
import com.example.meong_gae.databinding.FragmentHomeTab1Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tab1Fragment extends Fragment {

   private FragmentHomeTab1Binding binding;
   private TextView tl1_reserve;
   private TextView tl1_count;
   private TextView tl1_date;
   private TextView tl2_date;
   private TextView tl2_ticket;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    int ticketId;
    String maxTimes, times, timesReserve;
    String startDate, endDate, cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeTab1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tl1_reserve=binding.tl1Reserve;
        tl1_count=binding.tl1Count;
        tl1_date = binding.tl1Date;
        tl2_date = binding.tl2Date;
        tl2_ticket=binding.tl2Ticket;

        email = ((Global) getActivity().getApplication()).getEmail();
        ticketId = ((Global) getActivity().getApplication()).getTicketId();
        Log.v("Tab1Fragment의 email",email + " " + ticketId);

        getData();
        //db로 받아와서 변경하기
        // 예약가능 횟수, 잔여횟수, 잔여일 , 날짜, maxTimes



        return root;
    }

    public void getData(){
        db.collection("Ticket")
                .whereEqualTo("ticketId",ticketId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        maxTimes = String.valueOf(document.get("maxTimes"));
                        times = String.valueOf(document.get("times"));
                        timesReserve = String.valueOf(document.get("times-reserve"));
                        startDate = String.valueOf(document.get("startDate"));
                        endDate = String.valueOf(document.get("endDate"));
                        cancel = String.valueOf(document.get("cancel"));
                        Log.v("!!!!!!!",maxTimes+ " " + times + " " + timesReserve + " " + startDate + " " + endDate + " " + cancel);


                        tl1_reserve.setText(timesReserve+"회 남음");
                        tl1_count.setText(times+"회 남음");
                        tl1_date.setText(cancel+"회 남음");
                        tl2_date.setText(startDate + " ~ " + endDate);
                        tl2_ticket.setText(maxTimes + "회 이용권");
                    }

                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
