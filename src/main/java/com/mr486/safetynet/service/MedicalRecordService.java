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


@Service
@RequiredArgsConstructor
public class MedicalRecordService {

  private final MedicalRecordRepository medicalRecordRepository;
  private final PersonRepository personRepository;

  public List<MedicalRecord> findAll() {
    return medicalRecordRepository.findAll();
  }

  public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {

    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }

    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> medicalRecordNotFoundException(firstName, lastName));
  }

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

  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw medicalRecordNotFoundException(firstName, lastName);
    }
    medicalRecordRepository.delete(firstName, lastName);
  }

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


  private boolean exists(String firstName, String lastName) {
    return medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName).isPresent();
  }

  // private methods to create exceptions

  private EntityNotFoundException medicalRecordNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Medical record for: " + firstName + " " + lastName + " not exists.");
  }

  private EntityAlreadyExistsException medicalRecordAlreadyExists(String firstName, String lastName) {
    return new EntityAlreadyExistsException("Medical record for: " + firstName + " " + lastName + " already exists.");
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException("Person with name " + firstName + " " + lastName + " does not exist.");
  }

  public boolean isAdult(Person person) {
    try {
      int age = getAge(person);
      return age >= AppConfiguation.ADULT_AGE;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error calculating age for person: " + person.getFirstName() + " " + person.getLastName(), e);
    }
  }

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
}
