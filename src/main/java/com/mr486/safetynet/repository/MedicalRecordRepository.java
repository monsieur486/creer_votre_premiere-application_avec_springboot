package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.MedicalRecord;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing MedicalRecord entities.
 * This interface defines methods for CRUD operations and querying medical records by first and last name.
 */
public interface MedicalRecordRepository {

  /**
   * Retrieves all medical records.
   *
   * @return a list of all medical records.
   */
  List<MedicalRecord> findAll();

  /**
   * Finds a medical record by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return an Optional containing the MedicalRecord if found, or empty if not found
   */
  Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Saves a medical record entity.
   *
   * @param medicalRecord the medical record to save.
   * @return the saved MedicalRecord entity.
   */
  MedicalRecord save(MedicalRecord medicalRecord);

  /**
   * Deletes a medical record by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   */
  void delete(String firstName, String lastName);

  /**
   * Checks if a medical record exists by first name and last name.
   *
   * @param firstName the first name to check
   * @param lastName  the last name to check
   * @return true if a medical record exists for the specified person, false otherwise
   */
  boolean exists(String firstName, String lastName);
}
