package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import com.mr486.safetynet.tools.JsonService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the FireStationRepository interface that uses JSON data for storage.
 * This class initializes
 * the repository with fire station data from a JSON file upon application startup.
 * It provides methods to find, save, delete, and check the existence of fire stations
 **/
@Repository
@RequiredArgsConstructor
public class FireStationRepositoryJson implements FireStationRepository {

  private final JsonService jsonService;
  private List<FireStation> fireStations = new ArrayList<>();

  /**
   * Initializes the repository by loading fire station data from a JSON file.
   * This method is called after the bean's properties have been set.
   */
  @PostConstruct
  public void init() {
    fireStations = jsonService.loadFireStations();
  }

  /**
   * Finds a fire station by its address.
   *
   * @param address the address of the fire station to find.
   * @return an Optional containing the FireStation if found, or empty if not found.
   */
  @Override
  public Optional<FireStation> findByAddress(String address) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
            .findFirst();
  }


  /**
   * Saves a fire station entity.
   * If the fire station already exists by address, it updates the existing one.
   * If it does not exist, it adds the new fire station to the list.
   *
   * @param fireStation the FireStation entity to save.
   *
   * @return the saved FireStation entity.
   */
  @Override
  public FireStation save(FireStation fireStation) {
    // Check if the fire station already exists by address
    Optional<FireStation> existingFireStation = findByAddress(fireStation.getAddress());
    // If it exists, update the existing one
    if (existingFireStation.isPresent()) {
      FireStation existing = existingFireStation.get();
      existing.setStation(fireStation.getStation());
      return existing;
    } else {
      // If it does not exist, add the new fire station
      fireStations.add(fireStation);
    }
    return fireStation;
  }

  /**
   * Deletes a fire station by its address.
   *
   * @param address the address of the fire station to delete.
   */
  @Override
  public void delete(String address) {
    fireStations.removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
  }

  /**
   * Finds fire stations by their station number.
   *
   * @param stationNumber the station number to search for.
   * @return a list of FireStation entities with the specified station number.
   */
  @Override
  public List<FireStation> findByStationNumber(int stationNumber) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getStation() == stationNumber)
            .toList();
  }

  /**
   * Checks if a fire station exists by its address.
   *
   * @param address the address to check.
   * @return true if a fire station exists at the specified address, false otherwise.
   */
  @Override
  public boolean exists(String address) {
    return fireStations.stream()
            .anyMatch(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
  }

  /**
   * Finds all fire stations in the repository.
   *
   * @return a list of all FireStation entities.
   */
  @Override
  public List<FireStation> findAll() {
    return new ArrayList<>(fireStations);
  }
}
