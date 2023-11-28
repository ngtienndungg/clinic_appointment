package com.example.clinic_appointment.models.Doctor;

import android.util.Log;

import com.example.clinic_appointment.models.Department.Department;
import com.example.clinic_appointment.models.User.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {
    @SerializedName("_id")
    private User doctorInformation;

    @SerializedName("specialtyID")
    private Department departmentInformation;
    @SerializedName("clinicID")
    private String clinicID;

    @SerializedName("description")
    private String description;
    @SerializedName("position")
    private String academicLevel;
    @SerializedName("schedules")
    private int[] doctorSchedules;

    public String getScheduleString() {
        if (doctorSchedules == null || doctorSchedules.length == 0) {
            return "Không có lịch khám";
        } else {
            StringBuilder resultStringBuilder = new StringBuilder();
            for (int dayOfWeek : doctorSchedules) {
                switch (dayOfWeek) {
                    case 1:
                        resultStringBuilder.append(", hai");
                        break;
                    case 2:
                        resultStringBuilder.append(", ba");
                        break;
                    case 3:
                        resultStringBuilder.append(", tư");
                        break;
                    case 4:
                        resultStringBuilder.append(", năm");
                        break;
                    case 5:
                        resultStringBuilder.append(", sáu");
                        break;
                    case 6:
                        resultStringBuilder.append(", bảy");
                        break;
                    case 0:
                        resultStringBuilder.append(", chủ nhật");
                        break;
                }
            }
            String resultString = String.valueOf(resultStringBuilder.delete(0, 2));
            Log.d("TestString", resultString);
            if (resultString.charAt(0) == 'c') {
                return resultString.substring(0, 1).toUpperCase() + resultString.substring(1);
            } else {
                return "Thứ " + resultString.substring(0, 1).toUpperCase() + resultString.substring(1);
            }
        }
    }

    public static Doctor getDoctor(DetailDoctor detailDoctor) {
        Doctor doctor = new Doctor();
        doctor.setDoctorInformation(detailDoctor.getDoctorInformation());
        doctor.setClinicID(detailDoctor.getHealthFacility().getId());
        doctor.setAcademicLevel(detailDoctor.getAcademicLevel());
        doctor.setDescription(detailDoctor.getDescription());
        return doctor;
    }


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

    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }
}
