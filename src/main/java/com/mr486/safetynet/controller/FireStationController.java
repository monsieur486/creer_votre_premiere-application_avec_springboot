package com.mr486.safetynet.controller;

import com.mr486.safetynet.dto.FireStationDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.service.FireStationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing FireStation-related operations.
 * Provides endpoints for CRUD operations on FireStation entities.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/firestation")
public class FireStationController {

  private final FireStationService fireStationService;

  /**
   * Adds a new fire station.
   *
   * @param fireStation The FireStation object to be added. Must be valid.
   */
  @PostMapping(path = "", produces = "application/json")
  public ResponseEntity<FireStation> addFireStation(@RequestBody @Valid FireStation fireStation) {
    FireStation savedFireStation = fireStationService.save(fireStation);
    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(savedFireStation);
  }

  /**
   * Updates an existing fire station.
   *
   * @param address The address of the fire station to be updated.
   * @param fireStationDto The FireStationDto containing the updated information.
   *
   * @return ResponseEntity containing the updated FireStation object.
   */
  @PutMapping(path = "/{address}", produces = "application/json")
  public ResponseEntity<FireStation> updateFireStation(
          @PathVariable String address,
          @RequestBody @Valid FireStationDto fireStationDto) {
    FireStation updatedFireStation = fireStationService.update(address, fireStationDto);
    return ResponseEntity.ok(updatedFireStation);
  }

  /**
   * Deletes a fire station by its address.
   *
   * @param address The address of the fire station to be deleted.
   * @return ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping(path = "/{address}", produces = "application/json")
  public ResponseEntity<Void> deleteFireStation(@PathVariable String address) {
    fireStationService.delete(address);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Retrieves a fire station by its address.
   *
   * @param address The address of the fire station to be retrieved.
   * @return ResponseEntity containing the FireStation object if found, or NOT_FOUND if not found.
   */
  @GetMapping(path = "/{address}", produces = "application/json")
  public ResponseEntity<FireStation> getFireStationByAddress(@PathVariable String address) {
    FireStation fireStation = fireStationService.findByAddress(address);
    return ResponseEntity.ok(fireStation);
  }

  /**
   * Retrieves all fire stations.
   *
   * @return ResponseEntity containing a list of all FireStation objects.
   */
  @GetMapping(path = "/all", produces = "application/json")
  public ResponseEntity<Iterable<FireStation>> getAllFireStations() {
    Iterable<FireStation> fireStations = fireStationService.findAll();
    return ResponseEntity.ok(fireStations);
  }

}
