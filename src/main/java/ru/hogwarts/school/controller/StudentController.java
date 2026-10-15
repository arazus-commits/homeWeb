package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
@Autowired
    public StudentController(StudentService studentService) {
    this.studentService = studentService;
}

    @PostMapping
    public Long create(@RequestBody Student student) {
        return studentService.createStudent(student).getId();
    }
    @GetMapping(params = {"min", "max"})
    public Collection<Student> getStudentsByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min,max);
    }
    @PutMapping
    public Student edit(@RequestBody Student student) {
        return studentService.editStudent(student);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
    @GetMapping("{id}/faculty")
    public Faculty getStudentsByAge(@PathVariable Long id) {
        return studentService.findStudent(id).getFaculty();


    }
    @GetMapping("/names-starting-with-a")
    public Collection<String> getNamesStartingWithA() {
        return studentService.getStudentsNamesStartingWithA();
    }
    @GetMapping("/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/parallel-sum")
    public int getParallelSum() {
        return studentService.getParallelSum();
    }
}
