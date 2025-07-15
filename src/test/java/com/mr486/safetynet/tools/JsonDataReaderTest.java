package com.mr486.safetynet.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mr486.safetynet.dto.DataBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JsonDataReaderTest {

  @Mock
  private ObjectMapper mockMapper;

  private JsonDataReader jsonDataReader;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    jsonDataReader = new JsonDataReader(mockMapper);
    jsonDataReader.setDataFilePath("test.json");
  }

  @Test
  void loadData_shouldReturnDataBindingDtoWhenFileIsValid() throws Exception {
    DataBinding expectedData = new DataBinding();
    when(mockMapper.readValue(new File("test.json"), DataBinding.class)).thenReturn(expectedData);

    DataBinding result = jsonDataReader.loadData();

    assertNotNull(result);
    assertEquals(expectedData, result);
  }

  @Test
  void loadData_shouldThrowRuntimeExceptionWhenFileNotFound() throws Exception {
    when(mockMapper.readValue(new File("test.json"), DataBinding.class)).thenThrow(new RuntimeException("File not found"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> jsonDataReader.loadData());

    assertEquals("Error reading json file:test.json message: File not found", exception.getMessage());
  }

  @Test
  void loadData_shouldThrowRuntimeExceptionWhenJsonIsInvalid() throws Exception {
    when(mockMapper.readValue(new File("test.json"), DataBinding.class)).thenThrow(new RuntimeException("Invalid JSON"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> jsonDataReader.loadData());

    assertEquals("Error reading json file:test.json message: Invalid JSON", exception.getMessage());
  }
}