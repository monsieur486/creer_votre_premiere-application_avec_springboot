package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.request.FireStationDto;
import com.mr486.safetynet.model.FireStation;

import java.util.List;

/**
 * Service interface for managing fire stations.
 * This interface defines methods for finding, saving, deleting, and updating fire stations,
 * as well as finding fire stations by their station number.
 */
public interface FireStationService {


  /**
   * Finds a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return an Optional containing the FireStation if found, or empty if not found.
   */
  FireStation findByAddress(String address);

  /**
   * Saves a new fire station or updates an existing one.
   *
   * @param fireStation the fire station to save
   * @return the saved fire station
   */
  FireStation save(FireStation fireStation);

  /**
   * Deletes a fire station by its address.
   *
   * @param address the address of the fire station to delete
   */
  void delete(String address);

  /**
   * Updates an existing fire station with the provided details.
   *
   * @param address        the address of the fire station to update
   * @param fireStationDto the new details for the fire station
   * @return the updated fire station
   */
  FireStation update(String address, FireStationDto fireStationDto);

  /**
   * Finds fire stations by their station number.
   *
   * @param stationNumber the station number to search for
   * @return a list of fire stations with the specified station number
   */
  List<FireStation> findByStationNumber(int stationNumber);

  /**
   * Finds all fire stations in the repository.
   *
   * @return a list of all fire stations
   */
  List<FireStation> findAll();
}
