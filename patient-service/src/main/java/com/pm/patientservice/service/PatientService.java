package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        try{
            List<Patient> patients = patientRepository.findAll();
            var list = patients.stream()
                    .map(patient -> PatientMapper.patientToPatientResponseDTO(patient)).toList();
            return list;
        } catch (Exception e) {
           return Collections.emptyList();
        }

    }
    public PatientResponseDTO createPatient(PatientRequestDTO newPatientDTO) {
       try{
           Patient newPatient = patientRepository.save(PatientMapper.toPatientModel(newPatientDTO));
           return PatientMapper.patientToPatientResponseDTO(newPatient);
       }catch (Exception e) {
           return new PatientResponseDTO();
       }
    }
}
