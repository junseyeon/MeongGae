package com.example.meong_gae;

import androidx.appcompat.app.AppCompatActivity;


import com.example.meong_gae.databinding.ActivityModifyPwdBinding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ModifyPwd extends AppCompatActivity {

    private ActivityModifyPwdBinding binding;

    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());   //XML을 객체화 시킴

        SharedPreferences sharedPreferences = getSharedPreferences("pwd",MODE_PRIVATE);
        pwd = sharedPreferences.getString("pwd","");


        binding.cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pwd == binding.currentpwdedt.getText().toString())
                {
                    if(binding.newpwdagainedt.getText().toString() == binding.newpwdedt.getText().toString())
                    {
                        Toast.makeText(ModifyPwd.this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ModifyPwd.this, "다시확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ModifyPwd.this, "기존 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


}