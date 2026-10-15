package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.stream.IntStream;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public Collection<String> getStudentsNamesStartingWithA() {
        logger.info("Was invoked method for get students names starting with A");
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }
    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.findById(id).orElse(null);
    }
    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }
    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }
    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for find by age student");
        return studentRepository.findByAge(age);

    }
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find by age between student");
        return studentRepository.findByAgeBetween(min, max);
    }
    public double getAverageAge() {
        logger.info("Was invoked method for get average age");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
    public int getParallelSum() {
        logger.info("Was invoked method for get parallel sum");
        return IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

}
