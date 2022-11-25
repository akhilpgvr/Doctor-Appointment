package com.appointment.management.Model.entity;

import com.appointment.management.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "appointment")
public class DoctorAppointmentEntity {
    @Id
    private String patientId;
    private String patientNationalId;
    private String patientName;
    private int patientAge;
    private GenderEnum patientGender;
    private String doctorName;
    private String clinicName;
    private String clinicCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String consultationFee;

    private String patientUuid;

    private String createdPerson;
    private LocalDateTime createdDateTime;
    private String lastUpdatedPerson;
    private LocalDateTime lastUpdatedDateTime;




}
