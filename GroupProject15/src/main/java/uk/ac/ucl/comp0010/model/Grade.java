package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a grade assigned to a specific module. This class holds the related module, and the
 * score obtained in that module.
 */
@Entity
public class Grade {
  @Id
  @GeneratedValue
  private Long id;

  private Integer score;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "module_code")
  private Module module;

  /**
   * Constructs a new Grade with the specified module and score.
   *
   * @param student The student linked with this grade
   * @param module The module associated with this grade
   * @param score  The score achieved in the module
   */
  public Grade(Student student, Module module, Integer score) {
    this.student = student;
    this.module = module;
    this.score = score;
  }

  /**
   * No arg constructor is required.
   */
  public Grade() {
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Student getStudent() {
    return student;
  }
}
