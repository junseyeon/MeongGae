package com.example.meong_gae;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.meong_gae.ui.home.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Global extends Application {
    //private String email;
    private String email;    //합치기 전 임시로 테스트 위해서..
    private int ticketId ;            // 앱에서 진행할 반려견 수강권ID - 반려견 하나당 여러 수강권 가질 수 있고 여러 반려견이 있을 수 있음으로,,   //초기값은 0으로 둠

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}

//  <참고>
//  String email = ((Global) getApplication()).getEmail();  //getActivity().getApplication();
//  ((Global) getApplication()).setEmail('123@naver.com');
