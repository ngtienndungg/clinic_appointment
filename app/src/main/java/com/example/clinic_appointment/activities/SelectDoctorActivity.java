package com.example.clinic_appointment.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.adapters.ScheduleAdapter;
import com.example.clinic_appointment.adapters.SelectDoctorAdapter;
import com.example.clinic_appointment.databinding.ActivitySelectDoctorBinding;
import com.example.clinic_appointment.listeners.DoctorListener;
import com.example.clinic_appointment.listeners.ScheduleListener;
import com.example.clinic_appointment.models.AppointmentTime.AppointmentTime;
import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.Doctor.Doctor;
import com.example.clinic_appointment.models.Doctor.DoctorResponse;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.Schedule.DetailSchedule;
import com.example.clinic_appointment.models.Schedule.ScheduleResponse;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDoctorActivity extends AppCompatActivity implements DoctorListener, ScheduleListener {
    private ActivitySelectDoctorBinding binding;
    private Department selectedDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        if (getIntent().getStringExtra(Constants.KEY_SOURCE_ACTIVITY) == null) {
            selectedDepartment = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
            HealthFacility healthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
            Call<DoctorResponse> call = RetrofitClient.getPublicAppointmentService().getDoctorByDepartmentAndHealthFacility(selectedDepartment.getId(), healthFacility.getId());
            call.enqueue(new Callback<DoctorResponse>() {
                @Override
                public void onResponse(@NonNull Call<DoctorResponse> call, @NonNull Response<DoctorResponse> response) {
                    if (response.body() != null && response.body().isSuccess() && response.body().getDoctors().size() > 0) {
                        List<Doctor> doctors = response.body().getDoctors();
                        SelectDoctorAdapter adapter = new SelectDoctorAdapter(SelectDoctorActivity.this, doctors);
                        binding.rvDoctor.setAdapter(adapter);
                        binding.rvDoctor.setVisibility(View.VISIBLE);
                        binding.pbLoading.setVisibility(View.GONE);
                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DoctorResponse> call, @NonNull Throwable t) {
                    displayError();
                }
            });
        } else {
            AppointmentTime appointmentTime = (AppointmentTime) getIntent().getSerializableExtra(Constants.KEY_TIME);
            HealthFacility healthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
            Department department = (Department) getIntent().getSerializableExtra(Constants.KEY_DEPARTMENT);
            Call<ScheduleResponse> call = RetrofitClient.getPublicAppointmentService().getSchedules(
                    getIntent().getLongExtra(Constants.KEY_START_DATE, -1),
                    getIntent().getLongExtra(Constants.KEY_END_DATE, -1),
                    (appointmentTime != null) ? appointmentTime.getTimeNumber() : null,
                    (department) != null ? department.getName() : null,
                    (healthFacility) != null ? healthFacility.getName() : null,
                    null
            );
            call.enqueue(new Callback<ScheduleResponse>() {
                @Override
                public void onResponse(@NonNull Call<ScheduleResponse> call, @NonNull Response<ScheduleResponse> response) {
                    if (response.body() != null && response.body().isSuccess() && response.body().getSchedules().size() > 0) {
                        Set<String> uniqueIds = new HashSet<>();
                        List<DetailSchedule> schedules = response.body().getSchedules();
                        schedules.removeIf(detailSchedule -> !uniqueIds.add(detailSchedule.getDoctor().getDoctorInformation().getId()));
                        ScheduleAdapter adapter = new ScheduleAdapter(SelectDoctorActivity.this, schedules);
                        binding.rvDoctor.setAdapter(adapter);
                        binding.rvDoctor.setVisibility(View.VISIBLE);
                        binding.pbLoading.setVisibility(View.GONE);
                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ScheduleResponse> call, @NonNull Throwable t) {
                    displayError();
                }
            });
        }
    }

    private void displayError() {
        binding.pbLoading.setVisibility(View.GONE);
        binding.llError.setVisibility(View.VISIBLE);
    }

    private void eventHandling() {
        binding.ivBack.setOnClickListener(v -> onBackPressed());
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
    public void onClick(Doctor doctor) {
        if (!Objects.equals(doctor.getScheduleString(), "Không có lịch khám")) {
            HealthFacility selectedHealthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
            Intent intent = new Intent(this, SelectDateActivity.class);
            intent.putExtra(Constants.KEY_DOCTOR, doctor);
            intent.putExtra(Constants.KEY_DEPARTMENT, selectedDepartment);
            intent.putExtra(Constants.KEY_HEALTH_FACILITY, selectedHealthFacility);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.this_doctor_dont_have_schedule), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DetailSchedule detailSchedule) {
        Intent intent = new Intent(this, SelectDateActivity.class);
        intent.putExtra(Constants.KEY_DETAIL_DOCTOR, detailSchedule.getDoctor());
        intent.putExtra(Constants.KEY_DEPARTMENT, detailSchedule.getDoctor().getDepartmentInformation());
        intent.putExtra(Constants.KEY_HEALTH_FACILITY, detailSchedule.getDoctor().getHealthFacility());
        startActivity(intent);
    }
}