package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTestWithWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetStudentById() throws Exception {
        Long studentId = 4L;
        Student student = new Student();

        student.setId(studentId);
        student.setAge(18);
        student.setName("Иванов Иван");

        when(studentService.getStudentById(studentId)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Иванов Иван"));
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student(null, "Виктор Викторов", 20);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(asJsonStr(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testUpdateStudent() throws Exception {
        Long studentId = 24L;
        Student updatedStudent = new Student(studentId, "Петр Петров", 23);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/{id}", studentId)
                        .content(asJsonStr(updatedStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteStudent() throws Exception {
        Long studentId = 25L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testFilterStudentsByAge() throws Exception {
        int age = 21;
        List<Student> students = Arrays.asList(
                new Student(null, "Андрей Андреев", 21),
                new Student(null, "Дмитрий Дмитрив", 21)
        );

        when(studentService.filterStudentsByAge(age)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filterByAge?age={age}", age)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(students.get(1).getName()));
    }

    @Test
    public void testFilterStudentsByAgeBetween() throws Exception {
        int minAge = 20;
        int maxAge = 25;
        List<Student> students = Arrays.asList(
                new Student(1L, "Андрей Андреев", 20),
                new Student(2L, "Федор Федоров", 22),
                new Student(3L, "Максим Максимов", 25)
        );

        when(studentService.filterStudentsByAgeBetween(minAge, maxAge)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filterByAgeBetween?minAge={minAge}&maxAge={maxAge}", minAge, maxAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(students.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(students.get(1).getName()))
                .andExpect(jsonPath("$[2].name").value(students.get(2).getName()));
    }

    @Test
    public void testGetFacultyByStudent() throws Exception {
        Long studentId = 27L;
        Student student = new Student(studentId, "Ханна Аббот", 19);

        Faculty faculty = new Faculty(null, "Пуффендуй", "Желтый");
        student.setFaculty(faculty);

        when(studentService.getStudentById(studentId)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/{studentId}/faculty", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(student.getFaculty().getName()));

    }

    private String asJsonStr(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
