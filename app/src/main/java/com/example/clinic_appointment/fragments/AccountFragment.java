package com.example.clinic_appointment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.databinding.FragmentAccountBinding;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private final SharedPrefs sharedPrefs = SharedPrefs.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        initiate();
        eventHandling();
        return binding.getRoot();
    }

    private void initiate() {
        binding.tvName.setText(sharedPrefs.getData(Constants.KEY_CURRENT_NAME, String.class));
    }

    private void eventHandling() {
        binding.llLogout.setOnClickListener(v -> {

        });
    }
}