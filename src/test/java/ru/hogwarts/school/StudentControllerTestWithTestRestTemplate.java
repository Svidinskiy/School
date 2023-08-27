package ru.hogwarts.school;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestWithTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    private String getBaseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    public void testGetStudentById() {
        ResponseEntity<Student> response = restTemplate.getForEntity(getBaseUrl() + "/6", Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void testCreateStudent() {
        Student newStudent = new Student(null, "Петр Петров1", 26);
        ResponseEntity<Void> response = restTemplate.postForEntity(getBaseUrl(), newStudent, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testUpdateStudent() {
        Long studentId = 4L;

        Student updatedStudent = new Student();
        updatedStudent.setName("Иванов Иван");
        updatedStudent.setAge(18);

        restTemplate.put(getBaseUrl() + "/" + studentId, updatedStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity(getBaseUrl() + "/" + studentId, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent.getName(), response.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        Long studentId = 25L;
        restTemplate.delete(getBaseUrl() + "/" + studentId);
        ResponseEntity<Student> response = restTemplate.getForEntity(getBaseUrl() + "/" + studentId, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFilterStudentsByAge() {
        int age = 20;
        ResponseEntity<Student[]> response = restTemplate.getForEntity(getBaseUrl() + "/filterByAge?age=" + age, Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Transactional
    @Test
    public void testFilterStudentsByAgeBetween() {
        int minAge = 19;
        int maxAge = 22;

        ResponseEntity<Student[]> response = restTemplate.getForEntity(getBaseUrl() + "/filterByAgeBetween?minAge=" + minAge + "&maxAge=" + maxAge, Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetFacultyByStudent() {
        Long studentId = 6L;
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/" + studentId + "/faculty", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCountAllStudents() {
        ResponseEntity<Long> response = restTemplate.getForEntity(getBaseUrl() + "/countAllStudents", Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAverageStudentAge() {
        ResponseEntity<Double> response = restTemplate.getForEntity(getBaseUrl()+ "/averageStudentAge", Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testFindTop5Students() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl()+ "/top5Students", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
