package com.mathome.app.interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mathome.app.R;
import com.mathome.app.fragments.AccountFragment;
import com.mathome.app.fragments.HomeFragment;
import com.mathome.app.fragments.HomeworkFragment;
import com.mathome.app.fragments.NotificationFragment;
import com.mathome.app.fragments.SearchFragment;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private HomeworkFragment homeworkFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        homeworkFragment = new HomeworkFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        setFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.option1:
                        setFragment(homeFragment);
                        return true;
                    case R.id.option2:
                        setFragment(searchFragment);
                        return true;
                    case R.id.option3:
                        setFragment(homeworkFragment);
                        return true;
                    case R.id.option4:
                        setFragment(notificationFragment);
                        return true;
                    case R.id.option5:
                        setFragment(accountFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
