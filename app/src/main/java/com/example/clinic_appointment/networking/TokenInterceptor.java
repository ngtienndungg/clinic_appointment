package com.example.clinic_appointment.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenInterceptor implements Interceptor {
    private String accessToken;
    private String refreshToken;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (isAccessTokenExpired()) {
            Log.d("AccessTokenTest", "No valid");
            getNewAccessToken();
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTBhNmM2YWExNTU3MjlmYmFkYmI4YzIiLCJyb2xlIjozLCJpYXQiOjE2OTU0NTUwMzYsImV4cCI6MTY5NTYyNzgzNn0.V97ag_LHmFuQuqsaB5L30nGXtFy57WFezskh4gjNPYw")
                    .build();
            return chain.proceed(newRequest);
        } else {
            Log.d("AccessTokenTest", "Still valid");
        }
        return chain.proceed(originalRequest);
    }

    private boolean isAccessTokenExpired() throws IOException {
        boolean isExpired = false;
        retrofit2.Response<Void> response = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppointmentService.class)
                .getCurrent("Bearer yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTBhNmM2YWExNTU3MjlmYmFkYmI4YzIiLCJyb2xlIjozLCJpYXQiOjE2OTU0NTUwMzYsImV4cCI6MTY5NTYyNzgzNn0.V97ag_LHmFuQuqsaB5L30nGXtFy57WFezskh4gjNPYw")
                .execute();
        if (response.code() == 401) {
            isExpired = true;
        }
        return isExpired;
    }

    private void getNewAccessToken() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        List<Cookie> cookies = new ArrayList<>();
        cookies.add(new Cookie.Builder()
                .domain("localhost")
                .path("/")
                .name("refreshToken")
                .value("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NTBhNmM3YWExNTU3MjlmYmFkYmI4YzUiLCJpYXQiOjE2OTU0NzMzNzIsImV4cCI6MTY5NjA3ODE3Mn0.s8YlWX-aqEdt9UHWJjMgdG_GNuVmaeIK8hh69EVve7M")
                .build());
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {

            }

            @NonNull
            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                return cookies;
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(loggingInterceptor)
                .build();
        new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppointmentService.class)
                .refreshToken()
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            JsonObject jsonResponse = response.body();
                            String newAccessToken = jsonResponse.get("newAccessToken").getAsString();

                            Log.d("AccessTokenTest", newAccessToken);
                        } else {
                            Log.d("AccessTokenTest", "Error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        Log.d("AccessTokenTest", t.getMessage());
                    }
                });
    }
}
