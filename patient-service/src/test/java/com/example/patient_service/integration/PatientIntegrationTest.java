package com.example.patient_service.integration;

import com.example.patient_service.model.Patient;
import com.example.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void setup() {
        patientRepository.deleteAll();
    }

    @Test
    public void testAddAndGetPatient() {
        Patient patient = Patient.builder()
                .firstName("Test")
                .lastName("User")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .contactNumber("1234567890")
                .emailAddress("test.user@example.com")
                .gender("Other")
                .build();

        ResponseEntity<Patient> postResponse = restTemplate.postForEntity("/api/patients", patient, Patient.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String id = postResponse.getBody().getId();

        ResponseEntity<Patient> getResponse = restTemplate.getForEntity("/api/patients/" + id, Patient.class);
        assertThat(getResponse.getBody().getEmailAddress()).isEqualTo("test.user@example.com");
    }
}
