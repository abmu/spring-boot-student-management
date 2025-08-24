package uk.ac.ucl.comp0010.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.ac.ucl.comp0010.exception.NoRegistrationException;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.service.RegistrationService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ModuleServiceTest {

  @Autowired
  private ModuleService moduleService;

  @Autowired
  private RegistrationService registrationService;


  @BeforeEach
  void setUp() {
    // Clear registrations
    registrationService.getAllRegistrations().forEach(r -> {
      try {
        registrationService.deleteRegistration(r.getId());
      } catch (NoRegistrationException ignored) {
      }
    });

    // Clear modules
    moduleService.getAllModules().forEach(m -> {
      try {
        moduleService.deleteModule(m.getCode());
      } catch (NoRegistrationException ignored) {
      }
    });

    Module module = new Module("COMP0010", "Software Engineering", true);
    moduleService.addModule(module);
  }

  @Test
  void testAddModule() {
    Module newModule = new Module("COMP0016", "Systems Engineering", true);
    Module savedModule = moduleService.addModule(newModule);

    assertNotNull(savedModule);
    assertEquals("COMP0016", savedModule.getCode());
    assertEquals("Systems Engineering", savedModule.getName());
    assertTrue(savedModule.isMnc());
  }

  @Test
  void testGetModuleByCode() throws NoRegistrationException {
    Module fetchedModule = moduleService.getModuleByCode("COMP0010");

    assertNotNull(fetchedModule);
    assertEquals("COMP0010", fetchedModule.getCode());
    assertEquals("Software Engineering", fetchedModule.getName());
  }

  @Test
  void testGetModuleByCodeNotFound() {
    assertThrows(NoRegistrationException.class, () -> moduleService.getModuleByCode("COMP9999"));
  }

  @Test
  void testUpdateModule() throws NoRegistrationException {
    Module updatedModule = new Module("COMP0010", "Advanced Software Engineering", false);
    Module savedModule = moduleService.updateModule("COMP0010", updatedModule);

    assertNotNull(savedModule);
    assertEquals("COMP0010", savedModule.getCode());
    assertEquals("Advanced Software Engineering", savedModule.getName());
    assertFalse(savedModule.isMnc());
  }

  @Test
  void testUpdateModuleNotFound() {
    Module nonExistentModule = new Module("COMP9999", "Nonexistent Module", true);
    assertThrows(NoRegistrationException.class, () -> moduleService.updateModule("COMP9999", nonExistentModule));
  }

  @Test
  void testDeleteModule() throws NoRegistrationException {
    moduleService.deleteModule("COMP0010");
    assertThrows(NoRegistrationException.class, () -> moduleService.getModuleByCode("COMP0010"));
  }

  @Test
  void testDeleteModuleNotFound() {
    assertThrows(NoRegistrationException.class, () -> moduleService.deleteModule("COMP9999"));
  }
}