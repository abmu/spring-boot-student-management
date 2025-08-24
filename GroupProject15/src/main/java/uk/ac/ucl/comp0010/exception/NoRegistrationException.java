package uk.ac.ucl.comp0010.exception;

/**
 * An exception thrown if a user tries to access grades for an unregistered module.
 */
public class NoRegistrationException extends Exception {
  /**
   * Constructs a {@code NoRegistrationException} with a default error message.
   */
  public NoRegistrationException() {
    super("User is not registered with this module.");
  }
}
