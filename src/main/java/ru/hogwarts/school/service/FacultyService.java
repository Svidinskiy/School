package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public void createFaculty(String name, String color) {
        Faculty faculty = new Faculty(null, name, color);
        facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public void updateFaculty(Long id, String name, String color) {
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null) {
            faculty.setName(name);
            faculty.setColor(color);
            facultyRepository.save(faculty);
        }
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterFacultiesByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
}
