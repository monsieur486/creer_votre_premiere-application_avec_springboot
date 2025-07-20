package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.configuration.AppConfiguation;
import com.mr486.safetynet.dto.request.DataBindingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Component responsible for loading data from a JSON file.
 * Utilizes Jackson's ObjectMapper to deserialize JSON into a DataBindingDto object.
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
   * Loads data from a specified JSON file path and maps it to a DataBindingDto object.
   *
   * @return a DataBindingDto object containing the deserialized data
   * @throws RuntimeException if the file is not found or an error occurs while reading the file
   */
  public DataBindingDto loadData() {

    String dataFilePath = AppConfiguation.JSON_FILE_PATH;

    try {
      File file = new File(dataFilePath);
      return mapper.readValue(file, DataBindingDto.class);
    } catch (Exception e) {
      throw new RuntimeException("❌ Error reading json file:" + dataFilePath + " message: " + e.getMessage());
    }
  }
}