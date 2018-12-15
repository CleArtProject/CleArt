package com.example.pascalisnala.cleart.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.fragments.HomeFragment;
import com.example.pascalisnala.cleart.fragments.ProfileFragment;
import com.example.pascalisnala.cleart.fragments.SearchFragment;
import com.example.pascalisnala.cleart.fragments.UserReportFragment;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigationView = findViewById(R.id.btmNav);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());

    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ConstraintLayout, fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedin()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                fragment = new HomeFragment();
                item.setChecked(true);
                break;
            case R.id.menu_search:
                fragment = new SearchFragment();
                item.setChecked(true);
                break;
            case R.id.menu_report:
                fragment = new UserReportFragment();
                item.setChecked(true);
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                item.setChecked(true);
                break;
        }

        if (fragment != null) {
            displayFragment(fragment);
        }

        return false;
    }
}
