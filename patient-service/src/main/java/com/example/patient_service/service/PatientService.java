package com.example.patient_service.service;

import com.example.patient_service.model.Patient;
import com.example.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> updatePatient(String id, Patient updated) {
        return patientRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setDateOfBirth(updated.getDateOfBirth());
            existing.setContactNumber(updated.getContactNumber());
            existing.setEmailAddress(updated.getEmailAddress());
            existing.setGender(updated.getGender());
            return patientRepository.save(existing);
        });
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }
}
