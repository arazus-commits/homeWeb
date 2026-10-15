package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
@Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @PostMapping
    public Long create(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty).getId();
    }
    @GetMapping(params = "search")
    public Collection<Faculty> getStudent(@RequestParam String search) {
        return facultyService.findByNameOrColor(search, search);
    }
    @PutMapping
    public Faculty edit(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
    @GetMapping("{id}/students")
    public Collection<Student> getFacultyByColor(@PathVariable Long id) {
        return facultyService.findFaculty(id).getStudents();

    }
    @GetMapping("/longest-name")
    public String getLongestName() {
        return facultyService.getLongestFacultyName();
    }
}

