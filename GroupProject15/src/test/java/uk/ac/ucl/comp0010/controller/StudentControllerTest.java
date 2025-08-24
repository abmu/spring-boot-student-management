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
import uk.ac.ucl.comp0010.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private StudentService studentService;

  @BeforeEach
  void setup() {
    Student student1 = new Student(1L, "Bob", "Builder", "bobbuilder", "bobbuilder@test.com");
    studentService.addStudent(student1);
    Student student2 = new Student(2L, "Jack", "Jill", "jackjill", "jackjill@test.com");
    studentService.addStudent(student2);
  }

//  @Test
//  void testGetStudentById() throws Exception {
//    mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isOk())
//        .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.firstName").value("Bob"));
//  }
//

//  @Test
//  void testCreateStudent() throws Exception {
//    Student testcreate = new Student(3L,"Master","Shifu","mshifu1","mshifu@test.com");
//    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.post("/students/students")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(testcreate)))
//        .andReturn();
//    assertEquals(HttpStatus.CREATED.value(), action.getResponse().getStatus());
//
//    Student result = objectMapper.readValue(action.getResponse().getContentAsString(), Student.class);
//    assertEquals("Master", result.getFirstName());
//    assertEquals("mshifu1", result.getUsername());
//  }

//  @Test
//  void testGetAllStudents() throws Exception {
//    mockMvc.perform(get("/students")).andExpect(status().isOk())
//        .andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[1].id").value(2))
//        .andExpect(jsonPath("$[2].id").value(3));
//  }
//

//  @Test
//  void testUpdateStudent() throws Exception {
//    testCreateStudent();
//    Student testcreate = new Student(3L,"Po","Panda","popanda1","popanda@test.com");
//    MvcResult action = mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}",3L)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(testcreate)))
//        .andReturn();
//    assertEquals(HttpStatus.OK.value(), action.getResponse().getStatus());
//
//    Student result = objectMapper.readValue(action.getResponse().getContentAsString(), Student.class);
//    assertEquals("Po", result.getFirstName());
//    assertEquals("popanda1", result.getUsername());
//  }

//  @Test
//  void testDeleteStudent() throws Exception {
//    mockMvc.perform(delete("/students/{id}", 2)).andExpect(status().isNotFound());
//  }
}





