package uk.ac.ucl.comp0010.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class GradeServiceTest {
  @Autowired
  private GradeService gradeService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private ModuleService moduleService;

  @BeforeEach
  void setup() {
    Student student1 = new Student(1L, "Bob", "Builder", "bobbuilder", "bobbuilder@test.com");
    studentService.addStudent(student1);
    Student student2 = new Student(2L, "John", "Doe", "jdoe", "jdoe@company.com");
    studentService.addStudent(student2);
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    moduleService.addModule(module1);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    moduleService.addModule(module2);
    gradeService.addGrade(1L, "COMP0010", 85);
  }

  @Test
  void getGradesTest() {
    List<Grade> grades = gradeService.getGrades();
    assertEquals(1, grades.size());
    assertEquals("COMP0010", grades.get(0).getModule().getCode());
  }

  @Test
  void addGradeUpdateExistingTest() {
    gradeService.addGrade(1L, "COMP0010", 70);
    gradeService.addGrade(1L, "COMP0010", 90);
    List<Grade> grades = gradeService.getGrades();
    assertEquals(1, grades.size());
    assertEquals(90, grades.get(0).getScore());
  }

  @Test
  void addGradeSuccessTest() {
    gradeService.addGrade(1L, "COMP0016", 70);
    gradeService.addGrade(2L, "COMP0010", 80);
    List<Grade> grades = gradeService.getGrades();
    assertEquals(3, grades.size());
    assertEquals(70, grades.get(1).getScore());
  }

  @Test
  void addGradeStudentNotFoundTest() {
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> gradeService.addGrade(999L, "COMP0016", 70));

    assertEquals("Student not found with id: 999", exception.getReason());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void addGradeModuleNotFoundTest() {
    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
        () -> gradeService.addGrade(1L, "COMP9999", 100));

    assertEquals("Module not found with code: COMP9999", exception.getReason());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
  }

  @Test
  void addGradeInvalidScoreTest() {
    ResponseStatusException exception1 = assertThrows(ResponseStatusException.class,
        () -> gradeService.addGrade(1L, "COMP0016", -5));

    assertEquals("Score must be between 0 and 100", exception1.getReason());
    assertEquals(HttpStatus.BAD_REQUEST, exception1.getStatusCode());

    ResponseStatusException exception2 = assertThrows(ResponseStatusException.class,
        () -> gradeService.addGrade(1L, "COMP0016", 105));

    assertEquals("Score must be between 0 and 100", exception2.getReason());
    assertEquals(HttpStatus.BAD_REQUEST, exception2.getStatusCode());
  }
}
