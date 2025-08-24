package uk.ac.ucl.comp0010.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ucl.comp0010.service.ModuleService;



/**
 * Controller class for managing modules. Handles HTTP requests for CRUD operations related to
 * modules.
 */
@RestController
@RequestMapping("/modules")
public class ModuleController {
  private final ModuleService moduleService;

  /**
   * Constructs a new ModuleController with the required services.
   *
   * @param moduleService service used to manage students
   */
  public ModuleController(ModuleService moduleService) {
    this.moduleService = moduleService;
  }

}
