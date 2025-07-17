package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Person entities.
 * This interface defines methods for CRUD operations on Person objects,
 * including finding, saving, deleting, and checking existence of persons.
 */
public interface PersonRepository {

  /**
   * Finds all persons in the repository.
   *
   * @return a list of all persons
   */
  List<Person> findAll();

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
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   */
  void delete(String firstName, String lastName);

  /**
   * Checks if a person exists by their first and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return true if the person exists, false otherwise
   */
  boolean exists(String firstName, String lastName);

  /**
   * Finds all persons living at a specific address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  List<Person> findByAddress(String address);
}
