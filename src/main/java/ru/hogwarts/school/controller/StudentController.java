package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/filterByAge")
    public List<Student> filterStudentsByAge(@RequestParam int age) {
        return studentService.filterStudentsByAge(age);
    }

    @GetMapping("/filterByAgeBetween")
    public List<Student> filterStudentsByAgeBetween(@RequestParam int minAge, int maxAge) {
        return studentService.filterStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getFacultyByStudent(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            return student.getFaculty();
        } else {
            return null;
        }
    }
    @GetMapping("/countAllStudents")
    public long countAllStudents() {
        return studentService.countAllStudents();
    }

    @GetMapping("/averageStudentAge")
    public double getAverageStudentAge() {
        return studentService.getAverageStudentAge();
    }

    @GetMapping("/top5Students")
    public List<Student> findTop5Students() {
        return studentService.findTop5Students();
    }

    @GetMapping("/namesStarting")
    public List<String> getStudentNamesStarting() {
        return studentService.getStudentNamesStarting();
    }

    @GetMapping("/averageAge")
    public double getAverageStudentAgeStream() {
        return studentService.getAverageStudentAgeStream();
    }

    @GetMapping("/calculateSum")
    public long calculateSum() {
        return studentService.calculateSum();
    }



}
