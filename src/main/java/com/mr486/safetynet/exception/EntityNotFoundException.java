package com.mr486.safetynet.exception;

/**
 * Exception thrown when a requested entity is not found.
 * This custom exception is used to indicate errors related to the absence
 * of an entity in the system.
 */
public class EntityNotFoundException extends RuntimeException {

  /**
   * Constructs a new EntityNotFoundException with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public EntityNotFoundException(String message) {
    super(message);
  }
}