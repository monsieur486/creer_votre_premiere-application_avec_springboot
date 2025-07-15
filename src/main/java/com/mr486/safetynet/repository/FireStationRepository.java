package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.FireStation;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing FireStation entities.
 * This interface defines methods for CRUD operations and querying fire stations by address and station number.
 */
public interface FireStationRepository {

  /**
   * Retrieves all fire stations.
   *
   * @return a list of all fire stations.
   */
  List<FireStation> findAll();

  /**
   * Finds a fire station by its address.
   *
   * @param address the address of the fire station.
   * @return an Optional containing the FireStation if found, or empty if not found.
   */
  Optional<FireStation> findByAddress(String address);

  /**
   * Saves a fire station entity.
   *
   * @param fireStation the fire station to save.
   * @return the saved FireStation entity.
   */
  FireStation save(FireStation fireStation);

  /**
   * Deletes a fire station by its address.
   *
   * @param address the address of the fire station to delete.
   */
  void delete(String address);

  /**
   * Finds fire stations by their station number.
   *
   * @param stationNumber the station number to search for.
   * @return a list of FireStation entities with the specified station number.
   */
  List<FireStation> findByStationNumber(int stationNumber);

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check.
   * @return true if a fire station exists at the specified address, false otherwise.
   */
  boolean exists(String address);
}
