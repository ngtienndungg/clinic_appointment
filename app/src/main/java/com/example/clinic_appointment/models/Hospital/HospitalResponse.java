package com.example.clinic_appointment.models.Hospital;

import com.google.gson.annotations.SerializedName;

public class HospitalResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Hospital hospital;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
