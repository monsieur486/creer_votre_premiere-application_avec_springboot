package com.mr486.safetynet.service;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhoneAlertService {

  private final FireStationService fireStationService;
  private final PersonService personService;

  public Set<String> getPhonesByStation(int stationNumber) {
    List<String> addresses = fireStationService.findByStationNumber(stationNumber)
            .stream()
            .map(FireStation::getAddress)
            .toList();

    return addresses.stream()
            .flatMap(address -> personService.findByAddress(address).stream())
            .map(Person::getPhone)
            .collect(Collectors.toSet());
  }
}
