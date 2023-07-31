package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createStudent(String name, int age) {
        Student student = new Student(null, name, age);
        studentRepository.save(student);
    }


    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public void updateStudent(Long id, String name, int age) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setName(name);
            student.setAge(age);
            studentRepository.save(student);
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }
}
