package com.example.clinic_appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.models.User;
import com.example.clinic_appointment.networking.RetrofitClient;
import com.example.clinic_appointment.utilities.SharedPrefs;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;

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
    }


    private void eventHandling() {
        btLogin.setOnClickListener(v -> {
            RetrofitClient.getAppointmentService().login(etEmail.getText().toString(), etPassword.getText().toString()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        Log.d("AccessTokenTest", "Code = 200");
                    }
                    Log.d("AccessTokenTest", response.code() + " ");
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failed:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}