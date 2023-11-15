package com.example.clinic_appointment.networking.clients;

import com.example.clinic_appointment.networking.interceptors.CurrentSessionInterceptor;
import com.example.clinic_appointment.networking.services.AppointmentService;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "http://192.168.208.250:5000/api/";
    static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static Retrofit authenticatedRetrofit = null;
    private static Retrofit publicRetrofit = null;

    public static AppointmentService getAuthenticatedAppointmentService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CurrentSessionInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
        if (authenticatedRetrofit == null) {
            authenticatedRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return authenticatedRetrofit.create(AppointmentService.class);
    }

    public static AppointmentService getPublicAppointmentService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        if (publicRetrofit == null) {
            publicRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe()).create()))
                    .build();
        }
        return publicRetrofit.create(AppointmentService.class);
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDate read(final JsonReader jsonReader) throws IOException {
            return LocalDate.parse(jsonReader.nextString());
        }
    }
}