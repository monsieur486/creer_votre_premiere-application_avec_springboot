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

/**
 * Implementation of the PersonRepository interface that uses JSON files for data storage.
 * This class provides methods to find, save, delete, and check the existence of persons.
 * It initializes the repository by loading person data from a JSON file upon bean creation.
 */
@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

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
  public Person save(Person person) {
    // Check if a person with the same first and last name already exists
    Optional<Person> existingPerson = findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
    if (existingPerson.isPresent()) {
      // If the person exists, update their address, city, and zip
      Person existing = existingPerson.get();
      existing.setAddress(person.getAddress());
      existing.setCity(person.getCity());
      existing.setZip(person.getZip());
      jsonService.savePersons(persons);
    } else {
      // If the person does not exist, add them to the list
      persons.add(person);
      jsonService.savePersons(persons);
    }
    return person;

  }

  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person to delete.
   * @param lastName  the last name of the person to delete.
   */
  public void delete(String firstName, String lastName) {
    persons.removeIf(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
            person.getLastName().equalsIgnoreCase(lastName));
    jsonService.savePersons(persons);
  }

  /**
   * Checks if a person exists by their first and last name.
   *
   * @param firstName the first name to check.
   * @param lastName  the last name to check.
   * @return true if a person with the specified first and last name exists, false otherwise.
   */
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
  public List<Person> findByAddress(String address) {
    return persons.stream()
            .filter(person -> person.getAddress().equalsIgnoreCase(address))
            .toList();
  }

  /**
   * Retrieves all persons from the repository.
   *
   * @return a list of all persons.
   */
  public List<Person> findAll() {
    return new ArrayList<>(persons);
  }

  /**
   * Finds persons by their last name.
   *
   * @param lastName the last name to search for.
   * @return a list of persons with the specified last name.
   */
  public List<Person> findByLastName(String lastName) {
    return persons.stream()
            .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
            .toList();
  }

  /**
   * Finds persons by their city.
   *
   * @param city the city to search for.
   * @return a list of persons living in the specified city.
   */
  public List<Person> findByCity(String city) {
    return persons.stream()
            .filter(person -> person.getCity().equalsIgnoreCase(city))
            .toList();
  }
}
