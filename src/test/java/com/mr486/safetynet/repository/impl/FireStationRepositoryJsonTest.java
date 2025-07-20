package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.tools.JsonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FireStationRepositoryJsonTest {

  @Mock
  private JsonService jsonService;

  @InjectMocks
  private FireStationRepositoryJson fireStationRepositoryJson;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByAddress_shouldReturnFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);

    Optional<FireStation> result = fireStationRepositoryJson.findByAddress("123 Main St");

    assertTrue(result.isPresent());
    assertEquals("123 Main St", result.get().getAddress());
    assertEquals(1, result.get().getStation());
  }

  @Test
  void findByAddress_shouldReturnEmpty_whenAddressDoesNotExist() {
    Optional<FireStation> result = fireStationRepositoryJson.findByAddress("456 Oak St");
    assertTrue(result.isEmpty());
  }

  @Test
  void save_shouldAddNewFireStation_whenAddressDoesNotExist() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    FireStation result = fireStationRepositoryJson.save(fireStation);

    assertEquals(fireStation, result);
  }

  @Test
  void save_shouldUpdateExistingFireStation_whenAddressExists() {
    FireStation existingFireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(existingFireStation);
    FireStation updatedFireStation = new FireStation("123 Main St", 2);

    FireStation result = fireStationRepositoryJson.save(updatedFireStation);

    assertEquals(updatedFireStation, result);
    assertEquals(2, existingFireStation.getStation());
  }

  @Test
  void delete_shouldRemoveFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);
    fireStationRepositoryJson.delete("123 Main St");

    Optional<FireStation> result = fireStationRepositoryJson.findByAddress("123 Main St");
    assertFalse(result.isPresent());
  }

  @Test
  void delete_shouldDoNothing_whenAddressDoesNotExist() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);

    fireStationRepositoryJson.delete("456 Oak St");

    Optional<FireStation> result = fireStationRepositoryJson.findByAddress("123 Main St");
    assertTrue(result.isPresent());
  }

  @Test
  void findByStationNumber_shouldReturnFireStations_whenStationNumberExists() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 1);
    fireStationRepositoryJson.save(fireStation1);
    fireStationRepositoryJson.save(fireStation2);

    List<FireStation> result = fireStationRepositoryJson.findByStationNumber(1);

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }

  @Test
  void findByStationNumber_shouldReturnEmptyList_whenStationNumberDoesNotExist() {
    List<FireStation> result = fireStationRepositoryJson.findByStationNumber(2);

    assertTrue(result.isEmpty());
  }

  @Test
  void exists_shouldReturnTrue_whenFireStationExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);

    boolean result = fireStationRepositoryJson.exists("123 Main St");

    assertTrue(result);
  }

  @Test
  void exists_shouldReturnFalse_whenFireStationDoesNotExist() {
    boolean result = fireStationRepositoryJson.exists("456 Oak St");

    assertFalse(result);
  }

  @Test
  void findAll_shouldReturnAllFireStations() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 2);
    fireStationRepositoryJson.save(fireStation1);
    fireStationRepositoryJson.save(fireStation2);

    List<FireStation> result = fireStationRepositoryJson.findAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }

  @Test
  void findByAddress_shouldReturnFireStation_whenCaseIsDifferent() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);

    Optional<FireStation> result = fireStationRepositoryJson.findByAddress("123 MAIN st");

    assertTrue(result.isPresent());
    assertEquals("123 Main St", result.get().getAddress());
  }


  @Test
  void findByStationNumber_shouldReturnEmptyList_whenNoFireStationWithNumber() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryJson.save(fireStation);

    List<FireStation> result = fireStationRepositoryJson.findByStationNumber(99);

    assertTrue(result.isEmpty());
  }
}