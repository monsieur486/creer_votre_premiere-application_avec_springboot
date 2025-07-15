package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.dto.DataBindingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class JsonDataReaderTest {

  private ObjectMapper objectMapper;
  private JsonDataReader jsonDataReader;

  @BeforeEach
  void setUp() {
    objectMapper = mock(ObjectMapper.class);
    jsonDataReader = new JsonDataReader(objectMapper);
  }

  @Test
  void loadData_shouldReturnDataBinding_whenJsonIsValid() throws Exception {
    DataBindingDto mockData = new DataBindingDto();
    String testPath = "src/test/resources/data.json";

    File file = new File(testPath);
    when(objectMapper.readValue(any(File.class), eq(DataBindingDto.class))).thenReturn(mockData);

    DataBindingDto result = jsonDataReader.loadData();

    assertNotNull(result);
    verify(objectMapper, times(1)).readValue(any(File.class), eq(DataBindingDto.class));
  }

  @Test
  void loadData_shouldThrowRuntimeException_whenIOExceptionOccurs() throws Exception {
    when(objectMapper.readValue(any(File.class), eq(DataBindingDto.class)))
            .thenThrow(new RuntimeException("Simulated read error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      jsonDataReader.loadData();
    });

    assertTrue(exception.getMessage().contains("Error reading json file"));
  }

}