package uk.ac.ucl.comp0010.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SecurityConfig class configures security settings for the application, including CORS and CSRF
 * policies.
 */
@Configuration
public class SecurityConfig {
  /**
   * Configures the SecurityFilterChain, disabling CSRF and enabling CORS with default settings.
   *
   * @param http The HttpSecurity object to configure.
   * @return A SecurityFilterChain object with the defined security configurations.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable()).cors(withDefaults());

    return http.build();
  }

  /**
   * Defines a CORS configuration source that allows unrestricted access to all origins, headers,
   * and methods, while disabling credentials.
   *
   * @return A CorsConfigurationSource object with the configured CORS policies.
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowCredentials(false);
    config.applyPermitDefaultValues();

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return source;
  }
}