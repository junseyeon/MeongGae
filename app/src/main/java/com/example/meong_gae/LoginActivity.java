package com.example.meong_gae;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.meong_gae.databinding.ActivityLoginBinding;
import com.example.meong_gae.ui.board.BoardTab1Item;
import com.example.meong_gae.ui.board.Tab1Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding lbinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lbinding = com.example.meong_gae.databinding.ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(lbinding.getRoot());


        lbinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnlogin(lbinding.etLoginId.getText().toString(),lbinding.etPassword.getText().toString());
            }
        });

        lbinding.findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPwd();
            }
        });

        lbinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignup();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void btnlogin(String email, String pwd)
    {

     db.collection("TUser")
             .whereEqualTo("email",email)
             .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
         @Override
         public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
             if (task.isSuccessful()) {
                 for (QueryDocumentSnapshot document : task.getResult()) {
                     String db_pwd = String.valueOf(document.get("pwd"));
                     if(db_pwd.equals(pwd))
                     {
                         Toast.makeText(LoginActivity.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                         SharedPreferences sharedPreferences = getSharedPreferences("email",MODE_PRIVATE);
                         SharedPreferences.Editor editor = sharedPreferences.edit();

                         editor.putString("email", email);
                         editor.putString("pwd", pwd);

                         editor.commit();
                         ((Global) getApplication()).setEmail(email);
                         Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                         startActivity(intent);
                     }
                     else
                     {
                         Toast.makeText(LoginActivity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                     }
                 }

             }
             else {
                 Log.w("TAG", "Error LoginActivity", task.getException());
                 Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다..", Toast.LENGTH_SHORT).show();
             }
         }
     });

    }

    private void findPwd()
    {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
    }

    private void btnSignup()
    {
        Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
        startActivity(intent);
    }
}