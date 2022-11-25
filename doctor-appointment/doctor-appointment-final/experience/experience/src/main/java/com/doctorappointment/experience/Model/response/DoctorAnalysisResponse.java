package com.doctorappointment.experience.Model.response;

import com.doctorappointment.experience.enums.DoctorAvailabilityEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAnalysisResponse {

    private String clinicName;
    private String clinicCity;
    private String doctorName;
    private String consultationFee;

    private List<String> availableTimes;
    private List<String> bookedTimes;

    private DoctorAvailabilityEnum doctorAvailableStatus;
}
