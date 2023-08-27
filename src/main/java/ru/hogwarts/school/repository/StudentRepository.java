package ru.hogwarts.school.repository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT COUNT(s) FROM Student s", nativeQuery = true)
    long countAllStudents();

    @Query(value = "SELECT AVG(s.age) FROM Student s", nativeQuery = true)
    double getAverageStudentAge();

    @Query(value = "SELECT * FROM Student s ORDER BY s.id DESC LIMIT 5", nativeQuery = true)
    List<Student> findTop5ByOrderByIdDesc();

}
