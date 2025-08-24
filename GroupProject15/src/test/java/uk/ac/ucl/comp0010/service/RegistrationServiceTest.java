package uk.ac.ucl.comp0010.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.exception.StudentNotFoundException;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegistrationServiceTest {

  @Autowired
  private RegistrationService registrationService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private ModuleService moduleService;

  private Student student;
  private Module module;

  @BeforeEach
  void setUp() {
    // Clear existing registrations
    registrationService.getAllRegistrations().forEach(r -> {
      try {
        registrationService.deleteRegistration(r.getId());
      } catch (NoRegistrationException ignored) {
      }
    });

    // Add a student
    student = new Student(1L, "John", "Doe", "johndoe", "johndoe@example.com");
    studentService.addStudent(student);

    // Add a module
    module = new Module("COMP0010", "Software Engineering", true);
    moduleService.addModule(module);
  }

  @Test
  void testAddRegistration() throws StudentNotFoundException, NoRegistrationException {
    // Perform the registration
    registrationService.addRegistration(student.getId(), module.getCode());

    // Verify the registration exists
    List<Registration> registrations = registrationService.getAllRegistrations();
    assertEquals(1, registrations.size());

    Registration registration = registrations.get(0);
    assertNotNull(registration);
    assertEquals("COMP0010", registration.getModule().getCode());
    assertEquals("johndoe", registration.getStudent().getUsername());
  }

  @Test
  void testGetRegistrationById() throws StudentNotFoundException, NoRegistrationException {
    // Add and fetch the registration
    registrationService.addRegistration(student.getId(), module.getCode());
    List<Registration> registrations = registrationService.getAllRegistrations();
    Registration registration = registrations.get(0);

    Registration fetchedRegistration = registrationService.getRegistrationById(registration.getId());
    assertNotNull(fetchedRegistration);
    assertEquals("COMP0010", fetchedRegistration.getModule().getCode());
    assertEquals("johndoe", fetchedRegistration.getStudent().getUsername());
  }

  @Test
  void testUpdateRegistration() throws StudentNotFoundException, NoRegistrationException {
    // Add the registration
    registrationService.addRegistration(student.getId(), module.getCode());

    // Add a new module
    Module newModule = new Module("COMP0020", "Advanced Systems", true);
    moduleService.addModule(newModule);

    // Fetch the registration
    List<Registration> registrations = registrationService.getAllRegistrations();
    Registration registration = registrations.get(0);

    // Update the registration
    registrationService.updateRegistration(registration.getId(), newModule.getCode());

    // Verify the update
    Registration updatedRegistration = registrationService.getRegistrationById(registration.getId());
    assertEquals("COMP0020", updatedRegistration.getModule().getCode());
  }

  @Test
  void testDeleteRegistration() throws StudentNotFoundException, NoRegistrationException {
    // Add the registration
    registrationService.addRegistration(student.getId(), module.getCode());

    // Fetch the registration
    List<Registration> registrations = registrationService.getAllRegistrations();
    Registration registration = registrations.get(0);

    // Delete the registration
    registrationService.deleteRegistration(registration.getId());

    // Verify deletion
    assertThrows(NoRegistrationException.class, () -> registrationService.getRegistrationById(registration.getId()));
  }

  @Test
  void testGetAllRegistrations() throws StudentNotFoundException, NoRegistrationException {
    // Ensure no registrations initially
    assertEquals(0, registrationService.getAllRegistrations().size());

    // Add a registration
    registrationService.addRegistration(student.getId(), module.getCode());

    // Verify the registration is added
    List<Registration> registrations = registrationService.getAllRegistrations();
    assertEquals(1, registrations.size());
  }

  @Test
  void testDeleteNonExistentRegistration() {
    // Attempt to delete a non-existent registration
    assertThrows(NoRegistrationException.class, () -> registrationService.deleteRegistration(999L));
  }
}