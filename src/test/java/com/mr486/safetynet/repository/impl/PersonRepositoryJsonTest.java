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
    Person person = new Person(
            "John",
            "Doe",
            "address",
            "city",
            "zip",
            "phone",
            "email");
    personRepositoryJson.save(person);
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnPerson_whenExists() {
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
    Person person2 = new Person(
            "Jane",
            "Smith",
            "address2",
            "city2",
            "zip2",
            "phone2",
            "email2");
    personRepositoryJson.save(person2);

    List<Person> result = personRepositoryJson.findAll();

    assertEquals(2, result.size());
  }

  @Test
  void save_shouldUpdateExistingPerson_whenPersonAlreadyExists() {
    Person updated = new Person(
            "John",
            "Doe",
            "newAddress",
            "newCity",
            "newZip",
            "phone",
            "email");
    personRepositoryJson.save(updated);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    assertEquals("newAddress", result.get().getAddress());
    assertEquals("newCity", result.get().getCity());
    assertEquals("newZip", result.get().getZip());
  }

  @Test
  void save_shouldAddPerson_whenPersonDoesNotExist() {
    Person person2 = new Person(
            "Alice",
            "Wonderland",
            "address",
            "city",
            "zip",
            "phone",
            "email");
    personRepositoryJson.save(person2);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("Alice", "Wonderland");
    assertTrue(result.isPresent());
    assertEquals("Alice", result.get().getFirstName());
    assertEquals("Wonderland", result.get().getLastName());
  }

  @Test
  void delete_shouldRemovePerson_whenPersonExists() {
    personRepositoryJson.delete("John", "Doe");

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertFalse(result.isPresent());
  }

  @Test
  void exists_shouldReturnTrue_whenPersonExists() {
    assertTrue(personRepositoryJson.exists("John", "Doe"));
  }

  @Test
  void exists_shouldReturnFalse_whenPersonDoesNotExist() {
    assertFalse(personRepositoryJson.exists("Non", "Existent"));
  }

  @Test
  void findByAddress_shouldReturnPersonsWithGivenAddress() {
    Person person2 = new Person(
            "Jane",
            "Smith",
            "address",
            "city",
            "zip",
            "phone",
            "email");
    Person person3 = new Person(
            "Bob",
            "Builder",
            "Big Street",
            "city",
            "zip",
            "phone",
            "email");
    personRepositoryJson.save(person2);
    personRepositoryJson.save(person3);

    List<Person> result = personRepositoryJson.findByAddress("address");
    assertEquals(2, result.size());
  }

  @Test
  void findByAddress_shouldReturnEmptyList_whenNoPersonWithAddress() {
    List<Person> result = personRepositoryJson.findByAddress("No Address");
    assertTrue(result.isEmpty());
  }

  @Test
  void init_shouldLoadPersonsFromJsonService() {
    List<Person> loaded = List.of(
            new Person(
                    "John",
                    "Doe",
                    "address",
                    "city",
                    "zip",
                    "phone",
                    "email")
    );
    when(jsonService.loadPersons()).thenReturn(loaded);

    personRepositoryJson.init();

    List<Person> result = personRepositoryJson.findAll();
    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnEmpty_whenFirstNameDoesNotMatch() {
    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("Jane", "Doe");
    assertFalse(result.isPresent());
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnEmpty_whenLastNameDoesNotMatch() {
    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Smith");
    assertFalse(result.isPresent());
  }

  @Test
  void findByAddress_shouldReturnPerson_whenAddressCaseDiffers() {
    List<Person> result = personRepositoryJson.findByAddress("AdDResS");
    assertEquals(1, result.size());
  }

  @Test
  void exists_shouldReturnTrue_whenCaseIsDifferent() {
    assertTrue(personRepositoryJson.exists("joHN", "dOE"));
  }

  @Test
  void exists_shouldReturnTrue_shouldReturnEmpty_whenFirstNameDoesNotMatch() {
    assertFalse(personRepositoryJson.exists("Jane", "Doe"));
  }

  @Test
  void exists_shouldReturnTrue_shouldReturnEmpty_whenLastNameDoesNotMatch() {
    assertFalse(personRepositoryJson.exists("John", "Smith"));
  }

  @Test
  void delete_shouldReturnTrue_shouldReturnEmpty_whenFirstNameDoesNotMatch() {
    personRepositoryJson.delete("Jane", "Doe");

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    assertEquals("Doe", result.get().getLastName());
  }

  @Test
  void delete_shouldReturnEmpty_whenLastNameDoesNotMatch() {
    personRepositoryJson.delete("John", "Smith");

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    assertEquals("Doe", result.get().getLastName());
  }
}