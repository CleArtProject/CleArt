package com.coba.cleartemployee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.coba.cleartemployee.Fragments.HistoryFragment;
import com.coba.cleartemployee.Fragments.HomeFragment;
import com.coba.cleartemployee.Fragments.ProfileFragment;
import com.coba.cleartemployee.R;
import com.coba.cleartemployee.Storage.SharedPrefManager;
import com.coba.cleartemployee.models.AttrReport;
import com.coba.cleartemployee.models.Employee;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private int attrid;
    Employee employee;
    AttrReport attrReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        employee = SharedPrefManager.getInstance(HomeActivity.this).getUser();

        attrid = employee.getAttrid();
//        Log.d("", employee.getName()+"");

        BottomNavigationView navigationView = findViewById(R.id.btmNav);
        navigationView.setOnNavigationItemSelectedListener(this);

        Bundle bundle = new Bundle();
        bundle.putInt("attrid",employee.getAttrid());
        bundle.putString("empname",employee.getName());
        bundle.putInt("empid", employee.getEmpid());
//        bundle.putInt("reportid", attrReport.getReportid());
        Fragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        displayFragment(fragment);

    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ConstraintLayout, fragment)
                .commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_home:
                Bundle bundle = new Bundle();
                bundle.putInt("attrid",attrid);
                bundle.putString("empname",employee.getName());
                bundle.putInt("empid",employee.getEmpid());;
//                bundle.putInt("reportid", attrReport.getReportid());
                fragment = new HomeFragment();
                fragment.setArguments(bundle);
                item.setChecked(true);
                break;
        }
        switch (item.getItemId()) {
            case R.id.menu_history:
                Bundle bundle = new Bundle();
                bundle.putInt("attrid",attrid);
                bundle.putString("empname",employee.getName());
                bundle.putInt("empid",employee.getEmpid());
//                bundle.putInt("reportid", attrReport.getReportid());
                fragment = new HistoryFragment();
                fragment.setArguments(bundle);
                item.setChecked(true);
                break;
        }
        switch (item.getItemId()) {
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
