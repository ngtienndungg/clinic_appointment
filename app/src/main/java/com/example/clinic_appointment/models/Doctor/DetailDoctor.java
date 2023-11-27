package com.example.clinic_appointment.models.Doctor;

import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.HealthFacility.HealthFacility;
import com.example.clinic_appointment.models.User.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailDoctor implements Serializable {
    @SerializedName("_id")
    private User doctorInformation;

    @SerializedName("specialtyID")
    private Department departmentInformation;
    @SerializedName("clinicID")
    private HealthFacility healthFacility;

    @SerializedName("description")
    private String description;
    @SerializedName("position")
    private String academicLevel;

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public User getDoctorInformation() {
        return doctorInformation;
    }

    public void setDoctorInformation(User doctorInformation) {
        this.doctorInformation = doctorInformation;
    }

    public Department getDepartmentInformation() {
        return departmentInformation;
    }

    public void setDepartmentInformation(Department departmentInformation) {
        this.departmentInformation = departmentInformation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HealthFacility getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(HealthFacility healthFacility) {
        this.healthFacility = healthFacility;
    }
}
