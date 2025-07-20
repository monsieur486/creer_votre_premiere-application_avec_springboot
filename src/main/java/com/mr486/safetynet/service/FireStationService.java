package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.FireStationDto;
import com.mr486.safetynet.model.FireStation;

import java.util.List;

public interface FireStationService {

  List<FireStation> findAll();
  public FireStation findByAddress(String address);
  public FireStation save(FireStation fireStation);
  public void delete(String address);
  public FireStation update(String address, FireStationDto fireStationDto);
  public List<FireStation> findByStationNumber(int stationNumber);
}
