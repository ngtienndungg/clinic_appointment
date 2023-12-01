package com.example.clinic_appointment.networking.services;

import com.example.clinic_appointment.models.Address.VietnamProvinceResponse;
import com.example.clinic_appointment.models.Appointment.AppointmentResponse;
import com.example.clinic_appointment.models.Appointment.DetailAppointment;
import com.example.clinic_appointment.models.Appointment.DetailAppointmentResponse;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppointmentService {
    @POST("user/login")
    @FormUrlEncoded
    Call<UserResponse> login(@Field("email") String email, @Field("password") String password);

    @POST("user/logout")
    Call<Void> logout(@Header("Set-Cookie") String refreshToken);

    @POST("user/refreshtoken")
    Call<JsonObject> refreshToken(@Header("Set-Cookie") String refreshToken);

    @GET("user/current")
    Call<Void> getCurrent(@Header("Authorization") String accessToken);

    @POST("user/register")
    Call<UserResponse> register(@Field("email") String email, @Field("password") String password,
                                @Field("fullName") String fullName, @Field("mobile") String phoneNumber,
                                @Field("address") String address, @Field("gender") String gender,
                                @Field("dateOfBirth") String dateOfBirth);

    @GET("clinic")
    Call<HealthFacilitiesResponse> getAllHealthFacilities();

    @GET("clinic/{id}")
    Call<HealthFacilityResponse> getHealthFacilityById(@Path("id") String clinicId);

    @GET("specialty")
    Call<DepartmentResponse> getEntireDepartment();

    @GET("specialty")
    Call<DepartmentResponse> getFilteredDepartment();

    @GET("doctor")
    Call<DoctorResponse> getDoctorByDepartmentAndHealthFacility(@Query("specialtyID") String departmentId,
                                                                @Query("clinicID") String clinicId);

    @GET("schedule")
    Call<ScheduleResponse> getSchedules(@Query("startDate") Long startDate,
                                        @Query("endDate") Long endDate,
                                        @Query("timeType.time") String time,
                                        @Query(encoded = true, value = "nameSpecialty") String departmentName,
                                        @Query(encoded = true, value = "nameClinic") String clinicName,
                                        @Query("doctorID") String doctorId);

    @POST("booking/patient")
    @FormUrlEncoded
    Call<Void> bookAppointmentByPatient(@Field("scheduleID") String scheduleID, @Field("time") String appointmentTime);

    @GET("booking/patient")
    Call<AppointmentResponse> getEntireAppointment();

    @GET("province")
    Call<VietnamProvinceResponse> getEntireProvinces();

    @GET("booking/{id}")
    Call<DetailAppointmentResponse> getAppointmentById(@Path("id") String appointmentId);

    @PUT("clinic/rating")
    @FormUrlEncoded
    Call<Void> rateClinic(@Field("star") int star, @Field("comment") String comment, @Field("clinicID") String clinicId);
}
