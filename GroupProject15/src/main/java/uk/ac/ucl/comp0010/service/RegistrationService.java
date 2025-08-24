package uk.ac.ucl.comp0010.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.exception.StudentNotFoundException;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.RegistrationRepository;

/**
 * Service class responsible for handling business logic for registrations. It manages CRUD
 * operations and interactions with the registration repository.
 */
@Service
public class RegistrationService {
  private final RegistrationRepository registrationRepository;
  private final StudentService studentService;
  private final ModuleService moduleService;

  /**
   * Constructs a new RegistrationService with the required repositories and services.
   *
   * @param registrationRepository repository for registration operations
   * @param studentService         service used to manage students
   * @param moduleService          service used to manage modules
   */
  public RegistrationService(RegistrationRepository registrationRepository,
                             StudentService studentService, ModuleService moduleService) {
    this.registrationRepository = registrationRepository;
    this.studentService = studentService;
    this.moduleService = moduleService;
  }

  /**
   * Retrieves a registration by its unique ID.
   *
   * @param id The ID of the registration to retrieve.
   * @return The registration object associated with the given ID.
   * @throws NoRegistrationException if no registration is found with the given ID.
   */
  public Registration getRegistrationById(Long id) throws NoRegistrationException {
    Optional<Registration> registration = registrationRepository.findById(id);
    if (registration.isEmpty()) {
      throw new NoRegistrationException();
    }
    return registration.get();
  }

  /**
   * Retrieves all registrations in the system.
   *
   * @return A list of all registration objects.
   */
  public List<Registration> getAllRegistrations() {
    return (List<Registration>) registrationRepository.findAll();
  }

  /**
   * Adds a new registration to the system.
   *
   * @param studentId  The ID of the student to register.
   * @param moduleCode The code of the module to register the student to.
   * @throws StudentNotFoundException if the student does not exist.
   * @throws NoRegistrationException  if the module does not exist.
   */
  public void addRegistration(Long studentId, String moduleCode)
          throws StudentNotFoundException, NoRegistrationException {
    Student student = studentService.getStudentById(studentId);
    Module module = moduleService.getModuleByCode(moduleCode);

    Registration registration = new Registration(module);
    registration.setStudent(student);

    registrationRepository.save(registration);
  }

  /**
   * Updates an existing registration's module.
   *
   * @param id         The ID of the registration to update.
   * @param moduleCode The new module code to associate with the registration.
   * @throws NoRegistrationException if the registration or module is not found.
   */
  public void updateRegistration(Long id, String moduleCode) throws NoRegistrationException {
    Registration existingRegistration = getRegistrationById(id);
    Module module = moduleService.getModuleByCode(moduleCode);
    existingRegistration.setModule(module);
    registrationRepository.save(existingRegistration);
  }

  /**
   * Deletes a registration based on its unique ID.
   *
   * @param id The ID of the registration to delete.
   * @throws NoRegistrationException if no registration is found with the given ID.
   */
  public void deleteRegistration(Long id) throws NoRegistrationException {
    if (!registrationRepository.existsById(id)) {
      throw new NoRegistrationException();
    }
    registrationRepository.deleteById(id);
  }
}