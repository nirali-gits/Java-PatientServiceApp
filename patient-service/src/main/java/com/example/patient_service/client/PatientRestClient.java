package com.example.patient_service.client;

import com.example.patient_service.model.Patient;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

public class PatientRestClient {

    private static final String BASE_URL = "http://localhost:8080/api/patients";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        // Create a new patient
        Patient newPatient = Patient.builder()
                .firstName("Alice")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1985, 5, 20))
                .contactNumber("9876543210")
                .emailAddress("alice.smith@example.com")
                .gender("Female")
                .build();

        // Add patient (POST)
        ResponseEntity<Patient> response = restTemplate.postForEntity(BASE_URL, newPatient, Patient.class);
        Patient createdPatient = response.getBody();
        System.out.println("Created: " + createdPatient);

        String patientId = createdPatient.getId();

        // Get all patients (GET)
        ResponseEntity<Patient[]> allPatients = restTemplate.getForEntity(BASE_URL, Patient[].class);
        System.out.println("All patients: " + Arrays.toString(allPatients.getBody()));

        // Get single patient by ID (GET)
        Patient single = restTemplate.getForObject(BASE_URL + "/" + patientId, Patient.class);
        System.out.println("Fetched by ID: " + single);

        // Update patient (PUT)
        createdPatient.setContactNumber("1112223333");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Patient> updateRequest = new HttpEntity<>(createdPatient, headers);
        restTemplate.exchange(BASE_URL + "/" + patientId, HttpMethod.PUT, updateRequest, Patient.class);
        System.out.println("Updated patient.");

        // Delete patient (DELETE)
        restTemplate.delete(BASE_URL + "/" + patientId);
        System.out.println("Deleted patient.");
    }
}
