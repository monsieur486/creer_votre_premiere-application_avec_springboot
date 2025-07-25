// src/test/java/com/mr486/safetynet/service/buisness/ChildAlertServiceTest.java
package com.mr486.safetynet.service.buisness;

import com.mr486.safetynet.dto.request.ChildAlertDto;
import com.mr486.safetynet.dto.request.HouseholdMemberDto;
import com.mr486.safetynet.dto.response.ChildAlertResponseDto;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import com.mr486.safetynet.service.MedicalRecordService;
import com.mr486.safetynet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChildAlertServiceTest {

  @Mock
  private PersonService personService;
  @Mock
  private MedicalRecordService medicalRecordService;

  @InjectMocks
  private ChildAlertService childAlertService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getChildrenByAddress_returnsChildrenWithHouseholdMembers() {
    String address = "1 rue de Paris";
    Person child = new Person("Jean", "Dupont", address, "Paris", "75000", "0102030405", "email@test.com");
    Person adult = new Person("Marie", "Dupont", address, "Paris", "75000", "0102030406", "marie@test.com");
    MedicalRecord childRecord = mock(MedicalRecord.class);
    MedicalRecord adultRecord = mock(MedicalRecord.class);

    when(personService.findByAddress(address)).thenReturn(List.of(child, adult));
    when(medicalRecordService.findByFirstNameAndLastName("Jean", "Dupont")).thenReturn(childRecord);
    when(medicalRecordService.findByFirstNameAndLastName("Marie", "Dupont")).thenReturn(adultRecord);
    when(medicalRecordService.isAdult(childRecord)).thenReturn(false);
    when(medicalRecordService.isAdult(adultRecord)).thenReturn(true);
    when(medicalRecordService.getAge(childRecord)).thenReturn(10);

    ChildAlertResponseDto response = childAlertService.getChildrenByAddress(address);

    assertNotNull(response);
    assertEquals(1, response.getChildren().size());
    ChildAlertDto childDto = response.getChildren().get(0);
    assertEquals("Jean", childDto.getFirstName());
    assertEquals(10, childDto.getAge());
    assertEquals(1, childDto.getHouseholdMembers().size());
    HouseholdMemberDto member = childDto.getHouseholdMembers().get(0);
    assertEquals("Marie", member.getFirstName());
  }

  @Test
  void getChildrenByAddress_returnsEmptyList_whenNoChildren() {
    String address = "2 rue vide";
    Person adult = new Person("Paul", "Martin", address, "Paris", "75000", "0102030407", "paul@test.com");
    MedicalRecord adultRecord = mock(MedicalRecord.class);

    when(personService.findByAddress(address)).thenReturn(List.of(adult));
    when(medicalRecordService.findByFirstNameAndLastName("Paul", "Martin")).thenReturn(adultRecord);
    when(medicalRecordService.isAdult(adultRecord)).thenReturn(true);

    ChildAlertResponseDto response = childAlertService.getChildrenByAddress(address);

    assertNotNull(response);
    assertTrue(response.getChildren().isEmpty());
  }

  @Test
  void getChildrenByAddress_skipsPersonWithoutMedicalRecord() {
    String address = "3 rue inconnue";
    Person unknown = new Person("Inconnu", "SansDossier", address, "Paris", "75000", "0102030408", "inconnu@test.com");

    when(personService.findByAddress(address)).thenReturn(List.of(unknown));
    when(medicalRecordService.findByFirstNameAndLastName("Inconnu", "SansDossier")).thenReturn(null);

    ChildAlertResponseDto response = childAlertService.getChildrenByAddress(address);

    assertNotNull(response);
    assertTrue(response.getChildren().isEmpty());
  }
}