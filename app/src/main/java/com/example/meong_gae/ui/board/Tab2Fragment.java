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

import com.example.meong_gae.databinding.FragmentBoardTab2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment {

    private FragmentBoardTab2Binding binding;
    ArrayList<BoardTab2Item> boardTab2Items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String mgbTitle, mgbContent;

    ListView listView;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBoardTab2Binding.inflate(inflater, container, false);
        root = binding.getRoot();

        this.Initalizetab2ItemsData();
        listView = binding.boardTab2ListView;
        final Tab2Adapter tAdapter = new Tab2Adapter(root.getContext(), boardTab2Items);
        listView.setAdapter(tAdapter);

        return root;
    }

    public void Initalizetab2ItemsData(){
        boardTab2Items = new ArrayList<BoardTab2Item>();

        db.collection("MeonGaeBoard")    //firebase에 테이블명 잘못 적어서 여기도..
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mgbTitle = String.valueOf(document.get("mgbTitle"));
                        mgbContent = String.valueOf(document.get("mgbContent"));
                        boardTab2Items.add(new BoardTab2Item(mgbTitle,mgbContent));
                    }
                    final Tab2Adapter tAdapter = new Tab2Adapter(root.getContext(), boardTab2Items);
                    listView.setAdapter(tAdapter);
                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });
//        boardTab2Items.add(new BoardTab2Item("tab2 제목","tab2 내용글"));
//        boardTab2Items.add(new BoardTab2Item("tab2 제목22","tab2 내용글2"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
