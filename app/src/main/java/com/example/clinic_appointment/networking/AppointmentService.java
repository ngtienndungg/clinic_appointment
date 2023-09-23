package com.example.clinic_appointment.networking;

import com.example.clinic_appointment.models.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppointmentService {
    @POST("user/login")
    @FormUrlEncoded
    Call<User> login(@Field("email") String username, @Field("password") String password);

    @POST("user/refreshtoken")
    Call<JsonObject> refreshToken();

    @GET("user/current")
    Call<Void> getCurrent(@Header("Authorization") String accessToken);
}
