package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.dto.DataBinding;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Component responsible for saving data to a JSON file.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JsonDataWriter {

  private final ObjectMapper objectMapper;

  @Value("${app.json.path:data.json}")
  private String jsonFilePath;

  /**
   * Saves the given data into the configured JSON file.
   *
   * @param data the DataBinding object to write
   */
  public void saveData(DataBinding data) {
    try {
      File file = new File(jsonFilePath);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
      log.info("Data written to file: {}", jsonFilePath);
    } catch (IOException e) {
      log.error("Failed to write to file: {}", jsonFilePath, e);
      throw new RuntimeException("❌ Error writing json file: " + jsonFilePath, e);
    }
  }
}
