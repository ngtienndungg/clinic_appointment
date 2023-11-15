package com.example.clinic_appointment.networking.interceptors;

import androidx.annotation.NonNull;

import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.networking.services.AppointmentService;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentSessionInterceptor implements Interceptor {
    SharedPrefs sharedPrefs = SharedPrefs.getInstance();

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (isAccessTokenExpired()) {
            getNewAccessToken();
        }
        String accessToken = sharedPrefs.getData(Constants.KEY_ACCESS_TOKEN, String.class);
        return chain.proceed(chain.request().newBuilder()
                .header(Constants.HEADER_AUTHORIZATION, accessToken)
                .build());
    }

    private boolean isAccessTokenExpired() throws IOException {
        boolean isExpired = false;
        retrofit2.Response<Void> response = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppointmentService.class)
                .getCurrent(SharedPrefs.getInstance().getData(Constants.KEY_ACCESS_TOKEN, String.class))
                .execute();
        if (response.code() == 401) {
            isExpired = true;
        }
        return isExpired;
    }

    private void getNewAccessToken() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC);
        Call<JsonObject> call = new Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build())
                .build()
                .create(AppointmentService.class)
                .refreshToken(SharedPrefs.getInstance().getData(Constants.KEY_REFRESH_TOKEN, String.class));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull retrofit2.Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonResponse = response.body();
                    String newAccessToken = jsonResponse.get(Constants.KEY_NEW_ACCESS_TOKEN).getAsString();
                    sharedPrefs.putData(Constants.KEY_ACCESS_TOKEN, newAccessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });
    }
}
