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
    @GetMapping("/count")
    public Integer getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        Double avgAge = studentService.getAverageAge();
        return avgAge != null ? avgAge : 0.0;
    }

    @GetMapping("/last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

}
