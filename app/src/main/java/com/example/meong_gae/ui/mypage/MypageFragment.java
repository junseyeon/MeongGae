package com.example.meong_gae.ui.mypage;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meong_gae.DogRegistration;
import com.example.meong_gae.Dogs;
import com.example.meong_gae.DogsAdapter;
import com.example.meong_gae.HomeActivity;
import com.example.meong_gae.LoginActivity;
import com.example.meong_gae.MypageUserSetting;
import com.example.meong_gae.R;
import com.example.meong_gae.databinding.ActivityHomeBinding;
import com.example.meong_gae.databinding.FragmentMypageBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MypageFragment extends Fragment {

    private FragmentMypageBinding binding;
    RecyclerView recyclerView;
    ArrayList<Dogs> dogsArrayList;
    DogsAdapter dogsAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MypageViewModel mypageViewModel = new ViewModelProvider(this).get(MypageViewModel.class);

        binding = FragmentMypageBinding.inflate(inflater, container, false);  //Mypage XML전체 id값을 담고 있음, findViewById대신 사용
        View root = binding.getRoot();  // getRoot() 메서드가 LinearLayout root View반환

        View view = inflater.inflate(R.layout.fragment_mypage,container,false);
        recyclerView = view.findViewById(R.id.mydog_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsArrayList = new ArrayList<>();

        Dogs ob1=new Dogs(R.drawable.dog,"happy","UNISEX","대형","1");
        dogsArrayList.add(ob1);
/*

        binding.mydogRecycle.setHasFixedSize(true);
        binding.mydogRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
*/

/*        db = FirebaseFirestore.getInstance();
        dogsArrayList = new ArrayList<Dogs>();


        event();*/
        SharedPreferences preferences = this.getActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
        binding.useremail.setText(preferences.getString("email",""));

        binding.username.setText("someone");

        binding.profileImage.setImageResource(R.drawable.flower);



        binding.addDogFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddog();
            }
        });


       /* progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching");
        progressDialog.show();*/
//
//        dogsAdapter = new DogsAdapter(root.getContext(),dogsArrayList);
//        recyclerView.setAdapter(dogsAdapter);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);

        return root;

    }

    public void adddog()
    {
        Intent intent = new Intent(getActivity(), DogRegistration.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {  //binding 결합 해제
        super.onDestroyView();
        binding = null;
    }

    public void event()
    {
        db.collection("TDog")
                .orderBy("dogname", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error != null)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges())
                {
                    if(dc.getType() == DocumentChange.Type.ADDED)
                    {
                        dogsArrayList.add(dc.getDocument().toObject(Dogs.class));
                    }
                    dogsAdapter.notifyDataSetChanged();

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }

}