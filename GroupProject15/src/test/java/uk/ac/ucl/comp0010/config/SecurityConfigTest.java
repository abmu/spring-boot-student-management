package uk.ac.ucl.comp0010.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the SecurityConfig class.
 */
@SpringBootTest
public class SecurityConfigTest {

  @Autowired
  private SecurityConfig securityConfig;

  @Autowired
  private SecurityFilterChain securityFilterChain;

  @Autowired
  private CorsConfigurationSource corsConfigurationSource;

  /**
   * Verifies that the SecurityConfig bean is correctly loaded.
   */
  @Test
  public void testSecurityConfigBean() {
    assertNotNull(securityConfig, "SecurityConfig bean should not be null");
  }

  /**
   * Verifies that the SecurityFilterChain is correctly configured and loaded.
   */
  @Test
  public void testSecurityFilterChainBean() {
    assertNotNull(securityFilterChain, "SecurityFilterChain bean should not be null");
  }

  /**
   * Verifies that the CorsConfigurationSource bean is correctly configured and loaded.
   */
  @Test
  public void testCorsConfigurationSourceBean() {
    assertNotNull(corsConfigurationSource, "CorsConfigurationSource bean should not be null");
  }
}
