package com.appointment.management.Model.request;

import com.appointment.management.enums.GenderEnum;
import com.appointment.management.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    private String patientNationalId;
    private String patientName;
    private int patientAge;
    private GenderEnum patientGender;
    private String doctorName;
    private String clinicName;
    private String clinicCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;
    private TimeEnum appointmentTime;

}
