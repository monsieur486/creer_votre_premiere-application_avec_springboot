package com.mr486.safetynet.tools;

import com.mr486.safetynet.dto.request.DataBindingDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for loading JSON data related to fire stations, persons, and medical records.
 * This service uses a JsonDataReader to read the data from a JSON file and provides methods
 * to retrieve lists of fire stations, persons, and medical records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JsonService {

  private final JsonDataReader jsonDataReader;

  /**
   * Loads fire stations from the JSON data.
   *
   * @return a list of fire stations
   */
  public List<FireStation> loadFireStations() {
    List<FireStation> fireStations = new ArrayList<>();
    try {
      DataBindingDto dataBindingDto = jsonDataReader.loadData();
      if (dataBindingDto.getFirestations() != null) {
        fireStations = dataBindingDto.getFirestations();
      }
    } catch (Exception e) {
      log.warn("No fire stations found in json file.");
    }
    return fireStations;
  }

  /**
   * Loads persons from the JSON data.
   *
   * @return a list of persons
   */
  public List<Person> loadPersons() {
    List<Person> persons = new ArrayList<>();
    try {
      DataBindingDto dataBindingDto = jsonDataReader.loadData();
      if (dataBindingDto.getPersons() != null) {
        persons = dataBindingDto.getPersons();
      }
    } catch (Exception e) {
      log.warn("No persons found in json file.");
    }
    return persons;
  }

  /**
   * Loads medical records from the JSON data.
   *
   * @return a list of medical records
   */
  public List<MedicalRecord> loadMedicalRecords() {
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    try {
      DataBindingDto dataBindingDto = jsonDataReader.loadData();
      if (dataBindingDto.getMedicalrecords() != null) {
        medicalRecords = dataBindingDto.getMedicalrecords();
      }
    } catch (Exception e) {
      log.warn("No medical records found in json file.");
    }
    return medicalRecords;
  }

}
