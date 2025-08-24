package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModuleTest {
  private Module module;

  @BeforeEach
  void beforeEach() {
    module = new Module();
    module = new Module("COMP0010", "Software Engineering", true);
  }

  @Test
  void constructorAndGettersTest() {
    assertEquals(module.getCode(), "COMP0010");
    assertEquals(module.getName(), "Software Engineering");
    assertEquals(module.isMnc(), true);
  }

  @Test
  void setCodeTest() {
    module.setCode("COMP0016");
    assertEquals(module.getCode(), "COMP0016");
  }

  @Test
  void setNameTest() {
    module.setName("Systems Engineering");
    assertEquals(module.getName(), "Systems Engineering");
  }

  @Test
  void setMncTest() {
    module.setMnc(false);
    assertEquals(module.isMnc(), false);

    module.setMnc(true);
    assertEquals(module.isMnc(), true);
  }
}
