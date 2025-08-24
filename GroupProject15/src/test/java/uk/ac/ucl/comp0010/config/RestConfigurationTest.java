package uk.ac.ucl.comp0010.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

@SpringBootTest
class RestConfigurationTest {

  @Autowired
  private RepositoryRestConfiguration repositoryRestConfiguration;

  @Test
  void testExposeIdsForEntities() {
    // Verify that entity IDs are exposed for the specified classes
    assertTrue(repositoryRestConfiguration.isIdExposedFor(Student.class), "Student IDs should be exposed.");
    assertTrue(repositoryRestConfiguration.isIdExposedFor(Module.class), "Module IDs should be exposed.");
    assertTrue(repositoryRestConfiguration.isIdExposedFor(Grade.class), "Grade IDs should be exposed.");
  }
}
