package com.example.clinic_appointment.models.Doctor;

import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.User.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {
    @SerializedName("_id")
    private User doctorInformation;

    @SerializedName("specialtyID")
    private Department departmentInformation;

    @SerializedName("description")
    private String description;

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
}
