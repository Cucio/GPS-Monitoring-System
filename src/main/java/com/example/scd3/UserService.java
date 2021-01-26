package com.example.scd3;

import com.example.scd3.Request.LocationRequest;
import com.example.scd3.Request.LoginRequest;
import com.example.scd3.Request.RegisterRequest;
import com.example.scd3.Response.LocationResponse;
import com.example.scd3.Response.LoginResponse;
import com.example.scd3.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

  @POST("/users/login")
  Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

  @POST("/users/register")
  Call<RegisterResponse> userRegister(@Body RegisterRequest registerRequest);

  @POST("/locations/add")
  Call<LocationResponse> addLocation(@Body LocationRequest locationRequest,@Header("Authorization") String authHeader);


}
