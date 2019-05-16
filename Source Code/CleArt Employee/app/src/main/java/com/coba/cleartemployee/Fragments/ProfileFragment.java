package com.coba.cleartemployee.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coba.cleartemployee.Activities.MainActivity;
import com.coba.cleartemployee.R;
import com.coba.cleartemployee.Storage.SharedPrefManager;
import com.coba.cleartemployee.models.Employee;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView nameTV, usernameTV, useremailTV, usernumberTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    private TextView editTV;
    Button btnlogout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTV = view.findViewById(R.id.nameTV);
        usernameTV = view.findViewById(R.id.usernameTV);
        usernumberTV = view.findViewById(R.id.usernumberTV);

        final Employee employee = SharedPrefManager
                .getInstance(getActivity())
                .getUser();

        nameTV.setText(employee.getName());
        usernameTV.setText(employee.getUsername());
        usernumberTV.setText(employee.getPhonenum());

        btnlogout = view.findViewById(R.id.btnlogout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnlogout:
                logout();
        }
    }
}