package com.mr486.safetynet.repository.impl;

import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.tools.JsonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PersonRepositoryJsonTest {

  @Mock
  private JsonService jsonService;

  @InjectMocks
  private PersonRepositoryJson personRepositoryJson;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    List<Person> personList = List.of();
    when(jsonService.loadPersons()).thenReturn(personList);
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnPerson_whenExists() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);


    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");

    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnEmpty_whenNotExists() {
    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("Jane", "Smith");
    assertFalse(result.isPresent());
  }

  @Test
  void findAll_shouldReturnAllPersons() {
    Person person1 = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    Person person2 = new Person("Jane", "Smith", "address2", "city2", "zip2", "phone2", "email2");
    personRepositoryJson.save(person1);
    personRepositoryJson.save(person2);

    List<Person> result = personRepositoryJson.findAll();

    assertEquals(2, result.size());
    assertTrue(result.contains(person1));
    assertTrue(result.contains(person2));
  }
}