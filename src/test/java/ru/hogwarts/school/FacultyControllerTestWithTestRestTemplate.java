package ru.hogwarts.school;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestWithTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    public void testGetFacultyById() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/6", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty(null,"Слизерин", "Зеленый");
        ResponseEntity<Void> response = restTemplate.postForEntity(getBaseUrl(), faculty, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testUpdateFaculty() {
        Long facultyId = 7L;
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("Слизерин");
        updatedFaculty.setColor("Серебрянный и зеленый");
        restTemplate.put(getBaseUrl() + "/" + facultyId, updatedFaculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/" + facultyId, Faculty.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updatedFaculty.getName(), response.getBody().getName());


    }

    @Test
    public void testDeleteFaculty() {
        Long facultyId = 6L;
        restTemplate.delete(getBaseUrl() + "/" + facultyId);
        ResponseEntity<Faculty> response = restTemplate.getForEntity(getBaseUrl() + "/19", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testFilterFacultiesByColor() {
        String color = "Красный и золотой";
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(getBaseUrl() + "/filterByColor?color=" + color, Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFilterFacultiesByName() {
        String name = "Слизерин";
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(getBaseUrl() + "/filterByName?name=" + name, Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentsByFaculty() {
        Long facultyId = 7L;
        ResponseEntity<Student[]> response = restTemplate.getForEntity(getBaseUrl()+ "/" + facultyId + "/students", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}