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
   * Initializes the repository by loading person data from a JSON file.
   * This method is called after the bean's properties have been set.
   */
  @PostConstruct
  public void init() {
    persons = jsonService.loadPersons();
  }

  /**
   * Retrieves all persons from the repository.
   *
   * @return a list of all persons.
   */
  @Override
  public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
    return persons.stream()
            .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                    person.getLastName().equalsIgnoreCase(lastName))
            .findFirst();
  }

  /**
   * Saves a person to the repository.
   *
   * @param person the person to save.
   * @return the saved Person entity.
   */
  @Override
  public Person save(Person person) {
    persons.add(person);
    return person;
  }

  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person to delete.
   * @param lastName  the last name of the person to delete.
   */
  @Override
  public void delete(String firstName, String lastName) {
    persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));
  }

  /**
   * Checks if a person exists by their first and last name.
   *
   * @param firstName the first name to check.
   * @param lastName  the last name to check.
   * @return true if a person with the specified first and last name exists, false otherwise.
   */
  @Override
  public boolean exists(String firstName, String lastName) {
    return persons.stream()
            .anyMatch(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                    person.getLastName().equalsIgnoreCase(lastName));
  }

  /**
   * Finds persons by their address.
   *
   * @param address the address to search for.
   * @return a list of Person entities with the specified address.
   */
  @Override
  public List<Person> findByAddress(String address) {
    return persons.stream()
            .filter(person -> person.getAddress().equalsIgnoreCase(address))
            .toList();
  }
}
