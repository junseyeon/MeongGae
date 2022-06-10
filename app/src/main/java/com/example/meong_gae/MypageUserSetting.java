package com.example.meong_gae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.meong_gae.databinding.MypageUserSettingBinding;

public class MypageUserSetting extends AppCompatActivity {

    private MypageUserSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MypageUserSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

     binding.profileEdit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             ProfilwEdit();
         }
     });
        binding.eamilEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailEdit();
            }
        });

        binding.pwdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PwdEdit();
            }
        });


    }

    private void ProfilwEdit()
    {
        Intent intent = new Intent(MypageUserSetting.this, ModifyProfile.class);

        startActivity(intent);	//intent 에 명시된 액티비티로 이동

    }

    private void EmailEdit()
    {
        Intent intent = new Intent(MypageUserSetting.this, ModifyEmail.class);

        startActivity(intent);	//intent 에 명시된 액티비티로 이동

    }

    private void PwdEdit()
    {
        Intent intent = new Intent(MypageUserSetting.this, ModifyPwd.class);

        startActivity(intent);	//intent 에 명시된 액티비티로 이동

    }

}