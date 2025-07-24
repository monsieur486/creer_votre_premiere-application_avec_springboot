package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.FireStationService;
import com.mr486.safetynet.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for retrieving phone numbers of persons covered by a specific fire station.
 * It fetches all addresses associated with the fire station and collects phone numbers
 * of persons living at those addresses.
 */
@Service
@RequiredArgsConstructor
public class PhoneAlertService {

  private final FireStationService fireStationService;
  private final PersonService personService;

  /**
   * Retrieves a set of phone numbers for all persons covered by the specified fire station.
   *
   * @param stationNumber the number of the fire station
   * @return a set of phone numbers of persons covered by the fire station
   */
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
