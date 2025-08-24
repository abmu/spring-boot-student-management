package uk.ac.ucl.comp0010.exception;

/**
 * An exception thrown if a user tries to retrieve a student, using an invalid id.
 */
public class StudentNotFoundException extends RuntimeException {
  /**
   * Constructs a {@code StudentNotFoundException} with a custom error message.
   *
   * @param message The custom error message for this exception.
   */
  public StudentNotFoundException(String message) {
    super(message);
  }
}
