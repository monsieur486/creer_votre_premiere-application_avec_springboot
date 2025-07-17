package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.MedicalRecordDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.MedicalRecordRepository;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Service class for managing medical records.
 * Provides methods to find, save, update, and delete medical records.
 */
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;
  private final PersonRepository personRepository;

  /**
   * Retrieves all medical records.
   *
   * @return a list of all medical records.
   */
  public List<MedicalRecord> findAll() {
    return medicalRecordRepository.findAll();
  }

  /**
   * Finds a medical record by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return the MedicalRecord if found
   * @throws EntityNotFoundException if the medical record does not exist
   */
  public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {

    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));
  }

  /**
   * Saves a new medical record for a person.
   *
   * @param firstName       the first name of the person
   * @param lastName        the last name of the person
   * @param medicalRecordDto the DTO containing medical record data
   * @return the saved MedicalRecord
   * @throws EntityAlreadyExistsException if a medical record for the person already exists
   * @throws EntityNotFoundException       if the person does not exist
   */
  public MedicalRecord save(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {

    if (!personRepository.exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }

    if (exists(firstName, lastName)) {
      throw medicalRecordAlreadyExists(firstName, lastName);
    }

    Person person = personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));

    MedicalRecord medicalRecord = medicalRecordDto.toMedicalRecord(person.getFirstName(), person.getLastName());
    return medicalRecordRepository.save(medicalRecord);

  }

  /**
   * Deletes a medical record by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @throws EntityNotFoundException if the medical record does not exist
   */
  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }
    medicalRecordRepository.delete(firstName, lastName);
  }

  /**
   * Updates an existing medical record for a person.
   *
   * @param firstName       the first name of the person
   * @param lastName        the last name of the person
   * @param medicalRecordDto the DTO containing updated medical record data
   * @return the updated MedicalRecord
   * @throws EntityNotFoundException       if the medical record does not exist
   * @throws EntityAlreadyExistsException if a medical record for the person already exists
   */
  public MedicalRecord update(String firstName, String lastName, MedicalRecordDto medicalRecordDto) {
    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    MedicalRecord existingMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));
    MedicalRecord updatedMedicalRecord = medicalRecordDto.toMedicalRecord(
            existingMedicalRecord.getFirstName(),
            existingMedicalRecord.getLastName());

    medicalRecordRepository.delete(firstName, lastName);

    return medicalRecordRepository.save(updatedMedicalRecord);
  }

  /**
   * Checks if a person is an adult based on their age.
   *
   * @param person the person to check
   * @return true if the person is an adult, false otherwise
   * @throws IllegalArgumentException if the person's age cannot be calculated
   */
  public boolean isAdult(Person person) {
    try {
      int age = getAge(person);
      return age >= AppConfiguation.ADULT_AGE;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error calculating age for person: " + person.getFirstName() + " " + person.getLastName(), e);
    }
  }

  /**
   * Calculates the age of a person based on their medical record.
   *
   * @param person the person whose age is to be calculated
   * @return the age of the person in years
   * @throws IllegalArgumentException if the birthdate format is invalid
   */
  public int getAge(Person person) {
    MedicalRecord medicalRecord = findByFirstNameAndLastName(
            person.getFirstName(),
            person.getLastName());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConfiguation.DATE_FORMAT);
    try {
      LocalDate now = LocalDate.now();
      return Period.between(LocalDate.parse(medicalRecord.getBirthdate(), formatter), now).getYears();
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Invalid birthdate format. Expected format is: " + AppConfiguation.DATE_FORMAT, e);
    }

  }

  // private methods to create exceptions

  private boolean exists(String firstName, String lastName) {
    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
  }


  private EntityNotFoundException medicalRecordNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Medical record for: " + firstName + " " + lastName + " not exists.");
  }

  private EntityAlreadyExistsException medicalRecordAlreadyExists(String firstName, String lastName) {
    return new EntityAlreadyExistsException("Medical record for: " + firstName + " " + lastName + " already exists.");
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Person with name " + firstName + " " + lastName + " does not exist.");
  }

}
