package ru.hogwarts.school.controller;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.Collections;


@WebMvcTest(StudentController.class)
@org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient

public class StudentControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private StudentService studentService;

    @org.junit.jupiter.api.Test
    public void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Гарри Поттер");
        student.setAge(11);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Гарри Поттер");
        savedStudent.setAge(11);

        Mockito.when(studentService.createStudent(Mockito.any(Student.class))).thenReturn(savedStudent);

        this.restTestClient.post()
                .uri("/student")
                .body(student)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .isEqualTo(1L); // Проверяем, что эндпоинт вернул ID = 1
    }

    @org.junit.jupiter.api.Test
    public void testGetStudentsByAgeBetween() throws Exception {
        Student student = new Student();
        student.setName("Гермиона Грейнджер");
        student.setAge(11);

        Mockito.when(studentService.findByAgeBetween(10, 20)).thenReturn(Collections.singletonList(student));

        this.restTestClient.get()
                .uri("/student?min=10&max=20")
                .exchange()
                .expectStatus().isOk()
                .expectBody(java.util.List.class)
                .value(list -> org.assertj.core.api.Assertions.assertThat(list).isNotEmpty());
    }
}

