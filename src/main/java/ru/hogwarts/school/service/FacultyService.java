package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private Long idCounter = 1L;

    public void createFaculty(String name, String color) {
        Faculty faculty = new Faculty(idCounter, name, color);
        faculties.put(idCounter, faculty);
        idCounter++;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public void updateFaculty(Long id, String name, String color) {
        Faculty faculty = faculties.get(id);
        if (faculty != null) {
            faculty.setName(name);
            faculty.setColor(color);
            faculties.put(id, faculty);
        }
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }

    public List<Faculty> filterFacultiesByColor(String color) {
        List<Faculty> filteredFaculties = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (faculty.getColor().equalsIgnoreCase(color)) {
                filteredFaculties.add(faculty);
            }
        }
        return filteredFaculties;
    }
}
