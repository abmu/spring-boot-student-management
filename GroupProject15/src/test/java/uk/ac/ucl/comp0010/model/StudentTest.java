package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;

public class StudentTest {
  private Student student;

  @BeforeEach
  void beforeEach() {
    student = new Student();
    student = new Student(123L, "Bob", "Builder", "bobbuilder1", "bobthebuilder@test.com");
  }

  @Test
  void constructorAndGettersTest() {
    assertEquals(student.getId(), 123);
    assertEquals(student.getFirstName(), "Bob");
    assertEquals(student.getLastName(), "Builder");
    assertEquals(student.getUsername(), "bobbuilder1");
    assertEquals(student.getEmail(), "bobthebuilder@test.com");
  }

  @Test
  void setIdTest() {
    student.setId(234L);
    assertEquals(student.getId(), 234);
  }

  @Test
  void setFirstNameTest() {
    student.setFirstName("Jack");
    assertEquals(student.getFirstName(), "Jack");
  }

  @Test
  void setLastNameTest() {
    student.setLastName("Bean");
    assertEquals(student.getLastName(), "Bean");
  }

  @Test
  void setUsernameTest() {
    student.setUsername("jackbean1");
    assertEquals(student.getUsername(), "jackbean1");
  }

  @Test
  void setEmailTest() {
    student.setEmail("jackbean1@test.com");
    assertEquals(student.getEmail(), "jackbean1@test.com");
  }

  @Test
  void computeAverageTest() throws NoGradeAvailableException, NoRegistrationException {
    assertThrows(NoGradeAvailableException.class, () -> student.computeAverage());

    Module module1 = new Module("COMP0010", "Software Engineering", true);
    student.registerModule(module1);
    Grade grade1 = new Grade(student, module1, 90);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    student.registerModule(module2);
    Grade grade2 = new Grade(student, module2, 80);

    student.addGrade(grade1);
    assertEquals(student.computeAverage(), 90.0f);

    student.addGrade(grade2);
    assertEquals(student.computeAverage(), 85.0f);
  }

  @Test
  void addGradeTest() throws NoRegistrationException, NoGradeAvailableException {
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    Grade grade1 = new Grade(student, module1, 90);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    Grade grade2 = new Grade(student, module2, 80);

//    assertThrows(NoRegistrationException.class, () -> student.addGrade(grade1));

    student.registerModule(module1);
    student.registerModule(module2);

    student.addGrade(grade1);
    assertEquals(student.getGrade(module1), grade1);

    student.addGrade(grade2);
    assertEquals(student.getGrade(module1), grade1);
    assertEquals(student.getGrade(module2), grade2);

    Grade grade3 = new Grade(student, module1, 95);
    student.addGrade(grade3);
    assertNotEquals(student.getGrade(module1), grade1);
    assertEquals(student.getGrade(module1), grade3);
  }

  @Test
  void getGradeTest() throws NoRegistrationException, NoGradeAvailableException {
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    Grade grade1 = new Grade(student, module1, 90);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    Grade grade2 = new Grade(student, module2, 80);
    Module module3 = new Module("COMP0009", "Logic", true);

    assertThrows(NoRegistrationException.class, () -> student.getGrade(module1));

    student.registerModule(module1);
    student.registerModule(module2);
    assertThrows(NoGradeAvailableException.class, () -> student.getGrade(module1));

    student.addGrade(grade1);
    assertEquals(student.getGrade(module1), grade1);

    student.addGrade(grade2);
    assertEquals(student.getGrade(module1), grade1);
    assertEquals(student.getGrade(module2), grade2);

    Grade grade3 = new Grade(student, module1, 95);
    student.addGrade(grade3);
    assertNotEquals(student.getGrade(module1), grade1);
    assertEquals(student.getGrade(module1), grade3);

    student.registerModule(module3);
    assertThrows(NoGradeAvailableException.class, () -> student.getGrade(module3));
  }

  @Test
  void registerModuleTest() {
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);

    assertEquals(student.getRegistrations().isEmpty(), true);

    student.registerModule(module1);
    assertEquals(student.getRegistrations().size(), 1);
    assertEquals(student.getRegistrations().containsKey(module1.getCode()), true);
    assertEquals(student.getRegistrations().containsKey(module2.getCode()), false);

    student.registerModule(module2);
    assertEquals(student.getRegistrations().size(), 2);
    assertEquals(student.getRegistrations().containsKey(module1.getCode()), true);
    assertEquals(student.getRegistrations().containsKey(module2.getCode()), true);
  }
}
