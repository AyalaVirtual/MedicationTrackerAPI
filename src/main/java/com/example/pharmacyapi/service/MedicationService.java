package com.example.pharmacyapi.service;

import com.example.pharmacyapi.exception.InformationExistException;
import com.example.pharmacyapi.exception.InformationNotFoundException;
import com.example.pharmacyapi.model.Medication;
import com.example.pharmacyapi.model.Reminder;
import com.example.pharmacyapi.model.User;
import com.example.pharmacyapi.repository.MedicationRepository;
import com.example.pharmacyapi.repository.ReminderRepository;
import com.example.pharmacyapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class MedicationService {

    private MedicationRepository medicationRepository;
    private ReminderRepository reminderRepository;


    @Autowired // This enables us to use the methods from JpaRepository
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Autowired
    public void setReminderRepository(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }


    /**
     * This gets current user's principal (this is their details/information, such as whether their account is expired, locked, etc.) from SecurityContextHolder whenever the user logs in/sends jwt key and returns the current logged in user
     *
     * @return the details of the user who is currently logged in
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }


    /**
     * This is a GET request that checks to see if the list of medications is empty before either throwing an InformationNotFoundException, or  returning the list of medications
     *
     * @return a list of all medications
     */
    public List<Medication> getAllMedications() {
        List<Medication> medicationList = medicationRepository.findByUserId(MedicationService.getCurrentLoggedInUser().getId());

        if (medicationList.isEmpty()) {
            throw new InformationNotFoundException("no medications found for user id");
        } else {
            return medicationList;
        }
    }


    /**
     * This is a GET request that checks to see if an individual medication exists before either returning it, or throwing an InformationNotFoundException
     *
     * @param medicationId represents the id of the specific medication the user is trying to get
     * @return medication by id if it exists
     */
    public Optional<Medication> getMedicationById(Long medicationId) {
        Optional<Medication> medicationOptional = medicationRepository.findByIdAndUserId(medicationId, MedicationService.getCurrentLoggedInUser().getId());

        if (medicationOptional.isPresent()) {
            return medicationOptional;
        } else {
            throw new InformationNotFoundException("medication with id " + medicationId + " not found");
        }
    }


    /**
     * This is a POST request that checks to see if a medication already exists before either throwing an InformationExistException, or saving the newly created medication to the repository
     *
     * @param medicationObject represents the new medication the user is trying to create
     * @return newly created medication
     */
    public Medication createMedication(Medication medicationObject) {
        Medication medication = medicationRepository.findByNameAndUserId(medicationObject.getName(), MedicationService.getCurrentLoggedInUser().getId());

        if (medication != null) {
            throw new InformationExistException("medication with name " + medicationObject.getName() + " already exists");
        } else {
            medicationObject.setUser(MedicationService.getCurrentLoggedInUser());
            return medicationRepository.save(medicationObject);
        }
    }


    /**
     * This is a PUT request that checks to see if a medication exists before either throwing an InformationNotFoundException, or setting the attributes and saving the newly updated medication to the repository
     *
     * @param medicationId represents the id of the medication the user is trying to update
     * @param medicationObject represents the updated version of the medication
     * @return the newly updated medication
     */
    public Optional<Medication> updateMedication(Long medicationId, Medication medicationObject) {
        Optional<Medication> medicationOptional = medicationRepository.findByIdAndUserId(medicationId, MedicationService.getCurrentLoggedInUser().getId());

        if (medicationOptional.isPresent()) {

            medicationOptional.get().setName(medicationObject.getName());
            medicationOptional.get().setDescription(medicationObject.getDescription());
            medicationOptional.get().setDosage(medicationObject.getDosage());
            medicationOptional.get().setIsCurrent(medicationObject.getIsCurrent());

            medicationOptional.get().setUser(MedicationService.getCurrentLoggedInUser());

            medicationRepository.save(medicationOptional.get());
            return medicationOptional;

        } else {

            throw new InformationNotFoundException("medication with id " + medicationId + " not found");
        }
    }


    /**
     * This is a DELETE request that checks to see if an individual medication exists before either deleting it, or throwing an InformationNotFoundException
     *
     * @param medicationId represents the id of the medication the user is trying to delete
     * @return the deleted medication
     */
    public Optional<Medication> deleteMedication(Long medicationId) {
        Optional<Medication> medicationOptional = medicationRepository.findByIdAndUserId(medicationId, MedicationService.getCurrentLoggedInUser().getId());

        if (medicationOptional.isPresent()) {
            medicationRepository.deleteById(medicationId);
            return medicationOptional;
        } else {
            throw new InformationNotFoundException("medication with id " + medicationId + " not found");
        }
    }



    /**
     * This is a GET request that returns a list of all reminders
     *
     * @return all reminders
     */
    public List<Reminder> getAllReminders() {
//        return reminderRepository.findAll();

        List<Reminder> reminderList = reminderRepository.findByUserId(MedicationService.getCurrentLoggedInUser().getId());

        if (reminderList.isEmpty()) {
            throw new InformationNotFoundException("no reminders found for user id");
        } else {
            return reminderList;
        }
    }


    /**
     * This is a GET request that checks to see if an individual reminder exists and is in the medication's reminder list before either returning it, or throwing an InformationNotFoundException
     *
     * @param medicationId represents the id of the specific medication whose reminder list the user is trying to get a reminder from
     * @param reminderId represents the id of the specific reminder the user is trying to get
     * @return reminder by id if it exists
     */
    public Optional<Reminder> getReminderById(Long medicationId, Long reminderId) {
        Optional<Reminder> reminderOptional = reminderRepository.findByIdAndUserId(reminderId, MedicationService.getCurrentLoggedInUser().getId());

        Optional<Medication> medication = medicationRepository.findByIdAndUserId(medicationId, MedicationService.getCurrentLoggedInUser().getId());

         if (reminderOptional.isPresent() && medication.get().getReminderList().contains(reminderOptional.get())) {
            return reminderOptional;
         } else {
            throw new InformationNotFoundException("reminder with id " + reminderId + " not found");
         }
    }


    /**
     * This is a POST request that checks to see if the medication whose reminder list the user is trying to create a reminder in already exists before either throwing an InformationNotFoundException, or moving on to check if the reminder already exists. From there, it either saves the newly created reminder to the repository, or throws an InformationExistException
     *
     * @param medicationId represents the id of a specific medication whose reminder list the user is trying to create a reminder in
     * @param reminderObject represents the reminder the user is trying to create
     * @return the newly created reminder
     */
    public Reminder createReminder(Long medicationId, Reminder reminderObject) {
        Optional<Medication> medication = medicationRepository.findByIdAndUserId(medicationId, MedicationService.getCurrentLoggedInUser().getId());

        if (medication.isEmpty()) {
            throw new InformationNotFoundException("medication with id " + medicationId + " not found");
        }

        Reminder reminder = reminderRepository.findByNameAndUserId(reminderObject.getName(), MedicationService.getCurrentLoggedInUser().getId());

        if (reminder != null) {
            throw new InformationExistException("reminder with name " + reminderObject.getName() + " already exists");
        }
        reminderObject.setMedication(medication.get());
        reminderObject.setUser(MedicationService.getCurrentLoggedInUser());
        List<Reminder> reminderList = medication.get().getReminderList();
        medication.get().addToReminderList(reminderObject);
        return reminderRepository.save(reminderObject);
    }


    /**
     * This is a PUT request that checks to see if a reminder exists before either throwing an InformationNotFoundException, or setting its attributes and saving the newly updated reminder to the repository
     *
     * @param reminderId represents id of the reminder the user is trying to update
     * @param reminderObject represents the updated version of the reminder
     * @return the newly updated reminder
     */
    public Optional<Reminder> updateReminder(Long reminderId, Reminder reminderObject) {
        Optional<Reminder> reminderOptional = reminderRepository.findByIdAndUserId(reminderId, MedicationService.getCurrentLoggedInUser().getId());

        if (reminderOptional.isPresent()) {

            reminderOptional.get().setName(reminderObject.getName());
            reminderOptional.get().setInstructions(reminderObject.getInstructions());
            reminderOptional.get().setMedication(reminderObject.getMedication());
            reminderRepository.save(reminderOptional.get());
            return reminderOptional;

        } else {

            throw new InformationNotFoundException("reminder with id " + reminderId + " not found");
        }
    }


    /**
     * This is a DELETE request that checks to see if an individual reminder exists before either deleting it, or throwing an InformationNotFoundException
     *
     * @param reminderId represents the id of the specific reminder the user is trying to delete
     * @return the deleted reminder
     */
    public Optional<Reminder> deleteReminder(Long reminderId) {
        Optional<Reminder> reminderOptional = reminderRepository.findByIdAndUserId(reminderId, MedicationService.getCurrentLoggedInUser().getId());

        if (reminderOptional.isPresent()) {
            reminderRepository.deleteById(reminderId);
            return reminderOptional;
        } else {
            throw new InformationNotFoundException("reminder with id " + reminderId + " not found");
        }
    }

}
