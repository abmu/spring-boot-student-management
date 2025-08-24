package uk.ac.ucl.comp0010.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.service.ModuleService;
import uk.ac.ucl.comp0010.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTest {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ModuleService moduleService;
  @Autowired
  private StudentService studentService;

  @BeforeEach
  void setup() {
    Student student1 = new Student(1L, "Bob", "Builder", "bobbuilder", "bobbuilder@test.com");
    studentService.addStudent(student1);
    Student student2 = new Student(2L, "John", "Doe", "jdoe", "johndoe@company.com");
    studentService.addStudent(student2);
    Module module1 = new Module("COMP0010", "Software Engineering", true);
    moduleService.addModule(module1);
    Module module2 = new Module("COMP0016", "Systems Engineering", true);
    moduleService.addModule(module2);
  }
}
