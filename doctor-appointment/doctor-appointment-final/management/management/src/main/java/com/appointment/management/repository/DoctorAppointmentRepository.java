package com.appointment.management.repository;

import com.appointment.management.Model.entity.DoctorAppointmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAppointmentRepository extends MongoRepository<DoctorAppointmentEntity, String> {

}
