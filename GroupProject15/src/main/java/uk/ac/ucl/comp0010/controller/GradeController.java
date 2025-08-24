package uk.ac.ucl.comp0010.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.service.GradeService;

/**
 * REST controller for managing grades.
 */
@RestController
@RequestMapping("/grades")
class GradeController {
  private final GradeService gradeService;

  /**
   * Constructs a new GradeController with the required services.
   *
   * @param gradeService service used to manage grades
   */
  public GradeController(GradeService gradeService) {
    this.gradeService = gradeService;
  }

  //  /**
  //   * Retrieves all grades added.
  //   *
  //   * @return List of {@link Grade} objects
  //   */
  //  @GetMapping
  //  public ResponseEntity<List<Grade>> getGrades() {
  //    List<Grade> grades = gradeService.getGrades();
  //    return ResponseEntity.ok(grades);
  //  }

  /**
   * Handles the creation of a new grade.
   *
   * @param params contains student_id, module_code, and score
   * @return saved {@link Grade} object
   */
  @PostMapping("/addGrade")
  public ResponseEntity<?> addGrade(@RequestBody Map<String, String> params) {
    if (!(params.containsKey("student_id") && params.containsKey("module_code")
        && params.containsKey("score"))) {
      return ResponseEntity.badRequest()
          .body("Missing required parameters: student_id, module_code, or score.");
    }

    long studentId;
    String moduleCode;
    int score;

    try {
      studentId = Long.parseLong(params.get("student_id"));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest()
          .body("Invalid student_id: must be a numeric value.");
    }

    moduleCode = params.get("module_code");
    if (moduleCode.trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid module_code: must not be empty.");
    }

    try {
      score = Integer.parseInt(params.get("score"));
    } catch (NumberFormatException e) {
      return ResponseEntity.badRequest()
          .body("Invalid score: must be an integer value.");
    }

    Grade grade = gradeService.addGrade(studentId, moduleCode, score);
    return ResponseEntity.ok(grade);
  }
}
