package com.example.meong_gae.ui.board;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.meong_gae.databinding.FragmentBoardTab1Binding;
import com.example.meong_gae.ui.home.Hisotry;
import com.example.meong_gae.ui.home.HistoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

// 공지사항 화면
public class Tab1Fragment extends Fragment {

    private FragmentBoardTab1Binding binding;
    ArrayList<BoardTab1Item> tab1Items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nTitle, nContent;

    ListView listView;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBoardTab1Binding.inflate(inflater, container, false);
        root = binding.getRoot();

        this.Initalizetab1ItemsData();
        listView = binding.boardTab1ListView;

        return root;
    }

    public void Initalizetab1ItemsData(){
        tab1Items = new ArrayList<BoardTab1Item>();

        db.collection("Notice")
                .whereEqualTo("facilId",1)        //지금은 기관이 1개로 지정해서.. 1로 박음
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        nTitle = String.valueOf(document.get("nTitle"));
                        nContent = String.valueOf(document.get("nContent"));
                        tab1Items.add(new BoardTab1Item(nTitle,nContent));
                    }
                    final Tab1Adapter tAdapter = new Tab1Adapter(root.getContext(), tab1Items);
                    listView.setAdapter(tAdapter);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
//        tab1Items.add(new BoardTab1Item("tab1 제목","tab1 내용글"));
//        tab1Items.add(new BoardTab1Item("tab1 제목22","tab1 내용글2"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
