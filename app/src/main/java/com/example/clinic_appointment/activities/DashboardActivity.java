package com.example.clinic_appointment.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.clinic_appointment.R;
import com.example.clinic_appointment.databinding.ActivityDashboardBinding;
import com.example.clinic_appointment.fragments.AccountFragment;
import com.example.clinic_appointment.fragments.HomeFragment;
import com.example.clinic_appointment.fragments.MyScheduleFragment;
import com.example.clinic_appointment.fragments.ScheduleFragment;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TokenTest", SharedPrefs.getInstance().getData(Constants.KEY_ACCESS_TOKEN, String.class));
        Log.d("TokenTest", SharedPrefs.getInstance().getData(Constants.KEY_REFRESH_TOKEN, String.class));
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
        eventHandling();
    }

    private void initiate() {
        currentFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
    }

    @SuppressLint("NonConstantResourceId")
    private void eventHandling() {
        binding.bnvDashboard.setOnItemSelectedListener(item -> {
            Fragment newFragment = null;
            switch (item.getItemId()) {
                case R.id.menu_item_home:
                    newFragment = new HomeFragment();
                    break;
                case R.id.menu_item_schedule:
                    newFragment = new ScheduleFragment();
                    break;
                case R.id.menu_item_my_schedule:
                    newFragment = new MyScheduleFragment();
                    break;
                case R.id.menu_item_account:
                    newFragment = new AccountFragment();
                    break;
            }
            if (newFragment != null && !(currentFragment.getClass().equals(newFragment.getClass()))) {
                replaceFragment(newFragment);
                return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();
        currentFragment = newFragment;
    }

    @Override
    public void onBackPressed() {
        if (!(currentFragment instanceof HomeFragment)) {
            binding.bnvDashboard.setSelectedItemId(R.id.menu_item_home);
            currentFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

}