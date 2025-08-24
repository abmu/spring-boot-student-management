package uk.ac.ucl.comp0010.controller;

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
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.service.GradeService;
import uk.ac.ucl.comp0010.service.ModuleService;
import uk.ac.ucl.comp0010.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc(addFilters = false)
public class GradeControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private GradeService gradeService;

  @Autowired
  private StudentService studentService;

  @Autowired
  private ModuleService moduleService;

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

  @Test
  void addGradeTest() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();
    assertEquals(HttpStatus.OK.value(), action.getResponse().getStatus());

    Grade grade = objectMapper.readValue(action.getResponse().getContentAsString(), Grade.class);
    assertEquals("COMP0010", grade.getModule().getCode());
    assertEquals(85, grade.getScore());
  }


  @Test
  void addGradeMissingParametersTest() throws Exception {
    Map<String, String> params = new HashMap<>();

    // Missing "module_code" and "score"
    assertNull(params.get("student_id"));
    assertNull(params.get("module_code"));
    assertNull(params.get("score"));

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Missing required parameters: student_id, module_code, or score.");
  }

  @Test
  void addGradeInvalidStudentIdTest() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "abc"); // Invalid numeric value
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Invalid student_id: must be a numeric value.");
  }

  @Test
  void addGradeInvalidScoreTest() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");
    params.put("score", "eighty-five"); // Invalid numeric value

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Invalid score: must be an integer value.");
  }

  @Test
  void addGradeEmptyModuleCodeTest() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code"," "); // Empty module_code
    params.put("score", "85");

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Invalid module_code: must not be empty.");
  }

  @Test
  void addGradeMissingParamsTest() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("module_code","COMP0010");
    params.put("score", "85");

    MvcResult action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Missing required parameters: student_id, module_code, or score.");

    params = new HashMap<>();
    params.put("student_id", "1");
    params.put("score", "85");

    action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Missing required parameters: student_id, module_code, or score.");

    params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code","COMP0010");

    action = mockMvc.perform(
        MockMvcRequestBuilders.post("/grades/addGrade").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(params))).andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), action.getResponse().getStatus());
    assertEquals(action.getResponse().getContentAsString(),
        "Missing required parameters: student_id, module_code, or score.");
  }

  //  @Test
  //  void getGradesTest() throws Exception {
  //    gradeService.addGrade(1L, "COMP0010", 85);
  //    gradeService.addGrade(2L, "COMP0016", 80);
  //
  //    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.get("/grades")
  //        .contentType(MediaType.APPLICATION_JSON))
  //        .andReturn();
  //    assertEquals(HttpStatus.OK.value(), action.getResponse().getStatus());
  //
  //    List<Grade> grades = objectMapper.readValue(action.getResponse().getContentAsString(),
  //        objectMapper.getTypeFactory().constructCollectionType(List.class, Grade.class));
  //    assertNotNull(grades);
  //    assertEquals(2, grades.size());
  //    assertEquals("COMP0010", grades.get(0).getModule().getCode());
  //    assertEquals(80, grades.get(1).getScore());
  //  }
}
