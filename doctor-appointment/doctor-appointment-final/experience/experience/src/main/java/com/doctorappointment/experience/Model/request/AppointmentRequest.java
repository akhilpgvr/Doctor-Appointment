package com.doctorappointment.experience.Model.request;

import com.doctorappointment.experience.enums.GenderEnum;
import com.doctorappointment.experience.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    @Pattern(regexp = "(^[F])+([R])+(\\d{10})", message = "national id should start with FR and max 12 alpha numeric ")
    private String patientNationalId;
    @Size(min = 5 ,max = 16,message = "Enter Name with size between 5 & 30")
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
