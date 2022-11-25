package com.appointment.management.Model.entity;

import com.appointment.management.enums.CountryCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "clinic-doctor")
public class ClinicDoctorEntity {
    @Id
    private String clinicId;
    private String clinicName;
    private String clinicCity;
    private String doctorName;
    private CountryCodeEnum clinicCountryCode;
    private String consultationFee;

    private String createdPerson;
    private LocalDateTime createdDateTime;
    private String lastUpdatedPerson;
    private LocalDateTime lastUpdatedDateTime;

    public void addCountryCurrencyCode(){
        switch (clinicCountryCode.name()) {
            case "JO" -> this.consultationFee += " JOD";
            case "UA" -> this.consultationFee += " UA";
            case "AE" -> this.consultationFee += " AE";
            case "EG" -> this.consultationFee += " ED";
            default -> {
            }
        }
    }

}
