package com.example.pascalisnala.cleart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pascalisnala.cleart.Activities.EditProfileActivity;
import com.example.pascalisnala.cleart.Activities.MainActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

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
        useremailTV = view.findViewById(R.id.useremailTV);
        usernumberTV = view.findViewById(R.id.usernumberTV);

        final User user = SharedPrefManager
                .getInstance(getActivity())
                .getUser();

        nameTV.setText(user.getName());
        usernameTV.setText(user.getUsername());
        useremailTV.setText(user.getEmail());
        usernumberTV.setText(user.getPhonenum());

        editTV = view.findViewById(R.id.editTV);
        btnlogout = view.findViewById(R.id.btnlogout);

        editTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                i.putExtra("name", user.getName());
                i.putExtra("username", user.getUsername());
                i.putExtra("email", user.getEmail());
                i.putExtra("phonenum", user.getPhonenum());
                startActivity(i);

            }
        });

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
