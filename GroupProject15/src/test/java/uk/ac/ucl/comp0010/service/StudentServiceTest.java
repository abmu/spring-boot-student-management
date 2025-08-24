package uk.ac.ucl.comp0010.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.StudentNotFoundException;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.model.Module;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceTest {
  @Autowired
  private StudentService studentService;

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private GradeService gradeService;

  private Student student;

  @BeforeEach
  void setUp() {
    studentService.getAllStudents().forEach(s -> studentService.deleteStudent(s.getId()));

    student = new Student(1L, "Bob", "Builder", "bobbuilder", "bobbuilder@test.com");
    studentService.addStudent(student);
  }

  @Test
  void testAddStudent() {
    Student newStudent = new Student(2L, "Mario", "Luigi", "marioluigi1", "mandl@test.com");
    studentService.addStudent(newStudent);
    assertEquals(2, studentService.getAllStudents().size());
  }

  @Test
  void testGetStudentById() {
    Student fetchedStudent = studentService.getStudentById(1L);
    assertNotNull(fetchedStudent);
    assertEquals("Bob", fetchedStudent.getFirstName());
    assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(2L));
  }

  @Test
  void testGetAllStudents() {
    assertEquals(1, studentService.getAllStudents().size());
    assertEquals(1, studentService.getAllStudents().get(0).getId());

  }

  @Test
  void testUpdateStudent() {
    student.setFirstName("Chris");
    studentService.updateStudent(1L, student);

    Student updatedStudent = studentService.getStudentById(1L);
    assertEquals("Chris", updatedStudent.getFirstName());
  }

  @Test
  @Transactional
  void testDeleteStudent() {
    studentService.deleteStudent(1L);
    assertEquals(0, studentService.getAllStudents().size());
  }

  @Test
  @Transactional
  void testComputeAverage() throws NoRegistrationException, NoGradeAvailableException {
    // Add modules and grades for the student
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    Module module3 = new Module("COMP0009", "Logic", true);
    moduleService.addModule(module1);
    moduleService.addModule(module2);
    moduleService.addModule(module3);

    registrationService.addRegistration(1L, "COMP0010");
    registrationService.addRegistration(1L, "COMP0016");
    registrationService.addRegistration(1L, "COMP0009");

    gradeService.addGrade(1L, "COMP0010", 72);
    gradeService.addGrade(1L, "COMP0016", 69);
    gradeService.addGrade(1L, "COMP0009", 66);

    // Test the computeAverage method
    float average = studentService.computeAverage(1L);
    assertEquals(69.0f, average, 0.001f); // Verify the average

    // Test exception when no grades are available
    Student newStudent = new Student(2L, "Alice", "Wonderland", "alice1", "alice@test.com");
    studentService.addStudent(newStudent);

    assertThrows(NoGradeAvailableException.class, () -> studentService.computeAverage(2L));
  }
}