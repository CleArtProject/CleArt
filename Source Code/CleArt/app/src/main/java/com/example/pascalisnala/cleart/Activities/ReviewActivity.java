package com.example.pascalisnala.cleart.Activities;

import android.app.AlertDialog;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.Review;
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

public class ReviewActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageView imgreport;
    private TextView judulTV;
    private RatingBar ratingBar;
    private EditText kolomTV;
    private Button addbtn;

    ProgressBar pgReview;

    private static final int PICK_IMAGE = 100, TAKE_PHOTO = 200;

    private Uri imageUri;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        pgReview = findViewById(R.id.pgReview);
        pgReview.setVisibility(View.GONE);

        CardView cardView = (CardView) findViewById(R.id.addPhoto);
        imgreport = (ImageView) findViewById(R.id.imgReport);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ReviewActivity.this);
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

        ratingBar = findViewById(R.id.ratingbar);
        kolomTV = findViewById(R.id.kolomTV);
        addbtn = findViewById(R.id.addbtn);

        btnBack = findViewById(R.id.btnback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        judulTV = findViewById(R.id.judulTV);
        judulTV.setText(getIntent().getStringExtra("attrname"));

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bmp!=null || imageUri!=null){
                    NewReview();
                }else {
                    Toast.makeText(ReviewActivity.this, "Please add image!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            bmp = null;
        }
        else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            Bundle bundle = data.getExtras();
            bmp = (Bitmap) bundle.get("data");
            imgreport.setImageBitmap(bmp);
            imageUri = null;
        }

    }

    public  void NewReview(){
        User user = SharedPrefManager
                .getInstance(this)
                .getUser();

        int userid = user.getUserid();
        int attrid = getIntent().getIntExtra("attrid",-1);

        final int rating = (int) ratingBar.getRating();
        String review = kolomTV.getText().toString().trim();

        if(review.isEmpty()){
            kolomTV.setError("Please fill the kind of issues!");
            kolomTV.requestFocus();
            return;
        }

        pgReview.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("userid",createPartFromString(String.valueOf(userid)));
        map.put("rating",createPartFromString(String.valueOf(rating)));
        map.put("review",createPartFromString(review));
        map.put("attrid",createPartFromString(String.valueOf(attrid)));

        MultipartBody.Part body;

        if(bmp!=null){
            body = getBody(bmp);
        }else{
            body = getBody(imageUri);
        }



        Call<defaultResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .newReview(body,map);

        call.enqueue(new Callback<defaultResponse>() {
            @Override
            public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgReview.setVisibility(View.GONE);

                if (response.code() == 201) {

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReviewActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.reviewsuccess_popup, null);
                    Button mokay = mView.findViewById(R.id.btnreview);

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
                    Toast.makeText(ReviewActivity.this, "Some Error Occur, Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<defaultResponse> call, Throwable t) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                pgReview.setVisibility(View.GONE);

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
