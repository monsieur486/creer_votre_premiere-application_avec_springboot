package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.tools.JsonService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryJson implements PersonRepository {

  private final JsonService jsonService;
  private List<Person> persons = new ArrayList<>();

  /**
   * Initializes the repository by loading fire station data from a JSON file.
   * This method is called after the bean is constructed, ensuring that the
   * fireStations list is populated with data before any operations are performed.
   */
  @PostConstruct
  public void init() {
    persons = jsonService.loadPersons();
  }

  @Override
  public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
    return persons.stream()
            .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                    person.getLastName().equalsIgnoreCase(lastName))
            .findFirst();
  }

  @Override
  public Person save(Person person) {
    persons.add(person);
    return person;
  }

  @Override
  public void delete(String firstName, String lastName) {
    persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));
  }

  @Override
  public boolean exists(String firstName, String lastName) {
    return persons.stream()
            .anyMatch(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                    person.getLastName().equalsIgnoreCase(lastName));
  }

  @Override
  public List<Person> findByAddress(String address) {
    return persons.stream()
            .filter(person -> person.getAddress().equalsIgnoreCase(address))
            .toList();
  }
}
