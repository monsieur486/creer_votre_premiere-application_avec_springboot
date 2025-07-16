package com.mr486.safetynet.exception;

/**
 * Exception thrown when an entity that is being created already exists.
 * This is a custom runtime exception used to indicate conflicts during entity creation.
 */
public class EntityAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new EntityAlreadyExistsException with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public EntityAlreadyExistsException(String message) {
    super(message);
  }
}