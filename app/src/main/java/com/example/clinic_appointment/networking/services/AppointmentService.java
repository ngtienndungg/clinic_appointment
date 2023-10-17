package com.example.clinic_appointment.networking.services;

import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Department.DepartmentResponse;
import com.example.clinic_appointment.models.Hospital.HospitalResponse;
import com.example.clinic_appointment.models.User.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AppointmentService {
    @POST("user/login")
    @FormUrlEncoded
    Call<UserResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("user/refreshtoken")
    Call<JsonObject> refreshToken(@Header("Set-Cookie") String refreshToken);

    @GET("user/current")
    Call<Void> getCurrent(@Header("Authorization") String accessToken);

    @GET("clinic")
    Call<HospitalResponse> getFilteredClinic();

    @GET("specialty")
    Call<DepartmentResponse> getFilteredDepartment();
}
