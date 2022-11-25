package com.doctorappointment.experience.Model.request;

import com.doctorappointment.experience.enums.CountryCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
