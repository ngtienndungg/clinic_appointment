package com.example.clinic_appointment.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewMapping();
        eventHandling();
    }


    private void viewMapping() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        sharedPrefs = SharedPrefs.getInstance();
    }


    private void eventHandling() {
        btLogin.setOnClickListener(v -> {
            Call<UserResponse> call = RetrofitClient.getPublicAppointmentService()
                    .login(etEmail.getText().toString(), etPassword.getText().toString());
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    if (response.body() != null && response.body().isSuccess()) {
                        sharedPrefs.putData(Constants.KEY_ACCESS_TOKEN, response.headers().get(Constants.HEADER_AUTHORIZATION));
                        sharedPrefs.putData(Constants.KEY_REFRESH_TOKEN, Objects.requireNonNull(response.headers().get(Constants.HEADER_SET_COOKIE)).split(Constants.REGEX_SEMICOLON)[0]);
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