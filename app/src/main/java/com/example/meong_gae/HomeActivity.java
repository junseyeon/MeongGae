package com.example.meong_gae;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meong_gae.ui.board.BoardFragment;
import com.example.meong_gae.ui.home.HomeFragment;
import com.example.meong_gae.ui.mypage.MypageFragment;
import com.example.meong_gae.ui.reserve.ReserveDetail;
import com.example.meong_gae.ui.reserve.ReserveFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.meong_gae.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ActivityHomeBinding binding;
    private Toolbar toolbar;

    private ImageButton buttonDrawer;  //햄버거 바
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView toolbarName;

    MypageFragment mypage;
    MypageFragment mypageFragment = new MypageFragment();
    ReserveFragment reserve;
    BoardFragment board;
    HomeFragment home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());   //XML을 객체화 시킴
        toolbarName = findViewById(R.id.my_toolbar_text);

        mypage= new MypageFragment();
        reserve = new ReserveFragment();
        board = new BoardFragment();
        home = new HomeFragment();

        toolbar = binding.myToolbar;  //(Toolbar)findViewById(R.id.my_toolbar); 대신
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarName.setText("애견 유치원");

        BottomNavigationView navView = findViewById(R.id.nav_view);   //BottomNavigationView ID **
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_reserve, R.id.navigation_mypage, R.id.navigation_board)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        buttonDrawer = binding.btDrawer;
        buttonDrawer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                drawerLayout = binding.drawerLayout;
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);     //터치동작으로 열림 잠금
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }

            }
        });

        navigationView=binding.sideNav;
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_mypage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.toolbar_pencil:
                Intent intent = new Intent(HomeActivity.this, MypageUserSetting.class); //fragment라서 activity intent와 다름
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //** 겹쳐서 보여서 실패..
    // Activity -> Frament이동        (Fragment간 이동은 API28이후는 FragmentActivity 상속 받아야 가능..)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.side_home:
               // getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,home).commit();
                break;
            case R.id.side_reserve:
               // getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,reserve).commit();
                break;
            case R.id.side_mypage:
               // getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,mypage).commit();
                break;
            case R.id.side_board:
               // getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout,board).commit();
                break;
            case R.id.side_setting:
                // 설정 파일 만들면 추가하기 **
                break;
            case R.id.bt_sidebar_close:    //drawer 상단에 있는 close iamgebutton
                drawerLayout.closeDrawer(GravityCompat.START);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

//    shr
    @Override
    public void onBackPressed() {
        finish();
    }
}