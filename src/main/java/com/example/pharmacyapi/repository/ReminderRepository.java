package com.example.pharmacyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pharmacyapi.model.Medication;
import com.example.pharmacyapi.model.Reminder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    // This method finds a reminder by its id
    Optional<Reminder> findById(Long reminderId);

    // This method finds a reminder by its name
    Reminder findByName(String reminderName);

    // This method finds all reminders by user
    List<Reminder> findByUserId(Long userId);

    // This method retrieves an individual reminder by its id and the current logged in user's id
    Optional<Reminder> findByIdAndUserId(Long reminderId, Long userId);

    // This method retrieves an individual reminder by its name and the current logged in user's id
    Reminder findByNameAndUserId(String reminderName, Long userId);

}
