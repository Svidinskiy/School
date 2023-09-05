package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void createFaculty(Faculty faculty) {
        logger.info("Вызван метод для создания факультета");
        facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Вызван метод для получения факультета по ID");
        return facultyRepository.findById(id).orElse(null);
    }

    public void updateFaculty(Long id, Faculty updatedFaculty) {
        logger.info("Вызван метод для обновления факультета по ID");
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null) {
            faculty.setName(updatedFaculty.getName());
            faculty.setColor(updatedFaculty.getColor());
            facultyRepository.save(faculty);
            logger.debug("Обновлен факультет с ID: " + id);
        }
        else {
            logger.warn("Факультет с ID: " + id + " не найден");
            throw new IllegalArgumentException("Факультет не найден");
        }
    }

    public void deleteFaculty(Long id) {
        logger.info("Вызван метод для удаления факультета по ID");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterFacultiesByColor(String color) {
        logger.info("Вызван метод для фильтрации факультетов по цвету");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> filterFacultiesByName(String name) {
        logger.info("Вызван метод для фильтрации факультетов по названию");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public List<Student> getStudentsByFaculty(Long facultyId) {
        logger.info("Вызван метод для получения студентов факультета по ID");
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            return faculty.getStudents();
        } else {
            logger.warn("Факультет с ID: " + facultyId + " не найден");
            return Collections.emptyList();
        }
    }

    public String getFacultyWithLongestName() {
        List<Faculty> allFaculties = facultyRepository.findAll();

        String longestName = allFaculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");

        return longestName;
    }
}
