package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    java.util.Collection<Student> findByAge(int age);
public Collection<Student> findByAgeBetween (int min, int max);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
public Integer getStudentsCount();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
public Double getAverageAge();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    public Collection<Student> getLastFiveStudents();
}

