package ru.hogwarts.school.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    @OneToMany(mappedBy = "faculty")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Collection<Student> students;

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public Faculty() {
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
