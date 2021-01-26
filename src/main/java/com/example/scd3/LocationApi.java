package com.example.scd3;

import com.example.scd3.Response.LocationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocationApi {
    @FormUrlEncoded
    @POST("/locations/add")
    Call<LocationResponse> insertLocation(@Field("email") String email, @Field("latitude") String latitude, @Field("longitude") String longitude);
}
