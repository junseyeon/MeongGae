package com.example.meong_gae.ui.reserve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.meong_gae.R;

public class ReserveConfirmActivity extends AppCompatActivity {

    Button close;
    ReserveFragment reserveFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve_confirm);

        close=findViewById(R.id.bt_reserve_confirm_close);
        reserveFragment = new ReserveFragment();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reserve fragment로 돌아가기
               // getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_home,reserveFragment).commit();
                finish();
            }
        });
    }
}