package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a registration of a student to a particular module.
 */
@Entity
public class Registration {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id")
  Student student;

  @ManyToOne
  @JoinColumn(name = "module_code")
  private Module module;

  /**
   * Constructs a new registration with the specified module.
   *
   * @param module The module associated with this registration
   */
  public Registration(Module module) {
    this.module = module;
  }

  /**
   * No arg constructor is required.
   */
  public Registration() {
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public Long getId() {
    return id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }
}
