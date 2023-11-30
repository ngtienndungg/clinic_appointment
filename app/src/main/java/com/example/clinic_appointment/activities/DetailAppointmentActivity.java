package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.databinding.ActivityDetailAppointmentBinding;
import com.example.clinic_appointment.models.Appointment.DetailAppointment;
import com.example.clinic_appointment.models.Appointment.DetailAppointmentResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.CustomConverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAppointmentActivity extends AppCompatActivity {
    private ActivityDetailAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        String appointmentID = getIntent().getStringExtra(Constants.KEY_BOOKING_ID);
        Call<DetailAppointmentResponse> call = RetrofitClient.getPublicAppointmentService().getAppointmentById(appointmentID);
        call.enqueue(new Callback<DetailAppointmentResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<DetailAppointmentResponse> call, @NonNull Response<DetailAppointmentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DetailAppointment detailAppointment = response.body().getAppointment();
                    binding.tvHealthFacility.setText(detailAppointment.getSchedule().getDoctor().getHealthFacility().getName());
                    binding.tvAddress.setText(detailAppointment.getSchedule().getDoctor().getHealthFacility().getAddressString());
                    binding.tvDepartment.setText(detailAppointment.getSchedule().getDoctor().getDepartmentInformation().getName());
                    binding.tvDoctor.setText(detailAppointment.getSchedule().getDoctor().getDoctorInformation().getFullName());
                    binding.tvDate.setText(CustomConverter.getFormattedDate(detailAppointment.getSchedule().getDate()));
                    binding.tvTime.setText(CustomConverter.getStringAppointmentTime(detailAppointment.getAppointmentTime()));
                    binding.tvStatus.setText(detailAppointment.getStatus());
                    binding.tvPatientName.setText(detailAppointment.getPatientInformation().getFullName());
                    binding.tvPhoneNumber.setText(detailAppointment.getPatientInformation().getPhoneNumber());
                    binding.tvGender.setText(detailAppointment.getPatientInformation().getGenderVietnamese());
                    binding.tvPrice.setText(detailAppointment.getSchedule().getPrice() + " VND");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailAppointmentResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void eventHandling() {
        binding.tvConfirm.setOnClickListener(v -> {

        });
    }
}