package com.example.pharmacyapi.controller;

import com.example.pharmacyapi.model.Medication;
import com.example.pharmacyapi.model.Reminder;
import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;


@WebMvcTest(MedicationController.class)
public class MedicationControllerTest {

    // using Spring's @Autowired annotation to inject an instance of MockMvc into this class
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService medicationService;


    @Autowired
    // This is typically used to convert Java objects to JSON and vice versa. It's commonly used in Spring applications for JSON serialization and deserialization.
    ObjectMapper objectMapper;

    User USER_1 = new User(1L, "cigana", "pombagira@gmail.com", "quimbanda7", null);


    Medication MEDICATION_1 = new Medication(1L, "Name 1", "Description 1", "Dosage 1", true);

    Medication MEDICATION_2 = new Medication(2L, "Name 2", "Description 2", "Dosage 2", false);

    Reminder REMINDER_1 = new Reminder(1L, "Name 1", "Instructions 1", MEDICATION_1);

    Reminder REMINDER_2 = new Reminder(2L, "Name 2", "Instructions 2", MEDICATION_2);


    /**
     * This test says that when we call medicationService.getAllMedications(), then to return all medications.
     * Use mockMvc to perform a GET request to the endpoint ("/api/medications/"), set the content type you're expecting, which is MediaType.APPLICATION_JSON. Expect the response status to be ok. Expect the jsonPath of the 'data' key of the payload to have a size of 3. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if list of medications not found
     */
    @Test
    public void getAllMedicationRecords_success() throws Exception {
        List<Medication> medications = new ArrayList<>(Arrays.asList(MEDICATION_1, MEDICATION_2));

        when(medicationService.getAllMedications()).thenReturn(medications);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medications/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // jsonPath helps us check that we have a data key and a message key. '$' represents the payload
                .andExpect(jsonPath("$.data", hasSize(3))) // This represents the "data" key from Postman for this particular HTTP request
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     * This test says that when we call medicationService.getMedicationById(), then to return the medication if it exists.
     * Perform a GET request to the endpoint and uri variable ("/api/medications/{id}/", "1"), then set the content type you're expecting, which is MediaType.APPLICATION_JSON. Expect the response status to be ok. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if medication not found
     */
    @Test
    public void getMedicationRecord_success() throws Exception {

        when(medicationService.getMedicationById(MEDICATION_1.getId())).thenReturn(Optional.of(MEDICATION_1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medications/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(MEDICATION_1.getId()))
                .andExpect(jsonPath("$.data.Name").value(MEDICATION_1.getName()))
                .andExpect(jsonPath("$.data.description").value(MEDICATION_1.getDescription()))
                .andExpect(jsonPath("$.data.dosage").value(MEDICATION_1.getDosage())).andExpect(jsonPath("$.data.currentStatus").value(MEDICATION_1.getIsCurrent()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     *
     * This test says that when we call medicationService.createMedication(), create a mock of any medication, then return the medication.
     * Create a mock request and set it equal to calling a POST request to the endpoint ("/api/medications/"), then set the content type you're expecting, which is MediaType.APPLICATION_JSON. Accept the content and  convert it from Java to JSON, then write the value of the medication's record as a string.
     * Perform the mock request and expect the response status to be isCreated. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. Expect the jsonPath of the 'message' key of the payload to have a value of 'success'. Then print the message.
     *
     * @throws Exception if medication already exists
     */
    @Test
    public void createMedicationRecord_success() throws Exception {

        when(medicationService.createMedication(Mockito.any(Medication.class))).thenReturn(MEDICATION_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/medications/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(MEDICATION_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(MEDICATION_1.getId()))
                .andExpect(jsonPath("$.data.name").value(MEDICATION_1.getName()))
                .andExpect(jsonPath("$.data.description").value(MEDICATION_1.getDescription()))
                .andExpect(jsonPath("$.data.dosage").value(MEDICATION_1.getDosage())).andExpect(jsonPath("$.data.currentStatus").value(MEDICATION_1.getIsCurrent()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


    /**
     * This test says that when we call medicationService.updateMedication() in instances where the medication is not found, to create a mock of any medication and then return an empty optional.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/medications/{id}/", 1L). Then set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be not found. Expect the jsonPath of the payload and a not null value. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find medication with id 1'. Then print the message.
     *
     * @throws Exception if medication not found
     */
    @Test
    public void updateMedicationRecord_recordNotFound() throws Exception {

        when(medicationService.updateMedication(anyLong(), Mockito.any(Medication.class))).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/medications/{id}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find medication with id 1"))
                .andDo(print());
    }


    /**
     * This test says that when we call medicationService.updateMedication() in successful instances where the medication is found, to create a mock of any medication, then return the updated medication.
     * Create a mock request and set it equal to calling a PUT request to the endpoint and uri variable ("/api/medications/{id}/", 1L). Then set the content type you're expecting, which is 'MediaType.APPLICATION_JSON'. Accept the content and convert it from Java to JSON, then write the value of the medication object as a string.
     * Perform the mock request and expect the response status to be ok. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. And expect the jsonPath of the 'message' key of the payload to have a value of 'medication with id 1 has been successfully updated'. Then print the message.
     *
     * @throws Exception if medication not found
     */
    @Test
    public void updateMedicationRecord_success() throws Exception {

        Long medicationId = 1L;
        Medication medication = new Medication(medicationId, "Original Name", "Original Description", "Original Dosage", true);
        Medication updatedMedication = new Medication(medicationId, "Updated Name", "Updated Description", "Updated Dosage", false);

        when(medicationService.updateMedication(anyLong(), Mockito.any(Medication.class))).thenReturn(Optional.of(updatedMedication));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/medications/{id}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(medication));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(updatedMedication.getId()))
                .andExpect(jsonPath("$.data.Name").value(updatedMedication.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedMedication.getDescription()))
                .andExpect(jsonPath("$.data.dosage").value(updatedMedication.getDosage())).andExpect(jsonPath("$.data.currentStatus").value(updatedMedication.getIsCurrent()))
                .andExpect(jsonPath("$.message").value("medication with id 1 has been successfully updated"))
                .andDo(print());
    }


    /**
     * This test says that when we call medicationService.deleteMedication() in instances where the medication is not found, then return an empty optional.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/medications/{id}/", "1"). Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be not found. And expect the jsonPath of the 'message' key of the payload to have a value of 'cannot find medication with id 1'. Then print the message.
     *
     * @throws Exception if medication not found
     */
    @Test
    public void deleteMedicationRecord_recordNotFound() throws Exception {

        when(medicationService.deleteMedication(MEDICATION_1.getId())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/medications/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("cannot find medication with id 1"))
                .andDo(print());
    }


    /**
     * This test says that when we call medicationService.deleteMedication() in successful instances where the medication is found, then to return the medication if it exists.
     * Create a mock request and set it equal to calling a DELETE request to the endpoint and uri variable ("/api/medications/{id}/", "1"). Set the content type you're expecting, which is 'MediaType.APPLICATION_JSON', and accept it.
     * Perform the mock request and expect the response status to be ok. Expect the jsonPath of the payload and a not null value. Expect the jsonPath of the attributes in the payload to be equal to the value of the get method for that attribute. And expect the jsonPath of the 'message' key of the payload to have a value of 'medication with id 1 has been successfully deleted'. Then print the message.
     *
     * @throws Exception if medication not found
     */
    @Test
    public void deleteMedicationRecord_success() throws Exception {

        when(medicationService.deleteMedication(MEDICATION_1.getId())).thenReturn(Optional.of(MEDICATION_1));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/medications/{id}/", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(MEDICATION_1.getId()))
                .andExpect(jsonPath("$.data.name").value(MEDICATION_1.getName()))
                .andExpect(jsonPath("$.data.description").value(MEDICATION_1.getDescription()))
                .andExpect(jsonPath("$.data.dosage").value(MEDICATION_1.getDosage())).andExpect(jsonPath("$.data.currentStatus").value(MEDICATION_1.getIsCurrent()))
                .andExpect(jsonPath("$.message").value("medication with id 1 has been successfully deleted"))
                .andDo(print());
    }

}
