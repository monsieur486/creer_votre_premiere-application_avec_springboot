package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.dto.DataBinding;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Component responsible for loading data from a JSON file.
 * Utilizes Jackson's ObjectMapper to deserialize JSON into a DataBinding object.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JsonDataReader {

  /**
   * The ObjectMapper instance used for JSON deserialization.
   * This is a required dependency injected via the constructor.
   */
  private final ObjectMapper mapper;

  /**
   * The path to the JSON file, injected from application properties.
   */
  @Value("${json.file.path}")
  String dataFilePath;

  /**
   * Loads data from a specified JSON file path and maps it to a DataBinding object.
   *
   * @return a DataBinding object containing the deserialized data
   * @throws RuntimeException if the file is not found or an error occurs while reading the file
   */
  public DataBinding loadData() {

    try {
      File file = new File(dataFilePath);
      return mapper.readValue(file, DataBinding.class);
    } catch (Exception e) {
      throw new RuntimeException("❌ Error reading json file:" + dataFilePath + " message: " + e.getMessage());
    }
  }

  public void setDataFilePath(String s) {
    this.dataFilePath = s;
    log.info("Data file path set to: {}", dataFilePath);
  }
}