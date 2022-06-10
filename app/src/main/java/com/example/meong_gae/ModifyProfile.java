package com.example.meong_gae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.meong_gae.databinding.ActivityModifyProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class ModifyProfile extends AppCompatActivity {

    private ActivityModifyProfileBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.meong_gae.databinding.ActivityModifyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profileImage.setImageResource(R.drawable.flower);
//        db.collection("TUser").whereEqualTo("email",ds.useremil).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        username = String.valueOf(document.get("name"));
//                        binding.profileName.setText(username);
//                    }
//                    }
//
//                else
//                {
//                    Toast.makeText(ModifyProfile.this, "로딩실패", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        binding.cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });


    }

    public void update()
    {
        Toast.makeText(this, "수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}