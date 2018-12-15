package com.example.pascalisnala.cleart.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.defaultResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView imgreport;;
    private Button submitReport;
    private EditText SpecificForm, kind_form, comment_form;
    private TextView tvAttrName;

    ProgressBar pgReport;

    private static final int PICK_IMAGE = 100, TAKE_PHOTO = 200;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        CardView cardView = (CardView) findViewById(R.id.addPhoto);
        imgreport = (ImageView) findViewById(R.id.imgReport);

        pgReport = findViewById(R.id.pgReport);
        pgReport.setVisibility(View.GONE);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ReportActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.choose_popup,null);
                CardView cameracv = (CardView) mView.findViewById(R.id.cameraCV);
                CardView gallerycv = (CardView) mView.findViewById(R.id.galleryCV);

                mBuilder.setView(mView);
                final android.support.v7.app.AlertDialog dialog = mBuilder.create();
                dialog.show();

                gallerycv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openGallery();
                    }
                });
                cameracv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCamera();
                    }
                });
            }

        });

        backButton = findViewById(R.id.backButton);
        submitReport = findViewById(R.id.submitReport);

        SpecificForm = findViewById(R.id.specific_form);
        kind_form = findViewById(R.id.kind_form);
        comment_form = findViewById(R.id.comment_form);

        tvAttrName = findViewById(R.id.tvAttrName);
        tvAttrName.setText(getIntent().getStringExtra("attrname"));



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewReport();
            }
        });

    }

    public void backToAttr() {
        Intent i = new Intent(this, DetailActivity.class);
        startActivity(i);
    }

    private void openGallery() {
        Intent pickPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(pickPicture,PICK_IMAGE);
    }
    private void openCamera(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult( takePicture,TAKE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgreport = findViewById(R.id.imgReport);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgreport.setImageURI(imageUri);
        }
        else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            Bundle bundle = data.getExtras();
            final Bitmap bmp = (Bitmap) bundle.get("data");
            imgreport.setImageBitmap(bmp);
        }

    }

    public void NewReport(){
        User user = SharedPrefManager
                .getInstance(this)
                .getUser();

        int userid = user.getUserid();
        int attrid = getIntent().getIntExtra("attrid",-1);

        String specific = SpecificForm.getText().toString().trim();
        String issues = kind_form.getText().toString().trim();
        String comment = comment_form.getText().toString().trim();

        if(specific.isEmpty()){
            SpecificForm.setError("Please fill the specific location!");
            SpecificForm.requestFocus();
            return;
        }

        if(issues.isEmpty()){
            kind_form.setError("Please fill the kind of issues!");
            kind_form.requestFocus();
            return;
        }

        if(comment.isEmpty()){
            comment_form.setError("Please fill the comment!");
            comment_form.requestFocus();
            return;
        }

        pgReport.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Call<defaultResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .newReport(userid,specific,issues,comment,attrid);

        call.enqueue(new Callback<defaultResponse>() {
            @Override
            public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgReport.setVisibility(View.GONE);
                if (response.code() == 201) {

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReportActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.reportsuccess_popup, null);
                    Button mokay = mView.findViewById(R.id.btnreport);

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
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    pgReport.setVisibility(View.GONE);

                    Toast.makeText(ReportActivity.this, "Some Error Occur, Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse> call, Throwable t) {

            }
        });
    }


}

