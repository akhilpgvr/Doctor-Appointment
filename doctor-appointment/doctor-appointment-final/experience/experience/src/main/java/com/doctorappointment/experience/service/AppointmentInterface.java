package com.doctorappointment.experience.service;


import com.doctorappointment.experience.Model.request.AppointmentRequest;
import com.doctorappointment.experience.Model.response.ResponseModel;
import com.doctorappointment.experience.enums.CountryCodeEnum;
import com.doctorappointment.experience.enums.TimeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "employee-service", url = "${config.rest.service.addAppointmentUrl}")
public interface AppointmentInterface {

    @GetMapping("/analysis")
    ResponseModel analyseDoctorAppointmentConnect(@RequestHeader String patientUuid, @RequestParam String doctorName, @RequestParam String clinicName, @RequestParam String clinicCity, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate, @RequestParam TimeEnum appointmentTime);

    @PostMapping("/create-appointment")
    ResponseModel createDoctorAppointmentConnect(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestBody AppointmentRequest appointmentRequest);

    @PutMapping("/update-appointment")
    ResponseModel updateDoctorAppointmentConnect(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestParam String patientId, @RequestBody AppointmentRequest appointmentRequest);

    @GetMapping("/get-activeRecord")
    ResponseModel getAllClinicsConnect(@RequestHeader("uuid") String patientUuid);
}
