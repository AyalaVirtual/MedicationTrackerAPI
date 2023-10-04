package com.example.pharmacyapi.controller;

import com.example.pharmacyapi.exception.InformationExistException;
import com.example.pharmacyapi.exception.InformationNotFoundException;
import com.example.pharmacyapi.model.Medication;
import com.example.pharmacyapi.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pharmacyapi.service.MedicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/")
public class MedicationController {

    private MedicationService medicationService;

    // These attributes are for testing
    static HashMap<String, Object> result = new HashMap<>();
    static HashMap<String, Object> message = new HashMap<>();

    @Autowired
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    /**
     * This sets the path for GET requests for all medications and checks if the list of medications is empty or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @return the HTTP status message
     */
    @GetMapping(path = "/medications/")
    public ResponseEntity<?> getAllMedications() {

        List<Medication> medicationList = medicationService.getAllMedications();

        if (medicationList.isEmpty()) {
            message.put("message", "cannot find any medications ");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", medicationList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for GET requests for an individual medication and checks if the medication exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param medicationId represents the id of the specific medication the user is trying to get
     * @return the HTTP status message
     */
    @GetMapping(path = "/medications/{medicationId}/")
    public ResponseEntity<?> getMedicationById(@PathVariable(value = "medicationId") Long medicationId) {

        Optional<Medication> medicationOptional = medicationService.getMedicationById(medicationId);

        if (medicationOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", medicationOptional.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find medication with id " + medicationId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for POST requests for a new medication and checks if the medication exists already or not before deciding whether to send an HTTP status message of CREATED or OK
     *
     * @param medicationObject represents the new medication the user is trying to create
     * @return the HTTP status message
     */
    @PostMapping(path = "/medications/")
    public ResponseEntity<?> createMedication(@RequestBody Medication medicationObject) {

        Medication newMedication = medicationService.createMedication(medicationObject);

        if (newMedication != null) {
            message.put("message", "success");
            message.put("data", newMedication);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a medication at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for PUT requests for an existing medication and checks if the medication exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param medicationId represents the id of the medication the user is trying to update
     * @param medicationObject represents the updated version of the medication
     * @return the HTTP status message
     */
    @PutMapping(path = "/medications/{medicationId}/")
    public ResponseEntity<?> updateMedication(@PathVariable(value = "medicationId") Long medicationId, @RequestBody Medication medicationObject) throws InformationNotFoundException {

        Optional<Medication> medicationToUpdate = medicationService.updateMedication(medicationId, medicationObject);

        if (medicationToUpdate.isPresent()) {
            message.put("message", "medication with id " + medicationId + " has been successfully updated");
            message.put("data", medicationToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "medication with id " + medicationId + " not found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for DELETE requests for an existing medication and checks if the medication exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param medicationId represents the id of the medication the user is trying to delete
     * @return the HTTP status message
     */
    @DeleteMapping(path = "/medications/{medicationId}/")
    public ResponseEntity<?> deleteMedication(@PathVariable(value = "medicationId") Long medicationId) {

        Optional<Medication> medicationToDelete = medicationService.deleteMedication(medicationId);

        if (medicationToDelete.isPresent()) {
            message.put("message", "medication with id " + medicationId + " has been successfully deleted");
            message.put("data", medicationToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find medication with id " + medicationId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }



    /**
     * This sets the path for GET requests for all reminders and checks if the list of reminders is empty or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @return the HTTP status message
     */
    @GetMapping(path = "/medications/reminders/")
    public ResponseEntity<?> getAllReminders() {

        List<Reminder> reminderList = medicationService.getAllReminders();

        if (reminderList.isEmpty()) {
            message.put("message", "cannot find any reminders ");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", reminderList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for GET requests for an individual reminder and checks if the reminder exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param reminderId represents the id of the specific reminder the user is trying to get
     * @return the HTTP status message
     */
    @GetMapping(path = "/medications/{medicationId}/reminders/{reminderId}/")
    public ResponseEntity<?> getReminderById(@PathVariable(value = "medicationId") Long medicationId, @PathVariable(value = "reminderId") Long reminderId) {

        Optional<Reminder> reminderOptional = medicationService.getReminderById(medicationId, reminderId);

        if (reminderOptional.isPresent()) {
            message.put("message", "success");
            message.put("data", reminderOptional.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find reminder with id " + reminderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for POST requests for a new reminder and checks if the medication and reminder exist or not before deciding whether to send an HTTP status message of CREATED or OK
     *
     * @param medicationId represents the id of the specific medication whose reminder list the user is trying to create the reminder in
     * @param reminderObject represents the new reminder the user is trying to create
     * @return the HTTP status message
     */
    @PostMapping(path = "/medications/{medicationId}/reminders/")
    public ResponseEntity<?> createReminder(@PathVariable(value = "medicationId") Long medicationId, @RequestBody Reminder reminderObject) {

        Optional<Medication> medication = medicationService.getMedicationById(medicationId);
        Reminder newReminder = medicationService.createReminder(medicationId, reminderObject);

        if (medication.isPresent() && newReminder != null) {
            message.put("message", "success");
            message.put("data", newReminder);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a reminder at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


    /**
     * This sets the path for PUT requests for an individual reminder and checks if the reminder exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param reminderId represents the id of the specific reminder the user is trying to update
     * @param reminderObject represents the updated version of the reminder the user is trying to update
     * @return the HTTP status message
     */
    @PutMapping(path = "/medications/{medicationId}/reminders/{reminderId}/")
    public ResponseEntity<?> updateReminder(@PathVariable(value = "reminderId") Long reminderId, @RequestBody Reminder reminderObject) {

        Optional<Reminder> reminderToUpdate = medicationService.updateReminder(reminderId, reminderObject);

        if (reminderToUpdate.isPresent()) {
            message.put("message", "reminder with id " + reminderId + " has been successfully updated");
            message.put("data", reminderToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "reminder with id " + reminderId + " not found");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * This sets the path for DELETE requests for an individual reminder and checks if the reminder exists or not before deciding whether to send an HTTP status message of OK or NOT FOUND
     *
     * @param reminderId represents the id of the specific reminder the user is trying to delete
     * @return the HTTP status message
     */
    @DeleteMapping(path = "/medications/{medicationId}/reminders/{reminderId}/")
    public ResponseEntity<?> deleteReminder(@PathVariable(value = "reminderId") Long reminderId) {

        Optional<Reminder> reminderToDelete = medicationService.deleteReminder(reminderId);

        if (reminderToDelete.isPresent()) {
            message.put("message", "reminder with id " + reminderId + " has been successfully deleted");
            message.put("data", reminderToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find reminder with id " + reminderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

}
