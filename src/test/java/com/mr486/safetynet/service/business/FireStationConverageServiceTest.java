package com.mr486.safetynet.service.business;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationConverageServiceTest {

  @Mock
  private PersonService personService;
  @Mock
  private MedicalRecordService medicalRecordService;
  @Mock
  private FireStationService fireStationService;

  @InjectMocks
  private FireStationConverageService fireStationConverageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCoverageByStationNumber_returnsCoverageResponseDto() {
    int stationNumber = 1;
    List<Person> persons = List.of(
        new Person("John", "Doe", "123 Main St", "City", "12345", "555-1234", "mail@test.fr"),
        new Person("Jane", "Doe", "123 Main St", "City", "12345", "555-5678", "mail2@test.fr")
    );
    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of());
    when(personService.findByAddress("123 Main St")).thenReturn(persons);
    when(medicalRecordService.findByFirstNameAndLastName("John", "Doe")).thenReturn(null);
    when(medicalRecordService.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(null);

    var response = fireStationConverageService.getCoverageByStationNumber(stationNumber);
    assertNotNull(response);
    assertTrue(response.getPersons().isEmpty(), "Expected no persons in the response");
  }

  @Test
  void getCoverageByStationNumber_withNoFireStations_returnsEmptyCoverageResponseDto() {
    int stationNumber = 999; // Non-existent station number
    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of());

    var response = fireStationConverageService.getCoverageByStationNumber(stationNumber);
    assertNotNull(response);
    assertTrue(response.getPersons().isEmpty(), "Expected no persons in the response for non-existent station");
  }

  @Test
  void getCoverageByStationNumber_withMultipleFireStations_returnsCoverageResponseDto() {
    int stationNumber = 2;
    String address = "456 Elm St";
    List<Person> persons = List.of(
            new Person("Alice", "Smith", address, "City", "67890", "555-9876", "mail@test.com"),
            new Person("Bob", "Johnson", address, "City", "67890", "555-5432", "mail2@test.com")
    );
    FireStation fireStation = new FireStation(address, stationNumber);
    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of(fireStation));
    when(personService.findByAddress(address)).thenReturn(persons);
    when(medicalRecordService.findByFirstNameAndLastName("Alice", "Smith")).thenReturn(null);
    when(medicalRecordService.findByFirstNameAndLastName("Bob", "Johnson")).thenReturn(null);

    var response = fireStationConverageService.getCoverageByStationNumber(stationNumber);
    assertNotNull(response);
    assertEquals(2, response.getPersons().size(), "Expected two persons in the response");
    assertEquals("Alice", response.getPersons().get(0).getFirstName(), "Expected first person to be Alice");
    assertEquals("Bob", response.getPersons().get(1).getFirstName(), "Expected second person to be Bob");
  }

  @Test
  void getCoverageByStationNumber_withNoPersons_returnsEmptyCoverageResponseDto() {
    int stationNumber = 3;
    when(fireStationService.findByStationNumber(stationNumber)).thenReturn(List.of());
    when(personService.findByAddress("Nonexistent Address")).thenReturn(List.of());

    var response = fireStationConverageService.getCoverageByStationNumber(stationNumber);
    assertNotNull(response);
    assertTrue(response.getPersons().isEmpty(), "Expected no persons in the response for non-existent address");
  }


}