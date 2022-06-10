package com.example.meong_gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meong_gae.databinding.ActivitySignupCheckBinding;

public class SignupCheckActivity extends AppCompatActivity {

    private ActivitySignupCheckBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.closeSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}