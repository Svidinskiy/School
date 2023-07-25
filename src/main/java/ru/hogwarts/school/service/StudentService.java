package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;

    public void createStudent(String name, int age) {
        Student student = new Student(idCounter, name, age);
        students.put(idCounter, student);
        idCounter++;
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public void updateStudent(Long id, String name, int age) {
        Student student = students.get(id);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            students.put(id, student);
        }
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }

    public List<Student> filterStudentsByAge(int age) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }
}
