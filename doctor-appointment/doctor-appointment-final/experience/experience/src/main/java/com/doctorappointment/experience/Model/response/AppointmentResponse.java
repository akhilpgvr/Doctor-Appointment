package com.doctorappointment.experience.Model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private String patientId;
    private String patientName;
    private String doctorName;
    private String clinicName;
    private String clinicCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;
    private String appointmentTime;
    private String consultationFee;

}
