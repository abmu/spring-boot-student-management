package uk.ac.ucl.comp0010.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.StudentNotFoundException;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service class responsible for handling business logic for students.
 * It manages CRUD operations and interactions with the student repository.
 */
@Service
public class StudentService {
  private final StudentRepository studentRepository;
  private final RegistrationRepository registrationRepository;
  private final GradeRepository gradeRepository;

  /**
   * Constructs a new StudentService with the required repositories.
   *
   * @param studentRepository repository for student operations
   */
  public StudentService(StudentRepository studentRepository,
                        RegistrationRepository registrationRepository,
                        GradeRepository gradeRepository) {
    this.studentRepository = studentRepository;
    this.registrationRepository = registrationRepository;
    this.gradeRepository = gradeRepository;
  }

  /**
   * Retrieves a student by their unique ID.
   *
   * @param id The ID of the student to retrieve.
   * @return The student object associated with the given ID.
   */
  public Student getStudentById(Long id) {
    Optional<Student> student = studentRepository.findById(id);
    if (student.isEmpty()) {
      throw new StudentNotFoundException("No student found with id-" + id);
    } else {
      return student.get();
    }
  }

  /**
   * Retrieves all students in the system.
   *
   * @return A list of all student objects.
   */
  public List<Student> getAllStudents() {
    return (List<Student>) studentRepository.findAll();
  }

  /**
   * Adds a new student to the system.
   *
   * @param student The student object to be added.
   */
  public Student addStudent(Student student) {
    return studentRepository.save(student);
  }

  /**
   * Updates an existing student's details.
   *
   * @param id             The ID of the student to update.
   * @param updatedStudent The student object containing updated information.
   */
  public Student updateStudent(Long id, Student updatedStudent) {
    Student student = getStudentById(id);
    student.setId(updatedStudent.getId());
    student.setFirstName(updatedStudent.getFirstName());
    student.setLastName(updatedStudent.getLastName());
    student.setUsername(updatedStudent.getUsername());
    student.setEmail(updatedStudent.getEmail());
    return studentRepository.save(student);
  }

  /**
   * Deletes a student based on their unique ID.
   *
   * @param id The ID of the student to delete.
   */

  @Transactional
  public void deleteStudent(Long id) {
    gradeRepository.deleteByStudentId(id);
    registrationRepository.deleteByStudentId(id);
    studentRepository.deleteById(id);
  }

  /**
   * Computes the average grade of a student by their ID.
   *
   * @param id The ID of the student.
   * @return The average grade as a float.
   * @throws NoGradeAvailableException if there are no grades available for the student.
   */
  public float computeAverage(Long id) throws NoGradeAvailableException {
    Student student = getStudentById(id);
    return student.computeAverage();
  }
}
