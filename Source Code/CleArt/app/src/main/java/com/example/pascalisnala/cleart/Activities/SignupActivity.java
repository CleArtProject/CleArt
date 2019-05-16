package com.example.pascalisnala.cleart.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.responses.defaultResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {


    private EditText etName, etUsername, etEmail, etPhonenum, etPassword, etRepeatpass;
    private Button btnContinue;
    private ImageView btnback;
    ProgressBar pgSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.tvHome);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhonenum = findViewById(R.id.etPhonenum);
        etPassword = findViewById(R.id.etPassword);
        etRepeatpass = findViewById(R.id.etRepeatpass);

        btnback = findViewById(R.id.btnBack);

        btnContinue = findViewById(R.id.btnContinue);

        pgSignUp = findViewById(R.id.pgSignUp);
        pgSignUp.setVisibility(View.GONE);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignup();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedin()) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignup() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phonenum = etPhonenum.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repeatpass = etRepeatpass.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name Field Required");
            etName.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username Field Required");
            etUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email Field Required");
            etEmail.requestFocus();
            return;
        }

        if (phonenum.isEmpty()) {
            etPhonenum.setError("Please Fill With Your Phone Number");
            etPhonenum.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("password field required");
            etPassword.requestFocus();
            return;
        }

        if (repeatpass.isEmpty()) {
            etRepeatpass.setError("please repeat password");
            etRepeatpass.requestFocus();
            return;
        }

        if (!repeatpass.equals(password)) {
            etRepeatpass.setError("password not match ");
            etRepeatpass.requestFocus();
            return;
        }

        pgSignUp.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Call<defaultResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .createUser(name, username, email, phonenum, password);

        call.enqueue(new Callback<defaultResponse>() {
            @Override
            public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgSignUp.setVisibility(View.GONE);

                if (response.code() == 201) {
                    defaultResponse dr = response.body();

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignupActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.loginsuccess_popup, null);
                    Button mokay = (Button) mView.findViewById(R.id.btnAccept);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    mokay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(SignupActivity.this, "User Already Exist   ", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(SignupActivity.this);
                    View mView1 = getLayoutInflater().inflate(R.layout.signupfailed_popup, null);
                    Button okay = (Button) mView1.findViewById(R.id.btnokay);

                    mBuilder1.setView(mView1);
                    final AlertDialog dialog = mBuilder1.create();
                    dialog.show();

                    okay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<defaultResponse> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgSignUp.setVisibility(View.GONE);
            }
        });


    }
}
