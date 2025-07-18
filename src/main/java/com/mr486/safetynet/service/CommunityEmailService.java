package com.mr486.safetynet.service;

import com.mr486.safetynet.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityEmailService {

  private final PersonService personService;

  public Set<String> getEmailsByCity(String city) {
    List<Person> persons = personService.findByCity(city);
    return persons.stream()
            .map(Person::getEmail)
            .collect(Collectors.toSet());
  }
}