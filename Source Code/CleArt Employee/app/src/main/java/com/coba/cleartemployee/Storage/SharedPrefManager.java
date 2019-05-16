package com.coba.cleartemployee.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.coba.cleartemployee.models.Employee;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;

    }


    public void saveUser(Employee employee) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("attrid", employee.getAttrid());
        editor.putInt("empid", employee.getEmpid());
        editor.putString("name", employee.getName());
        editor.putString("phonenum", employee.getPhonenum());
        editor.putString("username", employee.getUsername());

        editor.apply();

    }

    public boolean isLoggedin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("empid", -1) != -1;
    }

    public Employee getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Employee employee = new Employee(
                sharedPreferences.getInt("attrid",-1),
                sharedPreferences.getInt("empid",-1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("phonenum", null),
                sharedPreferences.getString("username", null)
        );
        return employee;
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
