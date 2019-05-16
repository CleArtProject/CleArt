package com.example.pascalisnala.cleart.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import com.example.pascalisnala.cleart.responses.defaultResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ImageView imgreport;
    private Button submitReport;
    private EditText SpecificForm, kind_form, comment_form;
    private TextView tvAttrName;

    private Bitmap bitmap;
    private Uri imageUri;

    ProgressBar pgReport;

    private static final int PICK_IMAGE = 100, TAKE_PHOTO = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        CardView cardView = findViewById(R.id.addPhoto);
        imgreport = findViewById(R.id.imgReport);

        pgReport = findViewById(R.id.pgReport);
        pgReport.setVisibility(View.GONE);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ReportActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.choose_popup, null);
                CardView cameracv = mView.findViewById(R.id.cameraCV);
                CardView gallerycv = mView.findViewById(R.id.galleryCV);

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
                if(bitmap!=null || imageUri!=null){
                    NewReport();
                }else{
                    Toast.makeText(ReportActivity.this,"Please add image!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void openGallery() {
        Intent pickPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(pickPicture, PICK_IMAGE);
    }

    private void openCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgreport = findViewById(R.id.imgReport);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgreport.setImageURI(imageUri);
            bitmap = null;
        } else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            imgreport.setImageBitmap(bitmap);
            imageUri = null;
        }

    }

    public void NewReport() {
        User user = SharedPrefManager
                .getInstance(this)
                .getUser();

        int userid = user.getUserid();
        int attrid = getIntent().getIntExtra("attrid", -1);

        String specific = SpecificForm.getText().toString().trim();
        String issues = kind_form.getText().toString().trim();
        String comment = comment_form.getText().toString().trim();

        if (specific.isEmpty()) {
            SpecificForm.setError("Please fill the specific location!");
            SpecificForm.requestFocus();
            return;
        }

        if (issues.isEmpty()) {
            kind_form.setError("Please fill the kind of issues!");
            kind_form.requestFocus();
            return;
        }

        if (comment.isEmpty()) {
            comment_form.setError("Please fill the comment!");
            comment_form.requestFocus();
            return;
        }

        pgReport.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("userid", createPartFromString(String.valueOf(userid)));
        map.put("specific", createPartFromString(specific));
        map.put("issues", createPartFromString(issues));
        map.put("attrid", createPartFromString(String.valueOf(attrid)));
        map.put("comment", createPartFromString(comment));

        MultipartBody.Part body;

        if(bitmap!=null){
            body = getBody(bitmap);
        }else{
            body = getBody(imageUri);
        }


        Call<defaultResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .newReport(body,map);

        call.enqueue(new Callback<defaultResponse>() {
            @Override
            public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {

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
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgReport.setVisibility(View.GONE);
                Log.d("reportRetrofit", "onFailure: " + t.getMessage());

            }
        });
    }

    private MultipartBody.Part getBody(Bitmap bitmap){
        File file = createTempFile(bitmap);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("image", file.getName(), reqFile);
    }

    private MultipartBody.Part getBody(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
            cursor.close();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        File file = new File(result);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("image", file.getName(), reqFile);
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "_image.jpeg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

}

