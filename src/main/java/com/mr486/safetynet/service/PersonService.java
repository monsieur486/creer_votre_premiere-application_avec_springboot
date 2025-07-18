package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Person entities.
 * Provides methods to find, save, update, and delete persons.
 */
@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  /**
   * Retrieves all persons.
   *
   * @return a list of all persons.
   */
  public List<Person> findAll() {
    return personRepository.findAll();
  }

  /**
   * Finds a person by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @return the Person if found
   * @throws EntityNotFoundException if the person does not exist
   */
  public Person findByFirstNameAndLastName(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }

    return personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));
  }

  /**
   * Saves a new person.
   *
   * @param person the person to save
   * @return the saved Person entity
   * @throws EntityAlreadyExistsException if the person already exists
   */
  public Person save(Person person) {
    if (exists(person.getFirstName(), person.getLastName())) {
      throw personDuplicateException(person.getFirstName(), person.getLastName());
    }
    return personRepository.save(person);
  }

  /**
   * Deletes a person by first name and last name.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @throws EntityNotFoundException if the person does not exist
   */
  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }
    personRepository.delete(firstName, lastName);
  }

  /**
   * Updates an existing person.
   *
   * @param firstName the first name of the person
   * @param lastName  the last name of the person
   * @param personDto the DTO containing updated person data
   * @return the updated Person entity
   * @throws EntityNotFoundException if the person does not exist
   */
  public Person update(String firstName, String lastName, PersonDto personDto) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }

    Person existingPerson = personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));

    Person updatedPerson = personDto.toPerson(existingPerson.getFirstName(), existingPerson.getLastName());
    personRepository.delete(firstName, lastName);
    return personRepository.save(updatedPerson);
  }

  /**
   * Finds persons by address.
   *
   * @param address the address to search for
   * @return a list of persons living at the specified address
   */
  public List<Person> findByAddress(String address) {
    return personRepository.findByAddress(address);
  }

  /**
   * Finds persons by last name.
   *
   * @param lastName the last name to search for
   * @return a list of persons with the specified last name
   */
  public List<Person> findByLastName(String lastName) {
    return personRepository.findByLastName(lastName);
  }

  /**
   * Finds persons by city.
   *
   * @param city the city to search for
   * @return a list of persons living in the specified city
   */
  public List<Person> findByCity(String city) {
    return personRepository.findByCity(city);
  }

  // Private methods for exception handling and existence checks

  private EntityAlreadyExistsException personDuplicateException(String firstName, String lastName) {
    return new EntityAlreadyExistsException(
            "Person " + firstName + " " + lastName + " already exists");
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException(
            "Person " + firstName + " " + lastName + " not exists");
  }

  private boolean exists(String firstName, String lastName) {
    return personRepository.exists(firstName, lastName);
  }
}
