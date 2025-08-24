package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTest {
  private Registration registration;
  private Module testModule;

  @BeforeEach
  void beforeEach() {
    testModule = new Module("COMP0010", "Software Engineering", true);
    registration = new Registration();
    registration = new Registration(testModule);
  }

  @Test
  void constructorAndGettersTest() {
    assertEquals(registration.getModule(), testModule);
  }

  @Test
  void setModuleTest() {
    Module newModule = new Module("COMP0016", "Systems Engineering", true);
    registration.setModule(newModule);
    assertEquals(registration.getModule(), newModule);
  }
}
