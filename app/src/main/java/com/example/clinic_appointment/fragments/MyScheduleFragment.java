package com.example.clinic_appointment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.adapters.AppointmentManagementAdapter;
import com.example.clinic_appointment.databinding.FragmentMyScheduleBinding;
import com.example.clinic_appointment.listeners.AppointmentListener;
import com.example.clinic_appointment.models.Appointment.Appointment;
import com.example.clinic_appointment.models.Appointment.AppointmentResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScheduleFragment extends Fragment implements AppointmentListener {
    private FragmentMyScheduleBinding binding;
    private TextView currentOption = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyScheduleBinding.inflate(getLayoutInflater());
        initiate();
        eventHandling();
        return binding.getRoot();
    }

    private void initiate() {
        optionPerformClick(binding.tvWaitingConfirmation);
        getAppointments();
    }

    private void optionPerformClick(TextView newSelection) {
        if (currentOption == null) {
            setSelectedBackground(binding.tvWaitingConfirmation);
        } else {
            setUnselectedBackground(currentOption);
            setSelectedBackground(newSelection);
        }
        currentOption = newSelection;
        getAppointments();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setUnselectedBackground(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorMyScheduleOptionText));
        textView.setBackground(getResources().getDrawable(R.drawable.background_my_schedule_option_unselected));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setSelectedBackground(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setBackground(getResources().getDrawable(R.drawable.background_my_schedule_option_selected));
    }

    private void eventHandling() {
        binding.tvWaitingConfirmation.setOnClickListener(v -> optionPerformClick(binding.tvWaitingConfirmation));
        binding.tvConfirmed.setOnClickListener(v -> optionPerformClick(binding.tvConfirmed));
        binding.tvCancelled.setOnClickListener(v -> optionPerformClick(binding.tvCancelled));
        binding.tvEntire.setOnClickListener(v -> optionPerformClick(binding.tvEntire));
        binding.tvChecked.setOnClickListener(v -> optionPerformClick(binding.tvChecked));
        binding.ivBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void getAppointments() {
        binding.rvAppointments.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.VISIBLE);
        Call<AppointmentResponse> call = RetrofitClient.getAuthenticatedAppointmentService().getEntireAppointment();
        call.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<AppointmentResponse> call, @NonNull Response<AppointmentResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    List<Appointment> appointments = response.body().getBooking();
                    if (currentOption == binding.tvWaitingConfirmation) {
                        getAppointmentByStatus(appointments, "Đang xử lý");
                    } else if (currentOption == binding.tvConfirmed) {
                        getAppointmentByStatus(appointments, "Đã duyệt");
                    } else if (currentOption == binding.tvCancelled) {
                        getAppointmentByStatus(appointments, "Đã huỷ");
                    } else if (currentOption == binding.tvChecked) {
                        getAppointmentByStatus(appointments, "Đã khám");
                    }
                    AppointmentManagementAdapter adapter = new AppointmentManagementAdapter(MyScheduleFragment.this, appointments);
                    binding.rvAppointments.setAdapter(adapter);
                    binding.pbLoading.setVisibility(View.GONE);
                    binding.rvAppointments.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AppointmentResponse> call, @NonNull Throwable t) {
                binding.pbLoading.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), "Error", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void getAppointmentByStatus(List<Appointment> appointments, String status) {
        appointments.removeIf(appointment -> !appointment.getStatus().equals(status));
    }

    @Override
    public void onClick(Appointment appointment) {

    }
}