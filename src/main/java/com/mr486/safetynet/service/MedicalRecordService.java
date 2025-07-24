package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.request.MedicalRecordDto;
import com.mr486.safetynet.model.MedicalRecord;

import java.util.List;

/**
 * Service interface for managing medical records.
 * Provides methods to find, save, update, and delete medical records.
 */
public interface MedicalRecordService {

  /**
   * Retrieves all medical records.
   *
   * @return a list of all medical records.
   */
  List<MedicalRecord> findAll();

  /**
   * Finds a medical record by first and last name.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   * @return the medical record for the specified person, or null if not found.
   */
  MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Saves a new medical record or updates an existing one.
   *
   * @param firstName       the first name of the person.
   * @param lastName        the last name of the person.
   * @param medicalRecordDto the DTO containing medical record data.
   * @return the saved or updated medical record.
   */
  MedicalRecord save(String firstName, String lastName, MedicalRecordDto medicalRecordDto);

  /**
   * Deletes a medical record by first and last name.
   *
   * @param firstName the first name of the person.
   * @param lastName  the last name of the person.
   */
  void delete(String firstName, String lastName);

  /**
   * Updates an existing medical record with new data.
   *
   * @param firstName       the first name of the person.
   * @param lastName        the last name of the person.
   * @param medicalRecordDto the DTO containing updated medical record data.
   * @return the updated medical record.
   */
  MedicalRecord update(String firstName, String lastName, MedicalRecordDto medicalRecordDto);

  /**
   * Checks if a person is an adult based on their medical record.
   *
   * @return true if the person is an adult, false otherwise.
   */
  boolean isAdult(String birthdate);

  /**
   * Calculates the age of a person based on their medical record.
   * @return the age of the person in years.
   */
  int getAge(String birthdate);

}
