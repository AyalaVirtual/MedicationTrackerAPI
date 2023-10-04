package com.example.pharmacyapi.repository;

import com.example.pharmacyapi.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    // This method finds a medication by its id
    Optional<Medication> findById(Long medicationId);

    // This method finds a medication by its name
    Medication findByName(String medicationName);

    // This method finds all medications by user
    List<Medication> findByUserId(Long userId);

    // This method retrieves an individual medication by its id and the current logged in user's id
    Optional<Medication> findByIdAndUserId(Long medicationId, Long userId);

    // This method retrieves an individual medication by its name and the current logged in user's id
    Medication findByNameAndUserId(String medicationName, Long userId);

}
