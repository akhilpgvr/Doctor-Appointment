package com.doctorappointment.experience.controller;

import com.doctorappointment.experience.Model.request.AppointmentRequest;
import com.doctorappointment.experience.enums.CountryCodeEnum;
import com.doctorappointment.experience.enums.TimeEnum;
import com.doctorappointment.experience.response.ResponseModel;
import com.doctorappointment.experience.service.AppointmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ResponseModel> makeAppointment(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestBody @Valid AppointmentRequest appointmentRequest) {
        try {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(appointmentService.createDoctorAppointment(patientCountry, patientUuid, appointmentRequest)), null, "200"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()), "400"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update doctor appointment")
    @PutMapping("/update-appointment")
    public ResponseEntity<ResponseModel> updateAppointment(@RequestHeader CountryCodeEnum patientCountry, @RequestHeader String patientUuid, @RequestParam String patientId, @RequestBody @Valid AppointmentRequest appointmentRequest) {
        try {
            return new ResponseEntity(appointmentService.updateDoctorAppointment(patientCountry, patientUuid, patientId, appointmentRequest),HttpStatus.OK);
        } catch (
                Exception e) {
            return new ResponseEntity(new ResponseModel(Optional.ofNullable(null), Optional.ofNullable(e.getMessage()),"400"),HttpStatus.BAD_REQUEST);

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


}
