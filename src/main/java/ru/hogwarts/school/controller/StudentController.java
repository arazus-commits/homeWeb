package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }
    @PutMapping
    public Student edit(@RequestBody Student student) {
        return studentService.editStudent(student);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
    @GetMapping
    public Collection<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }
}
