package com.example.lab5_androidapi_pd07826;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab5_androidapi_pd07826.models.Response;
import com.example.lab5_androidapi_pd07826.models.User;
import com.example.lab5_androidapi_pd07826.services.HttpRequest;

import retrofit2.Call;
import retrofit2.Callback;
public class Login extends AppCompatActivity {
    private EditText edtUsername,edtPassword;
    private Button btnLogin,btnRegister;
    private HttpRequest request;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        request = new HttpRequest();
        btnRegister.setOnClickListener(v->{
            startActivity(new Intent(Login.this,Register.class));
        });
        btnLogin.setOnClickListener(v->{
            progressDialog.show();
            User user = new User();
            String _username = edtUsername.getText().toString().trim();
            String _password = edtPassword.getText().toString().trim();
            user.setUsername(_username);
            user.setPassword(_password);
            request.callAPI()
                    .login(user)
                    .enqueue(responseUser);

        });
    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(@NonNull Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus()==200){
                    Toast.makeText(Login.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("refreshToken", response.body().getRefreshToken());
                    editor.putString("id",response.body().getData().get_id());
                    editor.apply();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    progressDialog.dismiss();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            Log.d("phuocdz", "onFailure: "+t.getMessage());
        }
    };
    public void mapping(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

    }
}