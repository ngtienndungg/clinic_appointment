package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivityLoginBinding;
import com.example.clinic_appointment.databinding.ActivityMainBinding;
import com.example.clinic_appointment.models.User.UserResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventHandling();
    }

    private void eventHandling() {
        binding.btLogin.setOnClickListener(v -> {
            Call<UserResponse> call = RetrofitClient.getPublicAppointmentService()
                    .login(binding.etEmail.getText().toString(), binding.etEmail.getText().toString());
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    UserResponse userResponse = response.body();
                    if (response.body() != null && response.body().isSuccess()) {
                        sharedPrefs.putData(Constants.KEY_ACCESS_TOKEN, response.headers().get(Constants.HEADER_AUTHORIZATION));
                        sharedPrefs.putData(Constants.KEY_REFRESH_TOKEN, Objects.requireNonNull(response.headers().get(Constants.HEADER_SET_COOKIE)).split(Constants.REGEX_SEMICOLON)[0]);
                        sharedPrefs.putData(Constants.KEY_CURRENT_NAME, userResponse != null ? userResponse.getUser().getFullName() : null);
                        sharedPrefs.putData(Constants.KEY_CURRENT_PHONE_NUMBER, userResponse != null ? userResponse.getUser().getPhoneNumber() : null);
                        sharedPrefs.putData(Constants.KEY_CURRENT_EMAIL, userResponse != null ? userResponse.getUser().getEmail() : null);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, R.string.invalid_email_password, BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                    Snackbar.make(v, R.string.something_wrong, BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        });
    }
}