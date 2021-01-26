package com.example.scd3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.scd3.Request.RegisterRequest;
import com.example.scd3.Response.RegisterResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    Button btnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.edEmail);
        firstName = findViewById(R.id.edFirstName);
        lastName = findViewById(R.id.edLastName);
        password = findViewById(R.id.edPassword);
        confirmPassword = findViewById(R.id.edConfirmPassword);
        btnEnroll = findViewById(R.id.btnEnroll);

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(confirmPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "All fields must be completed", Toast.LENGTH_LONG).show();
                } else {
                    register();
                }
            }
        });
    }

    private void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setFirstName(firstName.getText().toString());
        registerRequest.setLastName(lastName.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setConfirmPassword(confirmPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getUserService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                    RegisterResponse registerResponse = response.body();
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Throwable" + t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}