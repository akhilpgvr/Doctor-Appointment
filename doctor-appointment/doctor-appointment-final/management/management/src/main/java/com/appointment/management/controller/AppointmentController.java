package com.appointment.management.controller;

import com.appointment.management.Model.request.AppointmentRequest;
import com.appointment.management.Model.request.CreateClinicRequest;
import com.appointment.management.Model.response.ResponseModel;
import com.appointment.management.enums.CountryCodeEnum;
import com.appointment.management.enums.TimeEnum;
import com.appointment.management.service.AppointmentService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @Autowired
    AppointmentService appointmentService;

    @ApiOperation(value = "doctor appointment analysis")
    @GetMapping("/analysis")
    public ResponseEntity<ResponseModel> analyseAppointment(@RequestHeader String patientUuid, @RequestParam String doctorName, @RequestParam String clinicName, @RequestParam String clinicCity, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate, @RequestParam TimeEnum appointmentTime) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(appointmentService.analyseDoctorAppointment(patientUuid, doctorName, clinicName, clinicCity, appointmentDate, appointmentTime)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "create doctor appointment")
    @PostMapping("/create-appointment")
    public ResponseEntity<ResponseModel> makeAppointment(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestBody AppointmentRequest appointmentRequest) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(appointmentService.createDoctorAppointment(patientCountry, patientUuid, appointmentRequest)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update doctor appointment")
    @PutMapping("/update-appointment")
    public ResponseEntity<ResponseModel> updateAppointment(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestParam String patientId, @RequestBody AppointmentRequest appointmentRequest) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(appointmentService.updateDoctorAppointment(patientCountry, patientUuid, patientId, appointmentRequest)), null, "200"),HttpStatus.OK);
        } catch (
                Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()),"400"),HttpStatus.BAD_REQUEST);

        }

    }

    @ApiOperation(value = "get patient appointment")
    @GetMapping("/get-appointment")
    public ResponseEntity<ResponseModel> getDoctorAppointment(@RequestHeader String patientUuid, @RequestParam String patientId) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.of(appointmentService.getDoctorAppointment(patientUuid, patientId)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }




    @ApiOperation(value = "create new clinic")
    @PostMapping("/add-clinic")      //To add new clinical record
    public ResponseEntity<ResponseModel> createNewClinic(@RequestHeader String patientUuid, @RequestBody CreateClinicRequest createClinicRequest) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(appointmentService.createNewClinic(patientUuid, createClinicRequest)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "get all clinic details")
    @GetMapping("/get-activeRecord")  //To get all Clinical records
    public ResponseEntity<ResponseModel> getClinics(@RequestHeader("uuid") String patientUuid) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.of(appointmentService.getAllClinics(patientUuid)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Update doctor clinic details")
    @PutMapping("/update-clinic")
    public ResponseEntity<ResponseModel> updateClinic(@RequestHeader String patientUuid, @RequestParam String clinicId, @RequestBody CreateClinicRequest createClinicRequest) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.of(appointmentService.updateDoctorClinic(patientUuid, clinicId, createClinicRequest)), null, "200"),HttpStatus.OK);
        } catch (
                Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()),"400"),HttpStatus.BAD_REQUEST);

        }

    }


    @ApiOperation(value = "delete-doctor-appointment")
    @DeleteMapping("/delete-appointment")
    public ResponseEntity<ResponseModel> deleteAppointment(@RequestHeader String patientUuid, @RequestParam String patientId) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.of(appointmentService.deleteDoctorAppointment(patientUuid, patientId)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "delete-clinic")
    @DeleteMapping("/delete-clinic")
    public ResponseEntity<ResponseModel> deleteClinic(@RequestHeader String patientUuid, @RequestParam String clinicId) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.of(appointmentService.deleteDoctorClinic(patientUuid, clinicId)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }




}
