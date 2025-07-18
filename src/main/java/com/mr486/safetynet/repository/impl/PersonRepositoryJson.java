package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import com.mr486.safetynet.tools.JsonDataReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PersonRepository interface that uses JSON data storage.
 * This class provides methods to manage Person entities, including finding,
 * saving, deleting, and checking existence of persons.
 */
@Repository
@RequiredArgsConstructor
public class PersonRepositoryJson implements PersonRepository {

  private final JsonDataReader jsonDataReader;
  private List<Person> persons;

  /**
   * Initializes the repository by loading fire station data from a JSON file.
   * This method is called after the bean is constructed, ensuring that the
   * fireStations list is populated with data before any operations are performed.
   */
  @PostConstruct
  public void init() {
    persons = new ArrayList<>();
    try {
      persons = jsonDataReader.loadData().getPersons();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load persons from JSON", e);
    }
  }

  /**
   * Returns the list of persons managed by this repository.
   *
   * @return a list of Person objects
   */
  @Override
  public List<Person> findAll() {
    return persons;
  }

  /**
   * Finds a person by their first and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return an Optional containing the found person, or empty if not found
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
   * @param person the person to save
   * @return the saved person
   */
  @Override
  public Person save(Person person) {
    persons.add(person);
    return person;
  }

  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   */
  @Override
  public void delete(String firstName, String lastName) {
    persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));
  }

  /**
   * Checks if a person exists by their first and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return true if the person exists, false otherwise
   */
  @Override
  public boolean exists(String firstName, String lastName) {
    return persons.stream()
            .anyMatch(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                    person.getLastName().equalsIgnoreCase(lastName));
  }

  /**
   * Finds all persons living at a specific address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  @Override
  public List<Person> findByAddress(String address) {
    return persons.stream()
            .filter(person -> person.getAddress().equalsIgnoreCase(address))
            .toList();
  }

  @Override
  public List<Person> findByLastName(String lastName) {
    return persons.stream()
            .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
            .toList();
  }
}
