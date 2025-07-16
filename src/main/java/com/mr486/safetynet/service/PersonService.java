package com.mr486.safetynet.service;

import com.mr486.safetynet.configuration.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.configuration.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

  private final PersonRepository personRepository;

  public List<Person> findAll() {
    log.info("Retrieving all persons");
    return  personRepository.findAll();
  }

  public Person findByFirstNameAndLastName(String firstName, String lastName) {
    if(!exists(firstName, lastName)) {
      log.error("Person not found: {} {}", firstName, lastName);
      throw peronNotFoundException(firstName, lastName);
    }

    log.info("Finding person: {} {}", firstName, lastName);
    return personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));
  }

  public Person save(Person person) {
    if (exists(person.getFirstName(), person.getLastName())) {
      log.error("Attempt to save a duplicate person: {} {}", person.getFirstName(), person.getLastName());
      throw personDuplicateException(person.getFirstName(), person.getLastName());
    }
    log.info("Saving person: {} {}", person.getFirstName(), person.getLastName());
    return personRepository.save(person);
  }

  private boolean exists(String firstName, String lastName) {
    return personRepository.exists(firstName, lastName);
  }

  private EntityNotFoundException peronNotFoundException(String firstName, String lastName) {
    return new EntityNotFoundException(
            "Person not found: " + firstName + " " + lastName);
  }

  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      log.error("Attempt to delete a non-existing person: {} {}", firstName, lastName);
      throw peronNotFoundException(firstName, lastName);
    }
    log.info("Deleting person: {} {}", firstName, lastName);
    personRepository.delete(firstName, lastName);
  }

  public List<Person> findByAddress(String address) {
    log.info("Finding persons by address: {}", address);
    return personRepository.findByAddress(address);
  }

  private EntityAlreadyExistsException personDuplicateException(String firstName, String lastName) {
    return new EntityAlreadyExistsException(
            "Person already exists: " + firstName + " " + lastName);
  }
}
