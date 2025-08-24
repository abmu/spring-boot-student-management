package uk.ac.ucl.comp0010.exception;

/**
 * An exception when there is no grade available.
 */
public class NoGradeAvailableException extends Exception {
  /**
   * Constructs a {@code NoGradeAvailableException} with a default error message.
   */
  public NoGradeAvailableException() {
    super("Grade is not available.");
  }

  /**
   * Constructs a {@code NoGradeAvailableException} with a custom error message.
   *
   * @param message The custom error message for this exception.
   */
  public NoGradeAvailableException(String message) {
    super(message);
  }
}
