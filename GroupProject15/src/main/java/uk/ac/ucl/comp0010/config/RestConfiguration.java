package uk.ac.ucl.comp0010.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Configuration class for customizing Spring Data REST settings. This class exposes entity IDs in
 * RESTful responses and sets up Cross-Origin Resource Sharing (CORS) settings.
 */
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

  /**
   * Configures the REST repository settings for the application. Specifically, this method ensures
   * that entity IDs for certain models are included in RESTful API responses and sets up global
   * CORS policies.
   *
   * @param config The {@link RepositoryRestConfiguration} used to customize REST repository
   *               settings.
   * @param cors   The {@link CorsRegistry} for defining global CORS configurations.
   */
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    // Expose the IDs of specific entity classes in RESTful API responses
    config.exposeIdsFor(Student.class);
    config.exposeIdsFor(Module.class);
    config.exposeIdsFor(Grade.class);

    // Configure global CORS settings (optional, if needed)
    // Example: Allow all origins for specific methods
    // cors.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
  }
}
