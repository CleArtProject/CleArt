package com.example.pascalisnala.cleart.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.Activities.EditProfileActivity;
import com.example.pascalisnala.cleart.Activities.MainActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    TextView nameTV, usernameTV, useremailTV, usernumberTV;
    ImageView imgpofile;
    User user1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    private TextView editTV;
    Button btnlogout;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTV = view.findViewById(R.id.nameTV);
        usernameTV = view.findViewById(R.id.usernameTV);
        useremailTV = view.findViewById(R.id.useremailTV);
        usernumberTV = view.findViewById(R.id.usernumberTV);
        imgpofile = view.findViewById(R.id.imgpofileedit);

        final User user = SharedPrefManager
                .getInstance(getActivity())
                .getUser();

        Call<User> call = retrofitClient
                .getInstance()
                .getApi()
                .getUser(user.getUserid());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user1 = response.body();

                if(user1.getImage()!=null){
                    String url = retrofitClient.BASE_URL+"/uploads/user_images/"+user1.getImage();

                    Picasso.get()
                            .load(url)
                            .fit()
                            .centerCrop()
                            .into(imgpofile);
                }

                nameTV.setText(user.getName());
                usernameTV.setText(user.getUsername());
                useremailTV.setText(user.getEmail());
                usernumberTV.setText(user.getPhonenum());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });





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
                i.putExtra("image",user1.getImage());
                startActivity(i);

            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Are you sure want to logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                SharedPrefManager.getInstance(getActivity()).clear();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }


}
