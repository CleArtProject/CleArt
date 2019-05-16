package com.example.pascalisnala.cleart.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.responses.defaultResponse;
import com.example.pascalisnala.cleart.responses.loginResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

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

public class EditProfileActivity extends AppCompatActivity {

    EditText nameET,usernameET,useremailET,usernumberET;
    ImageView imgProfileEdit;
    Button btnsave;

    String image;

    private static final int PICK_IMAGE = 100, TAKE_PHOTO = 200;

    private Bitmap bitmap = null;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameET = findViewById(R.id.nameTV);
        usernameET = findViewById(R.id.usernameTV);
        useremailET = findViewById(R.id.useremailTV);
        usernumberET = findViewById(R.id.usernumberTV);
        btnsave = findViewById(R.id.btnlogout);
        imgProfileEdit = findViewById(R.id.imgpofileedit);

        if(getIntent().hasExtra("image")){
            image = getIntent().getStringExtra("image");
            Log.d("image", "onCreate: "+image);
        }


        if(image!=null){
            String url = retrofitClient.BASE_URL+"/uploads/user_images/"+image;

            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(imgProfileEdit);
        }

        imgProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfileActivity.this);
                builder1.setMessage("Profile Picture");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Upload Image",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                final android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(EditProfileActivity.this);
                                View mView = getLayoutInflater().inflate(R.layout.choose_popup, null);
                                CardView cameracv = mView.findViewById(R.id.cameraCV);
                                CardView gallerycv = mView.findViewById(R.id.galleryCV);

                                mBuilder.setView(mView);
                                final android.support.v7.app.AlertDialog dialog1 = mBuilder.create();
                                dialog1.show();

                                gallerycv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.dismiss();
                                        openGallery();
                                    }
                                });
                                cameracv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.dismiss();
                                        openCamera();
                                    }
                                });
                            }
                        });

                builder1.setNegativeButton(
                        "Delete Existing",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                User user = SharedPrefManager
                                        .getInstance(EditProfileActivity.this)
                                        .getUser();

                                Call<defaultResponse> call = retrofitClient
                                        .getInstance()
                                        .getApi()
                                        .deleteProfilePicture(user.getUserid());
                                call.enqueue(new Callback<defaultResponse>() {
                                    @Override
                                    public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {
                                        imgProfileEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_black));
                                        Toast.makeText(EditProfileActivity.this, "Image Deleted", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<defaultResponse> call, Throwable t) {
                                        Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
                if(bitmap!=null || imageUri!=null){
                    updateImage();
                }

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
                if(!response.body().isError()) {
                    SharedPrefManager.getInstance(EditProfileActivity.this).saveUser(response.body().getUser());
                    Toast.makeText(EditProfileActivity.this, "Update Successful please refresh this page!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateImage(){
        User user = SharedPrefManager
                .getInstance(this)
                .getUser();

        int userid = user.getUserid();

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id", createPartFromString(String.valueOf(userid)));

        MultipartBody.Part body;

        if(bitmap!=null){
            body = getBody(bitmap);
        }else{
            body = getBody(imageUri);
        }

        Call<defaultResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .updateProfilePicture(body,map);

        call.enqueue(new Callback<defaultResponse>() {
            @Override
            public void onResponse(Call<defaultResponse> call, Response<defaultResponse> response) {
                if (response.code() == 201) {
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Some Error Occur, Please Try Again!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<defaultResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        imgProfileEdit = findViewById(R.id.imgpofileedit);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgProfileEdit.setImageURI(imageUri);
            bitmap = null;
        } else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            imgProfileEdit.setImageBitmap(bitmap);
            imageUri = null;
        }

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
