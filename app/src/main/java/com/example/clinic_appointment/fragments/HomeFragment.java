package com.example.clinic_appointment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.activities.SearchScheduleActivity;
import com.example.clinic_appointment.activities.SelectHealthFacilityActivity;
import com.example.clinic_appointment.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        eventHandling();
        return binding.getRoot();
    }

    private void eventHandling() {
        binding.llMakeAppointmentAtHealthFacilities.setOnClickListener(v -> startActivity(new Intent(getActivity(), SelectHealthFacilityActivity.class)));
        binding.llSearchSchedule.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchScheduleActivity.class)));
    }
}