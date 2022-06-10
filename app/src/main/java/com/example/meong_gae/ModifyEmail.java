package com.example.meong_gae;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meong_gae.databinding.ActivityLoginBinding;
import com.example.meong_gae.databinding.ActivityModifyEmailBinding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ModifyEmail extends AppCompatActivity {
    private ActivityModifyEmailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.meong_gae.databinding.ActivityModifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("email",MODE_PRIVATE);
        binding.nowemailedt.setText(sharedPreferences.getString("email",""));

        binding.cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ModifyEmail.this, "수정이 완료되었습니다", Toast.LENGTH_SHORT).show();

            }
        });


    }


}