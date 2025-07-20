package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.request.FireStationDto;
import com.mr486.safetynet.model.FireStation;

import java.util.List;

public interface FireStationService {

  <Optional> FireStation findByAddress(String address);
  FireStation save(FireStation fireStation);
  void delete(String address);
  FireStation update(String address, FireStationDto fireStationDto);
  List<FireStation> findByStationNumber(int stationNumber);
}
