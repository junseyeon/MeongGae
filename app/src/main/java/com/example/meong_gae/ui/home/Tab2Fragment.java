package com.example.meong_gae.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meong_gae.Global;
import com.example.meong_gae.R;
import com.example.meong_gae.databinding.FragmentHomeTab1Binding;
import com.example.meong_gae.databinding.FragmentHomeTab2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment {

    private FragmentHomeTab2Binding binding;
    ArrayList<Hisotry> histories;
    ArrayAdapter<CharSequence> adapter;
    View root;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    ListView listView;

    String email;
    int ticketId;
    int classId;
    String classIntro, classTD, classTitle, tName, status;
    int  maxNum, nowNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeTab2Binding.inflate(inflater, container, false);
        root = binding.getRoot();
        histories = new ArrayList<Hisotry>();
        email = ((Global) getActivity().getApplication()).getEmail();
        ticketId = ((Global) getActivity().getApplication()).getTicketId();
        Log.v("ticketId",ticketId+" ");

        // spinner시작
        Spinner spinnerOrder = binding.spinnerOrder;
        Spinner spinnerHistory = binding.spinnerHistory;

        //this에서 getContext()로 변경함
        adapter = ArrayAdapter.createFromResource(root.getContext(),
                R.array.order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrder.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(root.getContext(),
                R.array.history, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHistory.setAdapter(adapter2);

        spinnerOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("spinnerOrder", i + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("spinnerHistory", i + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 여기부터 cardview + listview : https://lktprogrammer.tistory.com/163 [참고]
        this.InitalizeHistoryData();
        listView = binding.homeTab2ListView;
//        final HistoryAdapter hAdapter = new HistoryAdapter(root.getContext(), histories);
//        listView.setAdapter(hAdapter);

        return root;
    }

    public void InitalizeHistoryData(){
        Log.v("ticketId", String.valueOf(ticketId));
        db.collection("Reserve")
                .whereEqualTo("ticketId",ticketId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.v("....", String.valueOf(task.getResult().size()));
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        classId = Integer.valueOf(document.get("classId").toString());
                        db2.collection("Class")
                                .whereEqualTo("classId",classId)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.v("classId2", String.valueOf(task.getResult().size()));
                                    for (QueryDocumentSnapshot document : task.getResult()) {    //어차피 항상 classId는 1개
                                        classIntro = String.valueOf(document.get("classIntro"));
                                        classTD = String.valueOf(document.get("classTD"));
                                        classTitle = String.valueOf( document.get("classTitle"));
                                        maxNum = Integer.valueOf(String.valueOf(document.get("maxNum")));
                                        nowNum = Integer.valueOf(String.valueOf(document.get("nowNum")));
                                        tName = String.valueOf( document.get("tName"));
                                        status = String.valueOf(document.get("status"));
                                    }
                                    histories.add(new Hisotry(ticketId, ticketId, classTD, classTitle,tName +" 강사",status));
                                    Log.v("his-list1",ticketId+ " " + classTD+ " " + classTitle + " " + tName+ " "+ status);
                                    HistoryAdapter hAdapter = new HistoryAdapter(root.getContext(), histories);
                                    listView.setAdapter(hAdapter);
                                }
                            }
                        });
                    }
                }
            }
        });
        // histories.add(new Hisotry("2022. 06. 21 09:00~17:00","사교 놀이","심효리 강사","예약"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
