package com.appointment.management.service;

import com.appointment.management.Model.entity.ClinicDoctorEntity;
import com.appointment.management.Model.entity.DoctorAppointmentEntity;
import com.appointment.management.Model.request.AppointmentRequest;
import com.appointment.management.Model.request.CreateClinicRequest;
import com.appointment.management.Model.response.AppointmentResponse;
import com.appointment.management.Model.response.DoctorAnalysisResponse;
import com.appointment.management.enums.CountryCodeEnum;
import com.appointment.management.enums.DoctorAvailabilityEnum;
import com.appointment.management.enums.TimeEnum;
import com.appointment.management.repository.DoctorAppointmentRepository;
import com.appointment.management.repository.ClinicDoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static com.appointment.management.constants.AppointmentTimes.APPOINTMENT_SHEDULES;

@Slf4j
@Service
public class AppointmentService {

    @Autowired
    DoctorAppointmentRepository doctorAppointmentRepository;
    @Autowired
    ClinicDoctorRepository clinicDoctorRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    ModelMapper modelMapper;

    public DoctorAnalysisResponse analyseDoctorAppointment(String patientUuid, String doctorName, String clinicName, String clinicCity, LocalDate appointmentDate, TimeEnum appointmentTime) {
        try {
            ClinicDoctorEntity clinicDoctorEntity = getClinicAndDoctorDetail(clinicName, clinicCity, doctorName);
            List<DoctorAppointmentEntity> doctorAppointmentEntities = getDoctorReservation(doctorName, clinicName, clinicCity, appointmentDate);
            List<String> availableAppointmentTimes = new ArrayList<>();
            List<String> bookedAppointmentTimes = new ArrayList<>();
            for (DoctorAppointmentEntity doctorAppointmentEntity : doctorAppointmentEntities) {
                bookedAppointmentTimes.add(doctorAppointmentEntity.getAppointmentTime());
            }
            for (String availableTime : APPOINTMENT_SHEDULES) {
                if (!(bookedAppointmentTimes.contains(availableTime))) {
                    availableAppointmentTimes.add(availableTime);
                }
            }
            log.info("Appointment analysis for {} is Done",patientUuid);
            if (availableAppointmentTimes.contains(appointmentTime.getPatientAppointmentTime())) {
                return new DoctorAnalysisResponse(clinicName, clinicCity, doctorName, clinicDoctorEntity.getConsultationFee(), availableAppointmentTimes, bookedAppointmentTimes, DoctorAvailabilityEnum.AVAILABLE);
            } else {
                return new DoctorAnalysisResponse(clinicName, clinicCity, doctorName, clinicDoctorEntity.getConsultationFee(), availableAppointmentTimes, bookedAppointmentTimes, DoctorAvailabilityEnum.NOTAVAILABLE);
            }


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage()); //Doctor clinic not found Exception
        }
    }

    public Object createDoctorAppointment(CountryCodeEnum patientCountry, String patientUuid,AppointmentRequest appointmentRequest) {
        try {
            DoctorAnalysisResponse doctorAnalysisResponse = analyseDoctorAppointment(patientUuid, appointmentRequest.getDoctorName(), appointmentRequest.getClinicName(), appointmentRequest.getClinicCity(), appointmentRequest.getAppointmentDate(), appointmentRequest.getAppointmentTime());
            if (doctorAnalysisResponse.getDoctorAvailableStatus() == DoctorAvailabilityEnum.AVAILABLE) {
                DoctorAppointmentEntity doctorAppointmentEntity = modelMapper.map(appointmentRequest, DoctorAppointmentEntity.class);
                doctorAppointmentEntity.setAppointmentTime(appointmentRequest.getAppointmentTime().getPatientAppointmentTime());
                doctorAppointmentEntity.setConsultationFee(doctorAnalysisResponse.getConsultationFee());
                doctorAppointmentEntity.setCreatedPerson(appointmentRequest.getPatientName());
                doctorAppointmentEntity.setCreatedDateTime(LocalDateTime.now());
                doctorAppointmentEntity.setLastUpdatedPerson(appointmentRequest.getDoctorName());
                doctorAppointmentEntity.setLastUpdatedDateTime(LocalDateTime.now());
                doctorAppointmentEntity.setPatientUuid(patientUuid);
                doctorAppointmentEntity.setPatientId(null);
                log.info("Appointment Reserved for {}",patientUuid);
                doctorAppointmentRepository.save(doctorAppointmentEntity);
                return new AppointmentResponse(doctorAppointmentEntity.getPatientId(), doctorAppointmentEntity.getPatientName(), doctorAppointmentEntity.getDoctorName(), doctorAppointmentEntity.getClinicName(), doctorAppointmentEntity.getClinicCity(), doctorAppointmentEntity.getAppointmentDate(), doctorAppointmentEntity.getAppointmentTime(), doctorAppointmentEntity.getConsultationFee());
            } else {
                return doctorAnalysisResponse;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object updateDoctorAppointment(CountryCodeEnum patientCountry, String patientUuid, String patientId, AppointmentRequest appointmentRequest) {
        try {
            DoctorAppointmentEntity doctorAppointmentEntity = doctorAppointmentRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Given patientId doesn't exists"));
            DoctorAppointmentEntity newDoctorAppointmentEntity = new DoctorAppointmentEntity();
            String updatePatientName = StringUtils.isNotBlank(appointmentRequest.getPatientName()) ? (appointmentRequest.getPatientName()) : doctorAppointmentEntity.getPatientName();
            newDoctorAppointmentEntity.setPatientName(updatePatientName);
            int updatePatientAge = StringUtils.isNotBlank(String.valueOf(appointmentRequest.getPatientAge())) ? (appointmentRequest.getPatientAge()) : doctorAppointmentEntity.getPatientAge();
            newDoctorAppointmentEntity.setPatientAge(updatePatientAge);
            String updateDoctorName = StringUtils.isNotBlank(appointmentRequest.getDoctorName()) ? (appointmentRequest.getDoctorName()) : doctorAppointmentEntity.getDoctorName();
            newDoctorAppointmentEntity.setDoctorName(updateDoctorName);
            String updateClinicName = StringUtils.isNotBlank(appointmentRequest.getClinicName()) ? (appointmentRequest.getClinicName()) : doctorAppointmentEntity.getClinicName();
            newDoctorAppointmentEntity.setClinicName(updateClinicName);
            String updateClinicCity = StringUtils.isNotBlank(appointmentRequest.getClinicCity()) ? (appointmentRequest.getClinicCity()) : doctorAppointmentEntity.getClinicCity();
            newDoctorAppointmentEntity.setClinicCity(updateClinicCity);

            newDoctorAppointmentEntity.setPatientNationalId(appointmentRequest.getPatientNationalId());
            newDoctorAppointmentEntity.setAppointmentDate(appointmentRequest.getAppointmentDate());
            newDoctorAppointmentEntity.setPatientGender(appointmentRequest.getPatientGender());
            newDoctorAppointmentEntity.setAppointmentTime(appointmentRequest.getAppointmentTime().getPatientAppointmentTime());

            DoctorAnalysisResponse doctorAnalysisResponse = analyseDoctorAppointment(patientUuid, newDoctorAppointmentEntity.getDoctorName(), newDoctorAppointmentEntity.getClinicName(), newDoctorAppointmentEntity.getClinicCity(), newDoctorAppointmentEntity.getAppointmentDate(), appointmentRequest.getAppointmentTime());
            if (doctorAnalysisResponse.getDoctorAvailableStatus() == DoctorAvailabilityEnum.AVAILABLE || ((updateDoctorName.equals(doctorAppointmentEntity.getDoctorName())) && (updateClinicName.equals(doctorAppointmentEntity.getClinicName())) && (updateClinicCity.equals(doctorAppointmentEntity.getClinicCity())) && ((appointmentRequest.getAppointmentDate()).equals(doctorAppointmentEntity.getAppointmentDate())) && appointmentRequest.getAppointmentTime().getPatientAppointmentTime().equals(doctorAppointmentEntity.getAppointmentTime()))) {

                newDoctorAppointmentEntity.setPatientId(appointmentRequest.getPatientNationalId());
                newDoctorAppointmentEntity.setAppointmentTime(newDoctorAppointmentEntity.getAppointmentTime());
                newDoctorAppointmentEntity.setConsultationFee(doctorAnalysisResponse.getConsultationFee());
                newDoctorAppointmentEntity.setCreatedDateTime(LocalDateTime.now());
                newDoctorAppointmentEntity.setLastUpdatedPerson(appointmentRequest.getDoctorName());
                newDoctorAppointmentEntity.setLastUpdatedDateTime(LocalDateTime.now());
                newDoctorAppointmentEntity.setPatientId(patientId);
                newDoctorAppointmentEntity.setPatientUuid(patientUuid);
                log.info("Appointment Updated for {}",patientUuid);
                doctorAppointmentRepository.save(newDoctorAppointmentEntity);
                return new AppointmentResponse(newDoctorAppointmentEntity.getPatientId(), newDoctorAppointmentEntity.getPatientName(), newDoctorAppointmentEntity.getDoctorName(), newDoctorAppointmentEntity.getClinicName(), newDoctorAppointmentEntity.getClinicCity(), newDoctorAppointmentEntity.getAppointmentDate(), newDoctorAppointmentEntity.getAppointmentTime(), newDoctorAppointmentEntity.getConsultationFee());
            } else {
                return doctorAnalysisResponse;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public DoctorAppointmentEntity getDoctorAppointment(String patientUuid, String patientId) {
        try{
            log.info("Appointment Retrieval Request passed for {}",patientUuid);
            return doctorAppointmentRepository.findById(patientId).orElseThrow(()-> new RuntimeException("No patient exists"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public String createNewClinic(String adminUuid, CreateClinicRequest createClinicRequest) {
        try {
            ClinicDoctorEntity clinicDoctorEntity = modelMapper.map(createClinicRequest, ClinicDoctorEntity.class);
            clinicDoctorEntity.addCountryCurrencyCode();
            clinicDoctorEntity.setCreatedPerson("admin");
            clinicDoctorEntity.setCreatedDateTime(LocalDateTime.now());
            clinicDoctorEntity.setLastUpdatedPerson("admin");
            clinicDoctorEntity.setLastUpdatedDateTime(LocalDateTime.now());
            log.info("New Clinic record Added By {}",adminUuid);
            clinicDoctorRepository.save(clinicDoctorEntity);
            return "Clinic Successfully Created";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public List<ClinicDoctorEntity> getAllClinics(String patientUuid) {
        try {
            return clinicDoctorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public String updateDoctorClinic(String adiminUuid, String clinicId, CreateClinicRequest createClinicRequest) {
        try {
            ClinicDoctorEntity ClinicDoctorEntity = clinicDoctorRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("ClinicId doesn't exists"));
            ClinicDoctorEntity newClinicDoctorEntity = new ClinicDoctorEntity();
            String updateClinicName = StringUtils.isNotBlank(createClinicRequest.getClinicName()) ? (createClinicRequest.getClinicName()) : ClinicDoctorEntity.getClinicName();
            newClinicDoctorEntity.setClinicName(updateClinicName);
            String updateClinicCity = StringUtils.isNotBlank(createClinicRequest.getClinicCity()) ? (createClinicRequest.getClinicCity()) : ClinicDoctorEntity.getClinicCity();
            newClinicDoctorEntity.setClinicCity(updateClinicCity);
            String updateDoctorName = StringUtils.isNotBlank(createClinicRequest.getDoctorName()) ? (createClinicRequest.getDoctorName()) : ClinicDoctorEntity.getDoctorName();
            newClinicDoctorEntity.setDoctorName(updateDoctorName);
            newClinicDoctorEntity.setClinicCountryCode(createClinicRequest.getClinicCountryCode());
            String updateConsultationFee = StringUtils.isNotBlank(createClinicRequest.getConsultationFee()) ? (createClinicRequest.getConsultationFee()) : ClinicDoctorEntity.getConsultationFee();
            newClinicDoctorEntity.setConsultationFee(updateConsultationFee);
            newClinicDoctorEntity.addCountryCurrencyCode();

            newClinicDoctorEntity.setClinicId(clinicId);
            newClinicDoctorEntity.setLastUpdatedPerson("admin");
            newClinicDoctorEntity.setLastUpdatedDateTime(LocalDateTime.now());
            log.info("Clinic Record Updated by {}",adiminUuid);
            clinicDoctorRepository.save(newClinicDoctorEntity);
            return "Clinic Details updated Successfully";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String deleteDoctorAppointment(String adminUuid, String patientId) {
        try{
            doctorAppointmentRepository.deleteById(patientId);
            log.info("Appointment Delete Request Success for {}",adminUuid);
            return "Appointment with "+patientId+" deleted Sucessfully";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public String deleteDoctorClinic(String adminUuid, String clinicId) {
        try{
            clinicDoctorRepository.deleteById(clinicId);
            log.info("Clinic Record Delete Request Success for {}",adminUuid);
            return "Clinic with "+clinicId+" deleted Sucessfully";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    //Supporting Functions

    public ClinicDoctorEntity getClinicAndDoctorDetail(String clinicName, String clinicCity, String doctorName) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("clinicName").is(clinicName));
            query.addCriteria(Criteria.where("doctorName").is(doctorName));
            query.addCriteria(Criteria.where("clinicCity").is(clinicCity));
            return mongoTemplate.findOne(query, ClinicDoctorEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<DoctorAppointmentEntity> getDoctorReservation(String doctorName, String clinicName, String clinicCity, LocalDate appointmentDate) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("doctorName").is(doctorName));
            query.addCriteria(Criteria.where("clinicName").is(clinicName));
            query.addCriteria(Criteria.where("clinicCity").is(clinicCity));
            query.addCriteria(Criteria.where("appointmentDate").is(appointmentDate));
            return mongoTemplate.find(query, DoctorAppointmentEntity.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}