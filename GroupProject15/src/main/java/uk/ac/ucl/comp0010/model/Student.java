package uk.ac.ucl.comp0010.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.ac.ucl.comp0010.exception.NoGradeAvailableException;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;

/**
 * Represents a student in the grade management system. This class contains details about a
 * particular student including their id, first name, last name, username, email, and grades.
 */
@Entity
public class Student {
  @Id
  private Long id;

  private String firstName;
  private String lastName;
  private String username;
  private String email;

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Grade> grades;

  @OneToMany(mappedBy = "student")
  private Map<String, Registration> registrations;

  /**
   * Constructs a new Student with their specified details.
   *
   * @param id        The unique identifier for the student
   * @param firstName The first name of the student
   * @param lastName  The last name of the student
   * @param username  The username of the student
   * @param email     The email address of the student
   */
  public Student(Long id, String firstName, String lastName, String username, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.grades = new ArrayList<>();
    this.registrations = new HashMap<>();
  }

  /**
   * No arg constructor is required.
   */
  public Student() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Map<String, Registration> getRegistrations() {
    return registrations;
  }

  /**
   * Calculates the average score of all grades for a student.
   *
   * @return the average grade as a {@code float}.
   * @throws NoGradeAvailableException if there are no grades available for the student.
   */
  public float computeAverage() throws NoGradeAvailableException {
    if (grades.isEmpty()) {
      throw new NoGradeAvailableException("This student has no grades.");
    }

    float sum = 0.0f;
    for (Grade grade : grades) {
      sum += grade.getScore();
    }
    return sum / grades.size();
  }

  /**
   * Adds a grade for a module which the student is registered to.
   *
   * @param g The new grade which the student has achieved.
   */
  public void addGrade(Grade g) {
    Module m = g.getModule();
    //    if (!registrations.containsKey(m.getCode())) {
    //      throw new NoRegistrationException();
    //    }

    String code = m.getCode();
    for (Grade grade : grades) {
      if (grade.getModule().getCode().equals(code)) {
        grades.remove(grade);
        break;
      }
    }
    grades.add(g);
  }

  /**
   * Gets the grade for a specific module.
   *
   * @param m The module for which the grade is requested.
   * @return the {@code Grade} object associated with the specified module.
   * @throws NoRegistrationException   if the student is not registered for the specified module.
   * @throws NoGradeAvailableException if there is no grade available for the specified module.
   */
  public Grade getGrade(Module m) throws NoRegistrationException, NoGradeAvailableException {
    if (!registrations.containsKey(m.getCode())) {
      throw new NoRegistrationException();
    }

    String code = m.getCode();
    for (Grade grade : grades) {
      if (grade.getModule().getCode().equals(code)) {
        return grade;
      }
    }
    throw new NoGradeAvailableException();
  }

  public void registerModule(Module m) {
    Registration registration = new Registration(m);
    registrations.put(m.getCode(), registration);
  }
}
