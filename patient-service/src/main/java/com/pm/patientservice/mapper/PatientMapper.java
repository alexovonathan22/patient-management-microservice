package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO patientToPatientResponseDTO(Patient patient) {
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientResponseDTO.setEmail(patient.getEmail());

        return patientResponseDTO;
    }

    public static Patient toPatientModel(PatientRequestDTO patient) {
        Patient newPat = new Patient();
        newPat.setName(patient.getName());
        newPat.setAddress(patient.getAddress());
        newPat.setEmail(patient.getEmail());
        newPat.setDateOfBirth(LocalDate.parse(patient.getDateOfBirth()));
        newPat.setRegisteredDate(LocalDate.parse(patient.getRegisteredDate()));
        return newPat;
    }
}
