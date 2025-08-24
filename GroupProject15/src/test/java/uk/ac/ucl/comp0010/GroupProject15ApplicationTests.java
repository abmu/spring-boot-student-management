package uk.ac.ucl.comp0010;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GroupProject15ApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void mainMethodTest() {
    // If the application starts without throwing an exception, the test passes
    GroupProject15Application.main(new String[] {});
  }
}
