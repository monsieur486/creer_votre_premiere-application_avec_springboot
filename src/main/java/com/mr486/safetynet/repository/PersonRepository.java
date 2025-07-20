package com.mr486.safetynet.repository;

import com.mr486.safetynet.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

  Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

  Person save(Person person);

  void delete(String firstName, String lastName);

  boolean exists(String firstName, String lastName);

  List<Person> findByAddress(String address);
}
