package uk.ac.ucl.comp0010.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Service class for managing grades.
 */
@Service
public class GradeService {
  private final GradeRepository gradeRepository;
  private final StudentRepository studentRepository;
  private final ModuleRepository moduleRepository;

  /**
   * Constructs a new GradeService with the required repositories.
   *
   * @param gradeRepository   repository for grade operations
   * @param studentRepository repository for student operations
   */
  public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository,
      ModuleRepository moduleRepository) {
    this.gradeRepository = gradeRepository;
    this.studentRepository = studentRepository;
    this.moduleRepository = moduleRepository;
  }

  /**
   * Retrieves all grades from the system.
   *
   * @return a List of all Grade objects in the system
   */
  public List<Grade> getGrades() {
    Iterable<Grade> iterable = gradeRepository.findAll();
    List<Grade> list = new ArrayList<>();
    iterable.forEach(list::add);
    return list;
  }

  /**
   * Adds a new grade for a student in a specific module. The method validates the student ID,
   * module code, and score before creating the grade.
   *
   * @param studentId  the ID of the student receiving the grade
   * @param moduleCode the code of the module for which the grade is being added
   * @param score      the numerical score (0-100) achieved by the student
   * @return the saved Grade object
   */
  public Grade addGrade(Long studentId, String moduleCode, Integer score) {
    Student student = studentRepository.findById(studentId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Student not found with id: " + studentId));
    Module module = moduleRepository.findById(moduleCode).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Module not found with code: " + moduleCode));

    if (score < 0 || score > 100) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Score must be between 0 and 100");
    }

    for (Grade g : gradeRepository.findAll()) {
      if (g.getStudent().getId().equals(studentId) && g.getModule().getCode().equals(moduleCode)) {
        gradeRepository.delete(g);
        break;
      }
    }
    Grade grade = new Grade(student, module, score);
    student.registerModule(module);
    student.addGrade(grade);

    return gradeRepository.save(grade);
  }
}