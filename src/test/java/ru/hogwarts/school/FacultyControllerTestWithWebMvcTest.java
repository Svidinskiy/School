package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTestWithWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty(null, "Гриффиндор", "Оранжевый");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(asJsonStr(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Long studentId = 7L;
        Faculty faculty = new Faculty();

        faculty.setId(studentId);
        faculty.setColor("Серебрянный и зеленый");
        faculty.setName("Слизерин");

        when(facultyService.getFacultyById(studentId)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("Серебрянный и зеленый"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Слизерин"));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Long facultyId = 3L;
        Faculty updatedFaculty = new Faculty(facultyId, "Гриффиндор", "Красный и золотой");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/{id}", facultyId)
                        .content(asJsonStr(updatedFaculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Long facultyId = 3L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFilterFacultiesByColor() throws Exception {
        String color = "Оранжевый";
        Faculty faculty1 = new Faculty(null, "Гриффиндор", "Оранжевый");
        Faculty faculty2 = new Faculty(null, "Слизерин", "Зеленый");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.filterFacultiesByColor(color)).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filterByColor")
                        .param("color", color)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$[1].name").value(faculty2.getName()));
    }

    @Test
    public void testFilterFacultiesByName() throws Exception {
        String name = "Гриффиндор";
        Faculty faculty1 = new Faculty(null, "Гриффиндор", "Оранжевый");
        Faculty faculty2 = new Faculty(null, "Слизерин", "Зеленый");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.filterFacultiesByName(name)).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filterByName")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$[1].color").value(faculty2.getColor()));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Long facultyId = 1L;
        Faculty faculty = new Faculty(facultyId, "Гриффиндор", "Оранжевый");

        Student student1 = new Student(null, "Гермиона Грейнджер", 19);
        Student student2 = new Student(null, "Ли Джордан", 18);
        faculty.setStudents(Arrays.asList(student1, student2));

        when(facultyService.getFacultyById(facultyId)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{facultyId}/students", facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(student1.getName()))
                .andExpect(jsonPath("$[1].name").value(student2.getName()));
    }

    private static String asJsonStr(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

