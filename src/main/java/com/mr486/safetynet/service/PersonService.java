package com.mr486.safetynet.service;

import com.mr486.safetynet.dto.PersonDto;
import com.mr486.safetynet.exception.EntityAlreadyExistsException;
import com.mr486.safetynet.exception.EntityNotFoundException;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public List<Person> findAll() {
    return personRepository.findAll();
  }

  public Person findByFirstNameAndLastName(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }

    return personRepository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> peronNotFoundException(firstName, lastName));
  }

  public Person save(Person person) {
    if (exists(person.getFirstName(), person.getLastName())) {
      throw personDuplicateException(person.getFirstName(), person.getLastName());
    }
    return personRepository.save(person);
  }

  public void delete(String firstName, String lastName) {
    if (!exists(firstName, lastName)) {
      throw peronNotFoundException(firstName, lastName);
    }
    personRepository.delete(firstName, lastName);
  }

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

  public List<Person> findByAddress(String address) {
    return personRepository.findByAddress(address);
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
