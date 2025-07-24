package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Person entities.
 * This interface defines methods for CRUD operations and querying persons by their first and last names,
 * as well as by their address.
 */
public interface PersonRepository {

  /**
   * Finds a person by their first and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return an Optional containing the found person, or empty if not found
   */
  Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Saves a person to the repository.
   *
   * @param person the person to save
   * @return the saved person
   */
  Person save(Person person);

  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person to delete
   * @param lastName  the last name of the person to delete
   */
  void delete(String firstName, String lastName);

  /**
   * Checks if a person exists by their first and last name.
   *
   * @param firstName the first name to check
   * @param lastName  the last name to check
   * @return true if a person with the specified first and last name exists, false otherwise
   */
  boolean exists(String firstName, String lastName);

  /**
   * Finds persons by their address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  List<Person> findByAddress(String address);

  /**
   * Finds all persons in the repository.
   *
   * @return a list of all persons
   */
  List<Person> findAll();

  /**
   * Finds persons by their last name.
   *
   * @param lastName the last name to search for
   * @return a list of persons with the specified last name
   */
  List<Person> findByLastName(String lastName);

  /**
   * Finds persons by their city.
   *
   * @param city the city to search for
   * @return a list of persons living in the specified city
   */
  List<Person> findByCity(String city);
}
