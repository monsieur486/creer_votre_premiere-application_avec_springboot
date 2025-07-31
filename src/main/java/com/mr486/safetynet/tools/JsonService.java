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
 * This service uses a JsonDataUtil to read the data from a JSON file and provides methods
 * to retrieve lists of fire stations, persons, and medical records.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JsonService {

  private final JsonDataUtil jsonDataUtil;

  /**
   * Loads fire stations from the JSON data.
   *
   * @return a list of fire stations
   */
  public List<FireStation> loadFireStations() {
    List<FireStation> fireStations = new ArrayList<>();
    try {
      DataBindingDto dataBindingDto = jsonDataUtil.loadData();
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
      DataBindingDto dataBindingDto = jsonDataUtil.loadData();
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
      DataBindingDto dataBindingDto = jsonDataUtil.loadData();
      if (dataBindingDto.getMedicalrecords() != null) {
        medicalRecords = dataBindingDto.getMedicalrecords();
      }
    } catch (Exception e) {
      log.warn("No medical records found in json file.");
    }
    return medicalRecords;
  }

  public void saveFireStations(List<FireStation> fireStations) {
    try {
      DataBindingDto data = jsonDataUtil.loadData();
      data.setFirestations(fireStations);
      jsonDataUtil.saveData(data);
    } catch (Exception e) {
      log.error("❌ Failed to save fire stations to JSON", e);
    }
  }

  /**
   * Saves a list of persons to the JSON data.
   *
   * @param persons the list of persons to save
   */
  public void savePersons(List<Person> persons) {
    try {
      DataBindingDto data = jsonDataUtil.loadData();
      data.setPersons(persons);
      jsonDataUtil.saveData(data);
    } catch (Exception e) {
      log.error("❌ Failed to save persons to JSON", e);
    }
  }

  /**
   * Saves a list of medical records to the JSON data.
   *
   * @param medicalRecords the list of medical records to save
   */
  public void saveMedicalRecords(List<MedicalRecord> medicalRecords) {
    try {
      DataBindingDto data = jsonDataUtil.loadData();
      data.setMedicalrecords(medicalRecords);
      jsonDataUtil.saveData(data);
    } catch (Exception e) {
      log.error("❌ Failed to save medical records to JSON", e);
    }
  }

}
