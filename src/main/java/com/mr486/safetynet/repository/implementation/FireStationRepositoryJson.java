package com.mr486.safetynet.repository.implementation;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.repository.FireStationRepository;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FireStationRepositoryJson implements FireStationRepository {

  private final JsonDataReader jsonDataReader;
  private List<FireStation> fireStations;

  @PostConstruct
  public void init() {
    fireStations = new ArrayList<>();
    try {
      fireStations = jsonDataReader.loadData().getFirestations();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load fire stations from JSON", e);
    }
  }

  @Override
  public List<FireStation> findAll() {
    return fireStations;
  }

  @Override
  public Optional<FireStation> findByAddress(String address) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getAddress().equalsIgnoreCase(address))
            .findFirst();
  }

  @Override
  public FireStation save(FireStation fireStation) {
    fireStations.add(fireStation);
    return fireStation;

  }

  @Override
  public void delete(String address) {
    fireStations.removeIf(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
  }

  @Override
  public List<FireStation> findByStationNumber(int stationNumber) {
    return fireStations.stream()
            .filter(fireStation -> fireStation.getStation() == stationNumber)
            .toList();
  }

  @Override
  public boolean exists(String address) {
    return fireStations.stream()
            .anyMatch(fireStation -> fireStation.getAddress().equalsIgnoreCase(address));
  }
}
