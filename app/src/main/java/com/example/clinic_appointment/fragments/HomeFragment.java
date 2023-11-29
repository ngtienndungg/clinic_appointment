package com.example.clinic_appointment.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.activities.LoginActivity;
import com.example.clinic_appointment.activities.SearchScheduleActivity;
import com.example.clinic_appointment.activities.SelectHealthFacilityActivity;
import com.example.clinic_appointment.databinding.DialogNotificationBinding;
import com.example.clinic_appointment.databinding.FragmentHomeBinding;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

public class HomeFragment extends Fragment {
    private final SharedPrefs sharedPrefs = SharedPrefs.getInstance();
    private FragmentHomeBinding binding;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
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
        binding.llMakeAppointmentAtHealthFacilities.setOnClickListener(v -> handleEvent(SelectHealthFacilityActivity.class));
        binding.llSearchSchedule.setOnClickListener(v -> handleEvent(SearchScheduleActivity.class));
    }

    private void handleEvent(Class<?> activityClass) {
        if (isNetworkAvailable()) {
            startActivity(new Intent(getActivity(), activityClass));
        } else {
            showNetworkUnavailableDialog();
        }
    }

    private void showNetworkUnavailableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        DialogNotificationBinding dialogNotificationBinding = DialogNotificationBinding.inflate(getLayoutInflater());
        builder.setView(dialogNotificationBinding.getRoot());
        alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        dialogNotificationBinding.tvTitle.setText(getString(R.string.you_are_not_connected_to_internet));
        dialogNotificationBinding.tvContent.setText(getString(R.string.please_connect_to_the_internet));
        dialogNotificationBinding.tvAction.setText(getString(R.string.accept));
        dialogNotificationBinding.tvAction.setOnClickListener(v -> dismissDialog());
        dialogNotificationBinding.ivClose.setOnClickListener(v -> dismissDialog());
        alertDialog.show();
    }

    private void dismissDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}