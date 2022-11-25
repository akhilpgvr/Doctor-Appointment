package com.doctorappointment.experience.Model.request;

import com.doctorappointment.experience.enums.TimeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicAnalysisRequest {

    private String doctorName;
    private String clinicId;
    private String clinicName;
    private String clinicCity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate appointmentDate;
    private TimeEnum appointmentTime;

}

