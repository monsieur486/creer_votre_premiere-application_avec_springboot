package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationRepository {
  List<FireStation> findAll();

  Optional<FireStation> findByAddress(String address);

  FireStation save(FireStation fireStation);

  void delete(String address);

  List<FireStation> findByStationNumber(int stationNumber);

  boolean exists(String address);
}
