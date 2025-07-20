package com.mr486.safetynet.service.impl;

import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.dto.request.FireStationDto;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import com.mr486.safetynet.service.FireStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing FireStation entities.
 * This interface defines methods for CRUD operations and querying fire stations by address and station number.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FireStationServiceImpl implements FireStationService {

  private final FireStationRepository fireStationRepository;

  /**
   * Finds a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return an Optional containing the FireStation if found, or empty if not found.
   */
  public FireStation findByAddress(String address){
    if( !exists(address)) {
      log.error("Fire station not found at address: {}", address);
      throw fireStationNotFoundException(address);
    }
    log.info("Finding fire station at address: {}", address);
    return fireStationRepository.findByAddress(address)
        .orElseThrow(() -> fireStationNotFoundException(address));
  }

  /**
   * Saves a fire station entity.
   *
   * @param fireStation the fire station to save.
   * @return the saved FireStation entity.
   */
  public FireStation save(FireStation fireStation){
    if (exists(fireStation.getAddress())) {
      log.error("Attempt to save a duplicate fire station at address: {}", fireStation.getAddress());
      throw fireStationDuplicateException(fireStation.getAddress());
    }
    log.info("Saving fire station at address: {}", fireStation.getAddress());
    return fireStationRepository.save(fireStation);
  }

  /**
   * Deletes a fire station by its address.
   *
   * @param address the address of the fire station to delete.
   */
  public void delete(String address){
    if (!exists(address)) {
      log.error("Attempt to delete a non-existing fire station at address: {}", address);
      throw fireStationNotFoundException(address);
    }
    log.info("Deleting fire station at address: {}", address);
    fireStationRepository.delete(address);
  }

  /**
   * Finds fire stations by their station number.
   *
   * @param stationNumber the station number to search for.
   * @return a list of FireStation entities with the specified station number.
   */
  public List<FireStation> findByStationNumber(int stationNumber){
    log.info("Finding fire stations with station number: {}", stationNumber);
    return fireStationRepository.findByStationNumber(stationNumber);
  }

  /**
   * Updates a fire station at the specified address with the provided data transfer object.
   *
   * @param address the address of the fire station to update.
   * @param fireStationDto the data transfer object containing updated fire station information.
   *
   * @return an Optional containing the updated FireStation if the update was successful, or empty if not found.
   */
  public FireStation update(String address, FireStationDto fireStationDto){
    if(!exists(address)) {
      log.error("Attempt to update a non-existing fire station at address: {}", address);
      throw fireStationNotFoundException(address);
    }
    FireStation existingFireStation = findByAddress(address);
    log.info("Updating fire station at address: {}", address);
    FireStation updatedFireStation = FireStation.builder()
        .address(existingFireStation.getAddress())
        .station(fireStationDto.getStation())
        .build();
    return fireStationRepository.save(updatedFireStation);
  }

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all FireStation entities.
   */
  public List<FireStation> findAll() {
    return fireStationRepository.findAll();
  }


  private boolean exists(String address) {
    return fireStationRepository.exists(address);
  }

  private EntityNotFoundException fireStationNotFoundException(String address) {
    return new EntityNotFoundException("no fire station at " + address + " exists");
  }

  private EntityAlreadyExistsException fireStationDuplicateException(String address) {
    return new EntityAlreadyExistsException("a fire station at " + address + " already exists");
  }
}
