package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeTest {
  private Grade grade;
  private Module testModule;
  private Student testStudent;

  @BeforeEach
  void beforeEach() {
    testModule = new Module("COMP0010", "Software Engineering", true);
    testStudent = new Student(123L, "Bob", "Builder", "bobbuilder1", "bobthebuilder@test.com");
    grade = new Grade();
    grade = new Grade(testStudent, testModule, 85);
  }

  @Test
  void constructorAndGettersTest() {
    assertEquals(grade.getStudent(), testStudent);
    assertEquals(grade.getModule(), testModule);
    assertEquals(grade.getScore(), 85);
  }

  @Test
  void setModuleTest() {
    Module newModule = new Module("COMP0016", "Systems Engineering", true);
    grade.setModule(newModule);
    assertEquals(grade.getModule(), newModule);
  }

  @Test
  void setScoreTest() {
    grade.setScore(99);
    assertEquals(grade.getScore(), 99);
  }
}
