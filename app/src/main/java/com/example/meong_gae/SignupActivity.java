package com.example.meong_gae;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meong_gae.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.primitives.SignedBytes;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding sbinding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sbinding = com.example.meong_gae.databinding.ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(sbinding.getRoot());

        sbinding.nextFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

    }

    private void signup() {
        String email = sbinding.edtMail.getText().toString();
        String pwd = sbinding.edtPwd.getText().toString();
        String pwdcheck = sbinding.edtPwdAgain.getText().toString();
        String name = sbinding.edtName.getText().toString();

//        이름과 이메일은 null이 아닌이상 조건X
        if(email.equals("") || name.equals("") || pwd.equals(""))
        { Toast.makeText(getApplicationContext(),"이름과 이메일 비밀번호에 빈 칸이 없는지 다시 확인해주세요",Toast.LENGTH_SHORT).show(); }
        else
        {
            // 비밀번호를 입력했을때 값이 같다!
            if(pwd.equals(pwdcheck))
            { Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();

                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                HashMap<Object,String> usermap = new HashMap<>();

                usermap.put("pwd",pwd);
                usermap.put("email",email);
                usermap.put("name",name);

                db.collection("TUser")
                        .add(usermap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, SignupCheckActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(SignupActivity.this, "에러발생.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            //            비밀번호 입력이 다르다!
            else { Toast.makeText(getApplicationContext(),"비밀번호 입력이 다릅니다.",Toast.LENGTH_SHORT).show(); }
        }
    }
}



