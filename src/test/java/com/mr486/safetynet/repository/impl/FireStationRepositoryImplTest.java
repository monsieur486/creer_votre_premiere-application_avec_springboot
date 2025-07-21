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

class FireStationRepositoryImplTest {

  @Mock
  private JsonService jsonService;

  @InjectMocks
  private FireStationRepositoryImpl fireStationRepositoryImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByAddress_shouldReturnFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);

    Optional<FireStation> result = fireStationRepositoryImpl.findByAddress("123 Main St");

    assertTrue(result.isPresent());
    assertEquals("123 Main St", result.get().getAddress());
    assertEquals(1, result.get().getStation());
  }

  @Test
  void findByAddress_shouldReturnEmpty_whenAddressDoesNotExist() {
    Optional<FireStation> result = fireStationRepositoryImpl.findByAddress("456 Oak St");
    assertTrue(result.isEmpty());
  }

  @Test
  void save_shouldAddNewFireStation_whenAddressDoesNotExist() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    FireStation result = fireStationRepositoryImpl.save(fireStation);

    assertEquals(fireStation, result);
  }

  @Test
  void save_shouldUpdateExistingFireStation_whenAddressExists() {
    FireStation existingFireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(existingFireStation);
    FireStation updatedFireStation = new FireStation("123 Main St", 2);

    FireStation result = fireStationRepositoryImpl.save(updatedFireStation);

    assertEquals(updatedFireStation, result);
    assertEquals(2, existingFireStation.getStation());
  }

  @Test
  void delete_shouldRemoveFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);
    fireStationRepositoryImpl.delete("123 Main St");

    Optional<FireStation> result = fireStationRepositoryImpl.findByAddress("123 Main St");
    assertFalse(result.isPresent());
  }

  @Test
  void delete_shouldDoNothing_whenAddressDoesNotExist() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);

    fireStationRepositoryImpl.delete("456 Oak St");

    Optional<FireStation> result = fireStationRepositoryImpl.findByAddress("123 Main St");
    assertTrue(result.isPresent());
  }

  @Test
  void findByStationNumber_shouldReturnFireStations_whenStationNumberExists() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 1);
    fireStationRepositoryImpl.save(fireStation1);
    fireStationRepositoryImpl.save(fireStation2);

    List<FireStation> result = fireStationRepositoryImpl.findByStationNumber(1);

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }

  @Test
  void findByStationNumber_shouldReturnEmptyList_whenStationNumberDoesNotExist() {
    List<FireStation> result = fireStationRepositoryImpl.findByStationNumber(2);

    assertTrue(result.isEmpty());
  }

  @Test
  void exists_shouldReturnTrue_whenFireStationExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);

    boolean result = fireStationRepositoryImpl.exists("123 Main St");

    assertTrue(result);
  }

  @Test
  void exists_shouldReturnFalse_whenFireStationDoesNotExist() {
    boolean result = fireStationRepositoryImpl.exists("456 Oak St");

    assertFalse(result);
  }

  @Test
  void findAll_shouldReturnAllFireStations() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 2);
    fireStationRepositoryImpl.save(fireStation1);
    fireStationRepositoryImpl.save(fireStation2);

    List<FireStation> result = fireStationRepositoryImpl.findAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }

  @Test
  void findByAddress_shouldReturnFireStation_whenCaseIsDifferent() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);

    Optional<FireStation> result = fireStationRepositoryImpl.findByAddress("123 MAIN st");

    assertTrue(result.isPresent());
    assertEquals("123 Main St", result.get().getAddress());
  }


  @Test
  void findByStationNumber_shouldReturnEmptyList_whenNoFireStationWithNumber() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    fireStationRepositoryImpl.save(fireStation);

    List<FireStation> result = fireStationRepositoryImpl.findByStationNumber(99);

    assertTrue(result.isEmpty());
  }
}