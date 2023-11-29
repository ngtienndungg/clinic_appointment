package com.example.clinic_appointment.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.activities.LoginActivity;
import com.example.clinic_appointment.activities.SearchScheduleActivity;
import com.example.clinic_appointment.activities.SelectHealthFacilityActivity;
import com.example.clinic_appointment.databinding.FragmentHomeBinding;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

public class HomeFragment extends Fragment {
    private final SharedPrefs sharedPrefs = SharedPrefs.getInstance();
    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Integer resultCode = result.getResultCode();
                if (resultCode == Activity.RESULT_OK) {
                    initiate();
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        initiate();
        eventHandling();
        return binding.getRoot();
    }

    private void initiate() {
        if (!sharedPrefs.getData(Constants.KEY_CURRENT_NAME, String.class).equals("")) {
            binding.tvName.setText(sharedPrefs.getData(Constants.KEY_CURRENT_NAME, String.class));
        } else {
            binding.tvName.setText(getString(R.string.click_here_to_login));
            binding.tvName.setOnClickListener(v -> mStartForResult.launch(new Intent(requireActivity(), LoginActivity.class)));
        }
    }

    private void eventHandling() {
        binding.llMakeAppointmentAtHealthFacilities.setOnClickListener(v -> startActivity(new Intent(getActivity(), SelectHealthFacilityActivity.class)));
        binding.llSearchSchedule.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchScheduleActivity.class)));

    }


}