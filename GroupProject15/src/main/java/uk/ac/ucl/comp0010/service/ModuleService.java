package uk.ac.ucl.comp0010.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.repository.ModuleRepository;

/**
 * Service class for handling business logic for modules. It manages CRUD operations and
 * interactions with the module repository.
 */
@Service
public class ModuleService {
  private final ModuleRepository moduleRepository;

  /**
   * Constructs a new ModuleService with the required repositories.
   *
   * @param moduleRepository repository for module operations
   */
  public ModuleService(ModuleRepository moduleRepository) {
    this.moduleRepository = moduleRepository;
  }

  /**
   * Adds a new module to the database.
   *
   * @param module The module to add.
   * @return The added module.
   */
  public Module addModule(Module module) {
    return moduleRepository.save(module);
  }

  /**
   * Retrieves a module by its code.
   *
   * @param code The unique code of the module.
   * @return The module with the specified code.
   * @throws NoRegistrationException If the module is not found.
   */
  public Module getModuleByCode(String code) throws NoRegistrationException {
    Optional<Module> module = moduleRepository.findById(code);
    if (module.isPresent()) {
      return module.get();
    }
    throw new NoRegistrationException();
  }

  /**
   * Updates an existing module's details.
   *
   * @param code   The unique code of the module to update.
   * @param module The updated module details.
   * @return The updated module.
   * @throws NoRegistrationException If the module is not found.
   */
  public Module updateModule(String code, Module module) throws NoRegistrationException {
    if (!moduleRepository.existsById(code)) {
      throw new NoRegistrationException();
    }
    module.setCode(code); // Ensure the code remains unchanged
    return moduleRepository.save(module);
  }

  /**
   * Deletes a module by its code.
   *
   * @param code The unique code of the module to delete.
   * @throws NoRegistrationException If the module is not found.
   */
  public void deleteModule(String code) throws NoRegistrationException {
    if (!moduleRepository.existsById(code)) {
      throw new NoRegistrationException();
    }
    moduleRepository.deleteById(code);
  }

  /**
   * Returns a list of all modules stored in the module repository.
   */
  public List<Module> getAllModules() {
    return StreamSupport.stream(moduleRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }
}