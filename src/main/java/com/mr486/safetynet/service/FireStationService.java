package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FireStationDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing FireStation entities.
 * This interface defines methods for CRUD operations and querying fire stations by address and station number.
 */
@Service
@RequiredArgsConstructor
public class FireStationService {

  private final FireStationRepository fireStationRepository;

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations.
   */
  List<FireStation> findAll() {
    return fireStationRepository.findAll();
  }

  /**
   * Finds a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return an Optional containing the FireStation if found, or empty if not found.
   */
  FireStation findByAddress(String address){
    return fireStationRepository.findByAddress(address)
        .orElseThrow(() -> new EntityAlreadyExistsException("Fire station not found at address: " + address));
  }

  /**
   * Saves a fire station entity.
   *
   * @param fireStation the fire station to save.
   * @return the saved FireStation entity.
   */
  FireStation save(FireStation fireStation){
    if (exists(fireStation.getAddress())) {
      throw new EntityAlreadyExistsException("Fire station already exists at address: " + fireStation.getAddress());
    }
    return fireStationRepository.save(fireStation);
  }

  /**
   * Deletes a fire station by its address.
   *
   * @param address the address of the fire station to delete.
   */
  void delete(String address){
    if (!exists(address)) {
      throw new EntityAlreadyExistsException("Fire station not found at address: " + address);
    }
    fireStationRepository.delete(address);
  }

  /**
   * Finds fire stations by their station number.
   *
   * @param stationNumber the station number to search for.
   * @return a list of FireStation entities with the specified station number.
   */
  List<FireStation> findByStationNumber(int stationNumber){
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
  FireStation update(String address, FireStationDto fireStationDto){
    if( !exists(address)) {
      throw new EntityAlreadyExistsException("Fire station not found at address: " + address);
    }
    FireStation updatedFireStation = new FireStation(
        address,
        fireStationDto.getStation()
    );
    fireStationRepository.delete(address);
    return fireStationRepository.save(updatedFireStation);
  }

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check.
   * @return true if a fire station exists at the specified address, false otherwise.
   */
  private boolean exists(String address) {
    return fireStationRepository.exists(address);
  }
}
