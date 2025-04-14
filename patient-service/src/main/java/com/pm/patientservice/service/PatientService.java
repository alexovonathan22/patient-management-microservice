package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
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
           var checkEmail = patientRepository.existsByEmail(newPatientDTO.getEmail());
           if(checkEmail) {
               log.info("method :: Email already exists");
             throw new EmailAlreadyExistsException("A patient with this email " + newPatientDTO.getEmail() + " already exists.");
               //return null;
           }
           Patient newPatient = patientRepository.save(PatientMapper.toPatientModel(newPatientDTO));
           return PatientMapper.patientToPatientResponseDTO(newPatient);
       }catch(EmailAlreadyExistsException e) {
           log.error("method :: Email already exists");
           throw e;
       }
       catch (Exception e) {
           return new PatientResponseDTO();
       }
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
       Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient id not found " + id));
        var checkEmail = patientRepository.existsByEmail(dto.getEmail());
        if(checkEmail) {
            log.info("method :: Email already exists");
            throw new EmailAlreadyExistsException("A patient with this email " + dto.getEmail() + " already exists.");
            //return null;
        }
        // update patient object
        PatientMapper.toUpdatePatientModel(dto, patient);
        Patient updatedData = patientRepository.save(patient);
        return PatientMapper.patientToPatientResponseDTO(updatedData);
    }
}
