package com.example.clinic_appointment.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivityLoginBinding;
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
    private final SharedPrefs sharedPrefs = SharedPrefs.getInstance();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventHandling();
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.btLogin.setOnClickListener(v -> {
            Call<UserResponse> call = RetrofitClient.getPublicAppointmentService()
                    .login(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
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
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        if (!Objects.equals(sharedPrefs.getData(Constants.KEY_ACCESS_TOKEN, String.class), "")) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        super.onStart();
    }
}