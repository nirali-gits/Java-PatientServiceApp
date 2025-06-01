package com.example.patient_service.controller;

import com.example.patient_service.model.Patient;
import com.example.patient_service.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean; // ✅ safe and supported
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @SuppressWarnings("removal")
    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPatients() throws Exception {
        Patient patient = new Patient("1", "John", "Doe", LocalDate.of(1990, 1, 1), "123", "john@example.com", "Male");
        when(patientService.getAllPatients()).thenReturn(List.of(patient));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient("1", "John", "Doe", LocalDate.of(1990, 1, 1), "123", "john@example.com", "Male");
        when(patientService.getPatientById("1")).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailAddress").value("john@example.com"));
    }

    @Test
    public void testAddPatient() throws Exception {
        Patient patient = new Patient(null, "Jane", "Smith", LocalDate.of(1985, 5, 20), "456", "jane@example.com", "Female");
        Patient saved = new Patient("2", "Jane", "Smith", LocalDate.of(1985, 5, 20), "456", "jane@example.com", "Female");

        when(patientService.addPatient(any(Patient.class))).thenReturn(saved);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"));
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }
}
