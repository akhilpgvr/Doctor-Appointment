package com.doctorappointment.experience.service;

import com.doctorappointment.experience.Model.request.AppointmentRequest;
import com.doctorappointment.experience.Model.response.ResponseModel;
import com.doctorappointment.experience.enums.CountryCodeEnum;
import com.doctorappointment.experience.enums.TimeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class AppointmentService {
    @Autowired
    AppointmentInterface appointmentInterface;

    public ResponseModel analyseDoctorAppointment(String patientUuid, String doctorName, String clinicName, String clinicCity, LocalDate appointmentDate, TimeEnum appointmentTime) {
        try {
            log.info("Appointment Analysis Request passed for {}", patientUuid);
            return appointmentInterface.analyseDoctorAppointmentConnect(patientUuid, doctorName, clinicName, clinicCity, appointmentDate, appointmentTime);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); //Doctor clinic not found Exception
        }
    }

    public ResponseModel createDoctorAppointment(CountryCodeEnum patientCountry, String patientUuid, AppointmentRequest appointmentRequest) {
        try {
            log.info("Appointment Reservation request has passed for {}",patientUuid);
            return appointmentInterface.createDoctorAppointmentConnect(patientCountry, patientUuid, appointmentRequest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseModel updateDoctorAppointment(CountryCodeEnum patientCountry, String patientUuid, String patientId, AppointmentRequest appointmentRequest) {
        try {
            log.info("Appointment Update Request has passed for {}",patientUuid);
            return appointmentInterface.updateDoctorAppointmentConnect(patientCountry, patientUuid, patientId, appointmentRequest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public ResponseModel getAllClinics(String patientUuid) {
        try {
            log.info("Clinic list Retrieve Request has passed for {}",patientUuid);
            return appointmentInterface.getAllClinicsConnect(patientUuid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    //Supporting Functions

}




