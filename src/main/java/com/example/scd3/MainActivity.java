package com.example.scd3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.scd3.Request.LoginRequest;
import com.example.scd3.Response.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    Button loginBtn;
    Button registerBtn;

    public static String user, pwd;

    public TextInputEditText getUsername() {
        return username;
    }

    public void setUsername(TextInputEditText username) {
        this.username = username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        loginBtn = findViewById(R.id.btnLogin);
        registerBtn = findViewById(R.id.btnRegister);

        user = username.getText().toString();
        pwd = password.getText().toString();


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));

                    }
                }, 700);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Username/Password Required", Toast.LENGTH_LONG).show();
                } else {

                    login();

                }
            }
        });

    }


    public void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());



        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//                            intent.putExtra("userText", loginResponse.getEmail());
//                            new Intent(MainActivity.this, DashboardActivity.class).putExtra("userText",loginResponse.getEmail());
//                            new Intent(MainActivity.this, DashboardActivity.class).putExtra("passwordText",loginResponse.getEmail());
                            intent.putExtra("userText",loginRequest.getEmail());
                            intent.putExtra("passwordText",loginRequest.getPassword());

                            startActivity(intent.putExtra("data", loginResponse.getFirstName() + " " + loginResponse.getLastName()));


                        }
                    }, 700);
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Throwable" + t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}