package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.request.FireStationDto;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireStationControllerTest {

  @Mock
  private FireStationService fireStationService;

  @InjectMocks
  private FireStationController fireStationController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addFireStation_shouldReturnCreatedFireStation_whenValidRequest() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    when(fireStationService.save(fireStation)).thenReturn(fireStation);

    ResponseEntity<FireStation> response = fireStationController.addFireStation(fireStation);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(fireStation, response.getBody());
  }

  @Test
  void updateFireStation_shouldReturnUpdatedFireStation_whenAddressExists() {
    FireStationDto fireStationDto = new FireStationDto(2);
    FireStation updatedFireStation = new FireStation("123 Main St", 2);
    when(fireStationService.update("123 Main St", fireStationDto)).thenReturn(updatedFireStation);

    ResponseEntity<FireStation> response = fireStationController.updateFireStation("123 Main St", fireStationDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(updatedFireStation, response.getBody());
  }

  @Test
  void deleteFireStation_shouldReturnNoContent_whenAddressExists() {
    doNothing().when(fireStationService).delete("123 Main St");

    ResponseEntity<Void> response = fireStationController.deleteFireStation("123 Main St");

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(fireStationService).delete("123 Main St");
  }

  @Test
  void getFireStationByAddress_shouldReturnFireStation_whenAddressExists() {
    FireStation fireStation = new FireStation("123 Main St", 1);
    when(fireStationService.findByAddress("123 Main St")).thenReturn(fireStation);

    ResponseEntity<FireStation> response = fireStationController.getFireStationByAddress("123 Main St");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(fireStation, response.getBody());
  }

  @Test
  void getFireStationByAddress_shouldReturnNotFound_whenAddressDoesNotExist() {
    when(fireStationService.findByAddress("456 Oak St")).thenThrow(new EntityNotFoundException("no fire station at 456 Oak St exists"));

    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> fireStationController.getFireStationByAddress("456 Oak St"));

    assertEquals("no fire station at 456 Oak St exists", exception.getMessage());
  }

  @Test
  void getAllFireStations_shouldReturnAllFireStations() {
    FireStation fireStation1 = new FireStation("123 Main St", 1);
    FireStation fireStation2 = new FireStation("456 Oak St", 2);
    when(fireStationService.findAll()).thenReturn(List.of(fireStation1, fireStation2));

    ResponseEntity<Iterable<FireStation>> response = fireStationController.getAllFireStations();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().iterator().hasNext());
  }

}