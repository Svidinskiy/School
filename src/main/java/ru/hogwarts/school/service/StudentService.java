package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createStudent(Student student) {
        logger.info("Вызван метод для создания студента");
        studentRepository.save(student);
    }


    public Student getStudentById(Long id) {
        logger.info("Вызван метод для получения студента по ID");
        return studentRepository.findById(id).orElse(null);
    }

    public void updateStudent(Long id, Student updatedStudent) {
        logger.info("Вызван метод для обновления студента по ID");
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            student.setName(updatedStudent.getName());
            student.setAge(updatedStudent.getAge());
            studentRepository.save(student);
            logger.debug("Обновлен студент с ID: " + id);
        }
        else {
            logger.warn("Студент с ID: " + id + " не найден");
            throw new IllegalArgumentException("Студент не найден");
        }
    }

    public void deleteStudent(Long id) {
        logger.info("Вызван метод для удаления студента по ID");
        studentRepository.deleteById(id);
    }

    public List<Student> filterStudentsByAge(int age) {
        logger.info("Вызван метод для фильтрации студентов по возрасту");
        return studentRepository.findByAge(age);
    }

    public List<Student> filterStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Вызван метод для фильтрации студентов по возрасту в диапазоне");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public long countAllStudents() {
        logger.info("Вызван метод для подсчета всех студентов");
        return studentRepository.countAllStudents();
    }

    public double getAverageStudentAge() {
        logger.info("Вызван метод для получения среднего возраста студентов");
        return studentRepository.getAverageStudentAge();
    }

    public List<Student> findTop5Students() {
        logger.info("Вызван метод для получения 5-ти последних студентов");
        return studentRepository.findTop5ByOrderByIdDesc();
    }
}
