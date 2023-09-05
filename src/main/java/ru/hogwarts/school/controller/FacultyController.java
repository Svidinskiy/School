package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public void createFaculty(@RequestBody Faculty faculty) {
        facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping("/{id}")
    public void updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/filterByColor")
    public List<Faculty> filterFacultiesByColor(@RequestParam String color) {
        return facultyService.filterFacultiesByColor(color);
    }

    @GetMapping("/filterByName")
    public List<Faculty> filterFacultiesByName(@RequestParam String name) {
        return facultyService.filterFacultiesByName(name);
    }

    @GetMapping("/{facultyId}/students")
    public List<Student> getStudentsByFaculty(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        if (faculty != null) {
            return faculty.getStudents();
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/longestName")
    public String getFacultyWithLongestName() {
        return facultyService.getFacultyWithLongestName();
    }

}
