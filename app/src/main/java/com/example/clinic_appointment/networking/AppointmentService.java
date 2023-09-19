package com.example.clinic_appointment.networking;

import com.example.clinic_appointment.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppointmentService {
    @POST("user/login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);
}
