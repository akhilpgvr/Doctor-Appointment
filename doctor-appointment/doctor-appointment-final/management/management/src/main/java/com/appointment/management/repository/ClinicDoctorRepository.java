package com.appointment.management.repository;

import com.appointment.management.Model.entity.ClinicDoctorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicDoctorRepository extends MongoRepository<ClinicDoctorEntity, String> {

}
