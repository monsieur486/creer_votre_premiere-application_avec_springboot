package com.mr486.safetynet.dto;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DataBinding} class.
 * This test class verifies the correct behavior of the DataBinding,
 * including initialization and manipulation of persons, firestations, and medical records lists.
 */
class DataBindingTest {

  /**
   * Instance of DataBinding used for testing.
   */
  private DataBinding dataBinding;

  /**
   * Initializes a new DataBinding before each test.
   */
  @BeforeEach
  void setUp() {
    dataBinding = new DataBinding();
  }

  /**
   * Verifies that the constructor initializes empty lists for persons, firestations, and medical records.
   */
  @Test
  void constructor_shouldInitializeEmptyLists() {
    assertNotNull(dataBinding.getPersons());
    assertNotNull(dataBinding.getFirestations());
    assertNotNull(dataBinding.getMedicalrecords());
    assertTrue(dataBinding.getPersons().isEmpty());
    assertTrue(dataBinding.getFirestations().isEmpty());
    assertTrue(dataBinding.getMedicalrecords().isEmpty());
  }

  /**
   * Verifies that setPersons updates the persons list.
   */
  @Test
  void setPersons_shouldUpdatePersonsList() {
    List<Person> persons = List.of(new Person(
            "John",
            "Doe",
            "123 Street",
            "City",
            "12345",
            "123-456-7890",
            "john.doe@example.com"));
    dataBinding.setPersons(persons);
    assertEquals(persons, dataBinding.getPersons());
  }

  /**
   * Verifies that setFirestations updates the firestations list.
   */
  @Test
  void setFirestations_shouldUpdateFirestationsList() {
    List<FireStation> firestations = List.of(new FireStation("123 Street", 1 ));
    dataBinding.setFirestations(firestations);
    assertEquals(firestations, dataBinding.getFirestations());
  }

  /**
   * Verifies that setMedicalrecords updates the medical records list.
   */
  @Test
  void setMedicalrecords_shouldUpdateMedicalRecordsList() {
    ArrayList<String> medications = new ArrayList<>();
    medications.add("med1");
    medications.add("med2");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("allergy1");
    allergies.add("allergy2");
    MedicalRecord medicalRecord = new MedicalRecord(
            "Jane",
            "Doe",
            "02/02/2000",
            medications,
            allergies);
    List<MedicalRecord> medicalRecords = List.of(medicalRecord);
    dataBinding.setMedicalrecords(medicalRecords);
    assertEquals(medicalRecords, dataBinding.getMedicalrecords());
  }

  /**
   * Verifies that a person can be added to the persons list.
   */
  @Test
  void addPerson_shouldAddPersonToList() {
    Person person = new Person(
            "Jane",
            "Doe",
            "456 Avenue",
            "City",
            "67890",
            "987-654-3210",
            "jane.doe@example.com");
    dataBinding.getPersons().add(person);
    assertTrue(dataBinding.getPersons().contains(person));
  }

  /**
   * Verifies that a fire station can be added to the firestations list.
   */
  @Test
  void addFireStation_shouldAddFireStationToList() {
    FireStation fireStation = new FireStation(
            "456 Avenue",
            2);
    dataBinding.getFirestations().add(fireStation);
    assertTrue(dataBinding.getFirestations().contains(fireStation));
  }

  /**
   * Verifies that a medical record can be added to the medical records list.
   */
  @Test
  void addMedicalRecord_shouldAddMedicalRecordToList() {
    ArrayList<String> medications = new ArrayList<>();
    medications.add("med1");
    medications.add("med2");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("allergy1");
    allergies.add("allergy2");
    MedicalRecord medicalRecord = new MedicalRecord(
            "Jane",
            "Doe",
            "02/02/2000",
            medications,
            allergies);
    dataBinding.getMedicalrecords().add(medicalRecord);
    assertTrue(dataBinding.getMedicalrecords().contains(medicalRecord));
  }
}