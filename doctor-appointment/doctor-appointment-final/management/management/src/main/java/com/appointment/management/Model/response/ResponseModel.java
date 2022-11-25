package com.appointment.management.Model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    Optional<Object> data;
    Optional<String> error;
    String status;
}
