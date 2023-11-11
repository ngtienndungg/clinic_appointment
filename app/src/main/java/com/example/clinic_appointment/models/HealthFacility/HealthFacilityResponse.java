package com.example.clinic_appointment.models.HealthFacility;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthFacilityResponse {
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("data")
    private List<HealthFacility> healthFacilities;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<HealthFacility> getAllHealthFacilities() {
        return healthFacilities;
    }

    public void setHealthFacilities(List<HealthFacility> healthFacilities) {
        this.healthFacilities = healthFacilities;
    }

}
