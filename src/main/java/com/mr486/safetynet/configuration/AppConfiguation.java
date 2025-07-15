package com.mr486.safetynet.configuration;

/**
 * Configuration class for the application.
 * This class contains constants used throughout the application,
 * such as file paths, date formats, and age thresholds.
 */
public class AppConfiguation {

  // Path to the JSON file containing data
  public static final String JSON_FILE_PATH = "data/data.json";

  // Date format used in the application
  public static final String DATE_FORMAT = "MM/dd/yyyy";

  // Age threshold to determine if a person is considered an adult
  public static final int ADULT_AGE = 18;
}
