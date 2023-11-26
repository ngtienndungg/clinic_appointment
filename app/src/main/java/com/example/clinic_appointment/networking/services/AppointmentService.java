package com.example.clinic_appointment.networking.services;

import com.example.clinic_appointment.models.Address.VietnamProvinceResponse;
import com.example.clinic_appointment.models.Appointment.AppointmentResponse;
import com.example.clinic_appointment.models.Department.DepartmentResponse;
import com.example.clinic_appointment.models.Doctor.DoctorResponse;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilitiesResponse;
import com.example.clinic_appointment.models.HealthFacility.HealthFacilityResponse;
import com.example.clinic_appointment.models.Schedule.ScheduleResponse;
import com.example.clinic_appointment.models.User.UserResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppointmentService {
    @POST("user/login")
    @FormUrlEncoded
    Call<UserResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("user/refreshtoken")
    Call<JsonObject> refreshToken(@Header("Set-Cookie") String refreshToken);

    @GET("user/current")
    Call<Void> getCurrent(@Header("Authorization") String accessToken);

    @POST("user/register")
    Call<UserResponse> register(@Field("email") String email, @Field("password") String password,
                                @Field("fullName") String fullName, @Field("mobile") String phoneNumber,
                                @Field("address") String address, @Field("gender") boolean gender,
                                @Field("dateOfBirth") String dateOfBirth);

    @GET("clinic")
    Call<HealthFacilitiesResponse> getAllHealthFacilities();

    @GET("clinic/{id}")
    Call<HealthFacilityResponse> getHealthFacilityById(@Path("id") String clinicId);

    @GET("specialty")
    Call<DepartmentResponse> getFilteredDepartment();

    @GET("doctor")
    Call<DoctorResponse> getDoctorByDepartmentAndHealthFacility(@Query("specialtyID") String departmentId,
                                                                @Query("clinicID") String clinicId);

    @GET("schedule")
    Call<ScheduleResponse> getSchedules(@Query("doctorID") String doctorId,
                                        @Query("startDate") long startDate,
                                        @Query("endDate") long endDate,
                                        @Query("departmentID") String departmentId,
                                        @Query("timeType.time") Integer time);

    @POST("booking/patient")
    @FormUrlEncoded
    Call<Void> bookAppointmentByPatient(@Field("scheduleID") String scheduleID, @Field("time") String appointmentTime);

    @GET("booking/patient")
    Call<AppointmentResponse> getEntireAppointment();

    @GET("province")
    Call<VietnamProvinceResponse> getEntireProvinces();
}
