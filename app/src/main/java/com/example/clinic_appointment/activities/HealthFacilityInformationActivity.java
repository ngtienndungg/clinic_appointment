package com.example.clinic_appointment.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.clinic_appointment.adapters.RatingAdapter;
import com.example.clinic_appointment.databinding.ActivityHealthFacilityInformationBinding;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilityResponse;
import com.example.clinic_appointment.models.Rating.Rating;
import com.example.clinic_appointment.networking.clients.RetrofitClient;
import com.example.clinic_appointment.utilities.Constants;
import com.example.clinic_appointment.utilities.SharedPrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthFacilityInformationActivity extends AppCompatActivity {
    private ActivityHealthFacilityInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthFacilityInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initiate();
    }

    private void initiate() {
        HealthFacility healthFacility = (HealthFacility) getIntent().getSerializableExtra(Constants.KEY_HEALTH_FACILITY);
        if (healthFacility != null) {
            binding.tvName.setText(healthFacility.getName());
            binding.tvDescription.setText(healthFacility.getDescription());
            binding.tvAddress.setText(healthFacility.getAddressString());
            Glide.with(this).load(healthFacility.getImage()).centerCrop().into(binding.ivLogo);
            Call<HealthFacilityResponse> call = RetrofitClient.getPublicAppointmentService().getHealthFacilityById(healthFacility.getId());
            call.enqueue(new Callback<HealthFacilityResponse>() {
                @Override
                public void onResponse(@NonNull Call<HealthFacilityResponse> call, @NonNull Response<HealthFacilityResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getHealthFacility().getRatings().size() == 0) {
                            binding.tvNoRating.setVisibility(View.VISIBLE);
                        } else {
                            List<Rating> ratings = new ArrayList<>(response.body().getHealthFacility().getRatings());
                            for (Rating rating : ratings) {
                                if (Objects.equals(rating.getPostedBy().getId(), SharedPrefs.getInstance().getData(Constants.KEY_CURRENT_UID, String.class))) {
                                    int starNumbers = rating.getStar();
                                    ImageView[] starImageViews = {
                                            binding.ivStar5,
                                            binding.ivStar4,
                                            binding.ivStar3,
                                            binding.ivStar2,
                                            binding.ivStar1
                                    };
                                    for (int i = 0; i < starImageViews.length; i++) {
                                        starImageViews[i].setVisibility(starNumbers >= (5 - i) ? View.VISIBLE : View.GONE);
                                    }
                                    binding.tvYourComment.setText(rating.getComment());
                                    ratings.remove(rating);
                                    break;
                                }
                            }
                            RatingAdapter ratingAdapter = new RatingAdapter(ratings);
                            binding.tvRating.append("(" + response.body().getHealthFacility().getAverageRating() + ")");
                            binding.rvRatings.setAdapter(ratingAdapter);
                            binding.rvRatings.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HealthFacilityResponse> call, @NonNull Throwable t) {

                }
            });
        }
    }
}