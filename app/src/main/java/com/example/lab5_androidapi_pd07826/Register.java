package com.example.lab5_androidapi_pd07826;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lab5_androidapi_pd07826.data.ChooseImage;
import com.example.lab5_androidapi_pd07826.models.Response;
import com.example.lab5_androidapi_pd07826.models.User;
import com.example.lab5_androidapi_pd07826.services.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Register extends AppCompatActivity {
    private EditText edtUser,edtPassword,edtEmail,edtName;
    private Button btnRegister, btnBack;
    private ImageView avatar;
    private File file;
    private HttpRequest request;
    private ProgressDialog progressDialog;
    private ChooseImage chooseImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mapping();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        request = new HttpRequest();
        chooseImage = new ChooseImage(this);
        btnBack.setOnClickListener(v->{
            startActivity(new Intent(Register.this, Login.class));
        });
        btnRegister.setOnClickListener(v->{
            progressDialog.show();
            RequestBody _username= RequestBody.create(MediaType.parse("multipart/form-data"),edtUser.getText().toString());
            RequestBody _password= RequestBody.create(MediaType.parse("multipart/form-data"),edtPassword.getText().toString());
            RequestBody _email= RequestBody.create(MediaType.parse("multipart/form-data"),edtEmail.getText().toString());
            RequestBody _name= RequestBody.create(MediaType.parse("multipart/form-data"),edtName.getText().toString());
            MultipartBody.Part imageRequest;
            if(file!=null){
                RequestBody _image= RequestBody.create(MediaType.parse("image/*"),file);
                imageRequest = MultipartBody.Part.createFormData("avatar", file.getName(),_image);
            }else {
                imageRequest =null;
            }
            request.callAPI()
                    .register(_username,_password,_email,_name,imageRequest)
                    .enqueue(responseUser);
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage.getChooseImage(getImage);
            }
        });


    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus()==200){
                    startActivity(new Intent(Register.this,Login.class));
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Register.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d("phuocdz", "onFailure: "+t.getMessage());
        }
    };

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode()== Activity.RESULT_OK){
                        Intent data  = o.getData();
                        Uri imagePath = data.getData();
                        file = chooseImage.createFileFromUri(imagePath,"avatar");
                        Glide.with(Register.this)
                                .load(file)
                                .thumbnail(Glide.with(Register.this).load(R.mipmap.loading))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(avatar);
                    }
                }
            });

    public void mapping(){
        edtUser = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        avatar = findViewById(R.id.avatar);
    }
}