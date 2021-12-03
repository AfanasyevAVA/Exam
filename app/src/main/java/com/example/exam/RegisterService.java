package com.example.exam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

}
