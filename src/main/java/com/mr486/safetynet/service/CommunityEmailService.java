package com.mr486.safetynet.service;

import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for retrieving community emails based on city.
 * It fetches all persons in the specified city and collects their email addresses.
 */
@Service
@RequiredArgsConstructor
public class CommunityEmailService {

  private final PersonService personService;

  /**
   * Retrieves a set of email addresses for all persons in the specified city.
   *
   * @param city the city to search for persons
   * @return a set of email addresses of persons in the specified city
   */
  public Set<String> getEmailsByCity(String city) {
    List<Person> persons = personService.findByCity(city);
    return persons.stream()
            .map(Person::getEmail)
            .collect(Collectors.toSet());
  }
}