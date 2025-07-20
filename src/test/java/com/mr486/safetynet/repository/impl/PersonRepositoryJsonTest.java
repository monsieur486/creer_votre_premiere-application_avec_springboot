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

  @Test
  void save_shouldUpdateExistingPerson_whenPersonAlreadyExists() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    Person updated = new Person("John", "Doe", "newAddress", "newCity", "newZip", "phone", "email");
    personRepositoryJson.save(updated);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    assertEquals("newAddress", result.get().getAddress());
    assertEquals("newCity", result.get().getCity());
    assertEquals("newZip", result.get().getZip());
  }

  @Test
  void save_shouldAddPerson_whenPersonDoesNotExist() {
    Person person = new Person("Alice", "Wonderland", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("Alice", "Wonderland");
    assertTrue(result.isPresent());
    assertEquals("Alice", result.get().getFirstName());
    assertEquals("Wonderland", result.get().getLastName());
  }

  @Test
  void delete_shouldRemovePerson_whenPersonExists() {
    Person person = new Person("Bob", "Builder", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    personRepositoryJson.delete("Bob", "Builder");

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("Bob", "Builder");
    assertFalse(result.isPresent());
  }

  @Test
  void exists_shouldReturnTrue_whenPersonExists() {
    Person person = new Person("Charlie", "Chaplin", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    assertTrue(personRepositoryJson.exists("Charlie", "Chaplin"));
  }

  @Test
  void exists_shouldReturnFalse_whenPersonDoesNotExist() {
    assertFalse(personRepositoryJson.exists("Non", "Existent"));
  }

  @Test
  void findByAddress_shouldReturnPersonsWithGivenAddress() {
    Person person1 = new Person("John", "Doe", "123 Street", "city", "zip", "phone", "email");
    Person person2 = new Person("Jane", "Smith", "123 Street", "city", "zip", "phone", "email");
    Person person3 = new Person("Bob", "Builder", "456 Avenue", "city", "zip", "phone", "email");
    personRepositoryJson.save(person1);
    personRepositoryJson.save(person2);
    personRepositoryJson.save(person3);

    List<Person> result = personRepositoryJson.findByAddress("123 Street");
    assertEquals(2, result.size());
    assertTrue(result.contains(person1));
    assertTrue(result.contains(person2));
  }

  @Test
  void findByAddress_shouldReturnEmptyList_whenNoPersonWithAddress() {
    List<Person> result = personRepositoryJson.findByAddress("No Address");
    assertTrue(result.isEmpty());
  }

  @Test
  void init_shouldLoadPersonsFromJsonService() {
    List<Person> loaded = List.of(
            new Person("John", "Doe", "address", "city", "zip", "phone", "email")
    );
    when(jsonService.loadPersons()).thenReturn(loaded);

    personRepositoryJson.init();

    List<Person> result = personRepositoryJson.findAll();
    assertEquals(1, result.size());
    assertEquals("John", result.get(0).getFirstName());
  }

  @Test
  void save_shouldNotUpdatePhoneOrEmail_whenPersonAlreadyExists() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "oldPhone", "oldEmail");
    personRepositoryJson.save(person);

    Person updated = new Person("John", "Doe", "newAddress", "newCity", "newZip", "newPhone", "newEmail");
    personRepositoryJson.save(updated);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("John", "Doe");
    assertTrue(result.isPresent());
    // Les champs phone et email ne doivent pas être modifiés
    assertEquals("oldPhone", result.get().getPhone());
    assertEquals("oldEmail", result.get().getEmail());
  }

  @Test
  void findByFirstNameAndLastName_shouldReturnPerson_whenCaseIsDifferent() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    Optional<Person> result = personRepositoryJson.findByFirstNameAndLastName("JOHn", "dOE");

    assertTrue(result.isPresent());
    assertEquals("John", result.get().getFirstName());
    assertEquals("Doe", result.get().getLastName());
  }

  @Test
  void exists_shouldReturnTrue_whenCaseIsDifferent() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    assertTrue(personRepositoryJson.exists("joHN", "dOE"));
  }

  @Test
  void delete_shouldRemovePerson_whenCaseIsDifferent() {
    Person person = new Person("John", "Doe", "address", "city", "zip", "phone", "email");
    personRepositoryJson.save(person);

    personRepositoryJson.delete("joHN", "dOE");

    assertFalse(personRepositoryJson.exists("John", "Doe"));
  }

}