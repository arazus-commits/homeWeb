package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    @GetMapping("{id}")
    public Faculty getStudent(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }
    @PutMapping
    public Faculty edit(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
    @GetMapping
    public Collection<Faculty> getFacultyByColor(@RequestParam String color) {
        return facultyService.findByColor(color);
    }
}

