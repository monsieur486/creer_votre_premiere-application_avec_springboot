package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.request.PersonDto;
import com.mr486.safetynet.model.Person;

import java.util.List;

/**
 * Service interface for managing Person entities.
 * This interface defines methods for finding, saving, deleting, and updating persons,
 * as well as finding persons by their address.
 */
public interface PersonService {

  /**
   * Finds a person by their first and last name.
   *
   * @param firstName the first name of the person to find.
   * @param lastName  the last name of the person to find.
   * @return the found Person entity.
   */
  Person findByFirstNameAndLastName(String firstName, String lastName);

  /**
   * Saves a new person to the repository.
   *
   * @param person the person to save.
   * @return the saved Person entity.
   */
  Person save(Person person);


  /**
   * Deletes a person by their first and last name.
   *
   * @param firstName the first name of the person to delete.
   * @param lastName  the last name of the person to delete.
   */
  void delete(String firstName, String lastName);

  /**
   * Updates an existing person with the provided details.
   *
   * @param firstName the first name of the person to update.
   * @param lastName  the last name of the person to update.
   * @param personDto the new details for the person.
   * @return the updated Person entity.
   */
  Person update(String firstName, String lastName, PersonDto personDto);

  /**
   * Finds persons by their address.
   * @param address the address to search for persons.
   * @return a list of persons living at the specified address.
   */
  List<Person> findByAddress(String address);

  /**
   * Finds all persons in the repository.
   * @return a list of all Person entities.
   */
  List<Person> findAll();
}
