package com.mr486.safetynet.dto.request;

import com.mr486.safetynet.model.FireStation;
import com.mr486.safetynet.model.MedicalRecord;
import com.mr486.safetynet.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a data binding component that holds lists of persons, fire stations, and medical records.
 * This class is used to map JSON data to Java objects and utilizes Lombok annotations to reduce boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataBindingDto {

  /**
   * A list of persons.
   */
  List<Person> persons = new ArrayList<>();

  /**
   * A list of fire stations.
   */
  List<FireStation> firestations = new ArrayList<>();

  /**
   * A list of medical records.
   */
  List<MedicalRecord> medicalrecords = new ArrayList<>();

}