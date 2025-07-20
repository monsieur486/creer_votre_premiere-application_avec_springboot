package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.dto.request.FireStationDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationServiceImplTest {

  @Mock
  private FireStationRepository fireStationRepository;

  @InjectMocks
  private FireStationServiceImpl fireStationServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByAddress_shouldReturnFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    when(fireStationRepository.exists("123 Main St")).thenReturn(true);
    when(fireStationRepository.findByAddress("123 Main St")).thenReturn(Optional.of(fireStation));

    FireStation result = fireStationServiceImpl.findByAddress("123 Main St");

    assertEquals("123 Main St", result.getAddress());
    assertEquals(1, result.getStation());
  }

  @Test
  void findByAddress_shouldThrowException_whenAddressDoesNotExist() {
    when(fireStationRepository.exists("456 Oak St")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> fireStationServiceImpl.findByAddress("456 Oak St"));

    assertEquals("no fire station at 456 Oak St exists", exception.getMessage());
  }

  @Test
  void save_shouldAddFireStation_whenAddressDoesNotExist() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    when(fireStationRepository.exists("123 Main St")).thenReturn(false);
    when(fireStationRepository.save(fireStation)).thenReturn(fireStation);

    FireStation result = fireStationServiceImpl.save(fireStation);

    assertEquals(fireStation, result);
  }

  @Test
  void save_shouldThrowException_whenAddressAlreadyExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    when(fireStationRepository.exists("123 Main St")).thenReturn(true);

    EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
            () -> fireStationServiceImpl.save(fireStation));

    assertEquals("a fire station at 123 Main St already exists", exception.getMessage());
  }

  @Test
  void delete_shouldRemoveFireStation_whenAddressExists() {
    when(fireStationRepository.exists("123 Main St")).thenReturn(true);

    fireStationServiceImpl.delete("123 Main St");

    verify(fireStationRepository).delete("123 Main St");
  }

  @Test
  void delete_shouldThrowException_whenAddressDoesNotExist() {
    when(fireStationRepository.exists("456 Oak St")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> fireStationServiceImpl.delete("456 Oak St"));

    assertEquals("no fire station at 456 Oak St exists", exception.getMessage());
  }

  @Test
  void update_shouldUpdateFireStation_whenAddressExists() {
    FireStation existingFireStation = new FireStation("123 Main St", 1);
    FireStationDto fireStationDto = new FireStationDto(2);
    FireStation updatedFireStation = new FireStation("123 Main St", 2);

    when(fireStationRepository.exists("123 Main St")).thenReturn(true);
    when(fireStationRepository.findByAddress("123 Main St")).thenReturn(Optional.of(existingFireStation));
    when(fireStationRepository.save(any(FireStation.class))).thenReturn(updatedFireStation);

    FireStation result = fireStationServiceImpl.update("123 Main St", fireStationDto);

    assertEquals("123 Main St", result.getAddress());
    assertEquals(2, result.getStation());
  }

  @Test
  void update_shouldThrowException_whenAddressDoesNotExist() {
    FireStationDto fireStationDto = new FireStationDto(2);
    when(fireStationRepository.exists("456 Oak St")).thenReturn(false);

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> fireStationServiceImpl.update("456 Oak St", fireStationDto));

    assertEquals("no fire station at 456 Oak St exists", exception.getMessage());
  }

  @Test
  void findByStationNumber_shouldReturnFireStations_whenStationNumberExists() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 1);
    when(fireStationRepository.findByStationNumber(1)).thenReturn(List.of(fireStation1, fireStation2));

    List<FireStation> result = fireStationServiceImpl.findByStationNumber(1);

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }

  @Test
  void findAll_shouldReturnAllFireStations() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 2);
    when(fireStationRepository.findAll()).thenReturn(List.of(fireStation1, fireStation2));

    List<FireStation> result = fireStationServiceImpl.findAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(fireStation1));
    assertTrue(result.contains(fireStation2));
  }
}