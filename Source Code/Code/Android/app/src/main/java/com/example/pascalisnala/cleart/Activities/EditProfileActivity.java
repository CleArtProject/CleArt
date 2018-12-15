package com.example.pascalisnala.cleart.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.loginResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameET,usernameET,useremailET,usernumberET;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameET = findViewById(R.id.nameTV);
        usernameET = findViewById(R.id.usernameTV);
        useremailET = findViewById(R.id.useremailTV);
        usernumberET = findViewById(R.id.usernumberTV);
        btnsave = findViewById(R.id.btnlogout);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        nameET = findViewById(R.id.nameTV);
        usernameET = findViewById(R.id.usernameTV);
        useremailET = findViewById(R.id.useremailTV);
        usernumberET = findViewById(R.id.usernumberTV);

        final User user = SharedPrefManager
                .getInstance(this)
                .getUser();

        nameET.setText(user.getName());
        usernameET.setText(user.getUsername());
        useremailET.setText(user.getEmail());
        usernumberET.setText(user.getPhonenum());

    }

    private void updateProfile(){
        String name = nameET.getText().toString().trim();
        String username = usernameET.getText().toString().trim();
        String email = useremailET.getText().toString().trim();
        String phonenum = usernumberET.getText().toString().trim();

        if (name.isEmpty()) {
            nameET.setError("Name Field Required");
            nameET.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usernameET.setError("Username Field Required");
            usernameET.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            useremailET.setError("Email Field Required");
            useremailET.requestFocus();
            return;
        }

        if (phonenum.isEmpty()) {
            usernumberET.setError("Please Fill With Your Phone Number");
            usernumberET.requestFocus();
            return;
        }

        final User user = SharedPrefManager
                .getInstance(this)
                .getUser();
        int userid = user.getUserid();

        Call<loginResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .updateUser(name,username,email,phonenum,userid);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(!response.body().isError()) {
                    SharedPrefManager.getInstance(EditProfileActivity.this).saveUser(response.body().getUser());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {

            }
        });


    }
}
