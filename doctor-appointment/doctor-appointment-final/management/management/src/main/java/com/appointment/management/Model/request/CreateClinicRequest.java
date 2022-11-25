package com.appointment.management.Model.request;

import com.appointment.management.enums.CountryCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClinicRequest {
    private String clinicName;
    private String clinicCity;
    private String doctorName;
    private CountryCodeEnum clinicCountryCode;
    private String consultationFee;

//    private String createdPerson;
//    private LocalDateTime createdDateTime;
//    private String lastUpdatedPerson;
//    private LocalDateTime lastUpdatedDateTime;
}
