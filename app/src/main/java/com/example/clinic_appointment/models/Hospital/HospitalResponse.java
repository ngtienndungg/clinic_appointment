package com.example.clinic_appointment.models.Hospital;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalResponse {
    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("data")
    private List<Hospital> hospitals;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospital(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

}
