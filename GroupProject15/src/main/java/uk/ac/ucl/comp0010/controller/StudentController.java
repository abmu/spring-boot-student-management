package uk.ac.ucl.comp0010.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.StudentRepository;
import uk.ac.ucl.comp0010.service.StudentService;

/**
 * Controller class for managing students. Handles HTTP requests for CRUD operations related to
 * students.
 */
@RestController
@RequestMapping("/students")
public class StudentController {
  private final StudentService studentService;

  /**
   * Constructs a new StudentController with the required services.
   *
   * @param studentService service used to manage students
   */
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  ///**
  // * Gets a student by their unique ID.
  //   *
  //   * @param id The ID of the student to retrieve.
  //   * @return The student associated with the given ID.
  //   */
  //@GetMapping("/{id}")
  //public Student getStudent(@PathVariable Long id) {
  //return studentService.getStudentById(id);
  // }

  ///**
  // * //   * Gets all the students in the system. //   * //   * @return A list of all students. //
  // */
  //@GetMapping("/students")
  //public List<Student> getAllStudents() {
  //  return studentService.getAllStudents();
  //}

  //  /**
  //   * //   * Creates a new student and adds them to the system. //   * //   * @param student The
  //   * student object to be created. //
  //   * @return A response entity with a 201 Created status. //
  //   */
  //  @PostMapping("/students")
  //  public ResponseEntity<Student> addStudent(@RequestBody Student student) {
  //    Student createdStudent = studentService.addStudent(student);
  //    return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
  //  }

  //  /**
  //   * Updates an existing student's information based on their ID.
  //   * @param id      The ID of
  //   * the student to update. //   * @param student
  //   The student object containing updated information.
  //   * //
  //   */
  //  @PutMapping("{id}")
  //  public ResponseEntity<Student> updateStudent(@PathVariable Long id,
  //      @RequestBody Student student) {
  //    Student updatedStudent = studentService.updateStudent(id, student);
  //    return ResponseEntity.ok(updatedStudent);
  //  }

  //  /**
  //   * Deletes a student based on their unique ID.
  //   *
  //   * @param id The ID of the student to delete.
  //   */
  //  @DeleteMapping("/{id}")
  //  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
  //    studentService.deleteStudent(id);
  //    Optional<Student> student = studentRepository.findById(id);
  //    if (student.isEmpty()) {
  //      return ResponseEntity.notFound().build();
  //
  //    }
  //    return ResponseEntity.noContent().build();
  //  }
}
