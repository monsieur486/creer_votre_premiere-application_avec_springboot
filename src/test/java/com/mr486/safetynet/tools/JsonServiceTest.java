package com.mr486.safetynet.tools;

import com.mr486.safetynet.dto.request.DataBindingDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JsonServiceTest {

  @Mock
  private JsonDataReader jsonDataReader;

  private JsonService jsonService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    jsonService = new JsonService(jsonDataReader);
  }

  @Test
  void loadFireStations_shouldReturnFireStations_whenDataIsPresent() {
    List<FireStation> fireStations = List.of(new FireStation("123 Main St", 1));
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setFirestations(fireStations);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<FireStation> result = jsonService.loadFireStations();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("123 Main St", result.get(0).getAddress());
  }

  @Test
  void loadFireStations_shouldReturnEmptyList_whenNoDataIsPresent() {
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setFirestations(null);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<FireStation> result = jsonService.loadFireStations();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void loadPersons_shouldReturnPersons_whenDataIsPresent() {
    List<Person> persons = List.of(new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com"));
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setPersons(persons);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<Person> result = jsonService.loadPersons();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void loadPersons_shouldReturnEmptyList_whenNoDataIsPresent() {
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setPersons(null);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<Person> result = jsonService.loadPersons();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void loadMedicalRecords_shouldReturnMedicalRecords_whenDataIsPresent() {
    ArrayList<String> medications = new ArrayList<>();
    medications.add("Medication1");
    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("Allergy1");
    MedicalRecord medicalRecord = new MedicalRecord(
            "John",
            "Doe",
            "01/01/1980",
            medications,
            allergies);
    List<MedicalRecord> medicalRecords = List.of(medicalRecord);
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setMedicalrecords(medicalRecords);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<MedicalRecord> result = jsonService.loadMedicalRecords();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void loadMedicalRecords_shouldReturnEmptyList_whenNoDataIsPresent() {
    DataBindingDto dataBindingDto = new DataBindingDto();
    dataBindingDto.setMedicalrecords(null);

    when(jsonDataReader.loadData()).thenReturn(dataBindingDto);

    List<MedicalRecord> result = jsonService.loadMedicalRecords();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }


  @Test
  void loadFireStations_shouldReturnEmptyList_whenExceptionIsThrown() {
    when(jsonDataReader.loadData()).thenThrow(new RuntimeException("Erreur de lecture"));

    List<FireStation> result = jsonService.loadFireStations();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void loadPersons_shouldReturnEmptyList_whenExceptionIsThrown() {
    when(jsonDataReader.loadData()).thenThrow(new RuntimeException("Erreur de lecture"));

    List<Person> result = jsonService.loadPersons();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void loadMedicalRecords_shouldReturnEmptyList_whenExceptionIsThrown() {
    when(jsonDataReader.loadData()).thenThrow(new RuntimeException("Erreur de lecture"));

    List<MedicalRecord> result = jsonService.loadMedicalRecords();

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

}