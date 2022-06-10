package com.example.meong_gae.ui.reserve;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meong_gae.Global;
import com.example.meong_gae.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/*
        1. 예약마감이면 이 페이지로 넘어오지 않게 하기 [완료]

*/
public class ReserveDetail extends AppCompatActivity {

    private ImageButton close;
    private TextView classN, classN2, dateTime, teacher, num, classDetail, tInfo, tHistory;
    private String className, dateT, tea, nowNum,maxNum, tIntro, tHis, classIntro;
    private int classId;
    private Button reserve;
    ReserveFragment reserveFragment;

    int ticketId;
    String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_detail);

        email = ((Global) getApplication()).getEmail();
        ticketId = ((Global) getApplication()).getTicketId();
        init();

        //intent로 넘어온 값 받기
        Intent intent = getIntent();
        classId = intent.getIntExtra("classId",1024);
        dateT = intent.getStringExtra("dateTime");
        className = intent.getStringExtra("classN");
        tea = intent.getStringExtra("teacher");
        nowNum = intent.getStringExtra("nowNum");
        maxNum = intent.getStringExtra("maxNum");

        //나머지 값들은 db로 검색해서 변수에 넣기
        db.collection("Instructor")
                .whereEqualTo("tName",tea)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {    //어차피 항상 classId는 1개
                        tIntro = String.valueOf(document.get("tIntro"));
                        tHis = String.valueOf(document.get("tHistory"));

                        tHistory.setText(tHis);
                        tInfo.setText(tIntro);
                    }

                }
            }
        });

        db.collection("Class")
                .whereEqualTo("classId",classId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {    //어차피 항상 classId는 1개
                        classIntro = String.valueOf(document.get("classIntro"));
                        classDetail.setText(classIntro);
                    }
                }
            }
        });


        //setText로 값 변경하기
        dateTime.setText(dateT);
        classN.setText(className);
        classN2.setText(className);   //xml에서 같은 text보여주는 데 id값이 같으면 안되서
        num.setText("예약인원: 최대 수강인원" + nowNum + "/" + maxNum);
        teacher.setText(tea);

//        classDetail.setText(classIntro);
//        tHistory.setText(tHis);
//        tInfo.setText(tIntro);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reserve fragment로 돌아가기
              //  getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home,reserveFragment).commit();
                finish();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 예약 추가하고 예약 확정 화면 띄우기

                Map<String, Object> classR = new HashMap<>();
                classR.put("classId", classId);
                classR.put("email", email);
                classR.put("ticketId", ticketId);

                Log.v("데이터",classId + " "  + email + ticketId);

                db.collection("Reserve").document(email+String.valueOf(classId))
                        .set(classR)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                Intent intent = new Intent(ReserveDetail.this, ReserveConfirmActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });
                // 현재 예약인원 +1 (firestore update) [보류]
                // 내 정보 -1 하기
            }
        });

    }

    public void init(){
        close = (ImageButton) findViewById(R.id.reserve_detail_close);
        classN = (TextView) findViewById(R.id.reserve_detail_classN);
        classN2 = (TextView) findViewById(R.id.reserve_detail_classN2);
        dateTime= (TextView) findViewById(R.id.reserve_detail_dateTime);
        teacher= (TextView) findViewById(R.id.reserve_detail_teacher);
        num= (TextView) findViewById(R.id.reserve_detail_num);
        classDetail = (TextView) findViewById(R.id.reserve_detail_classDetail);
        tInfo = (TextView) findViewById(R.id.reserve_detail_tInfo);
        tHistory = (TextView) findViewById(R.id.reserve_detail_tHistory);
        reserve = (Button) findViewById(R.id.bt_reserve);

        reserveFragment= new ReserveFragment();
    }

}
