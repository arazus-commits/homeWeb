package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;

import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient // Включает новый клиент Спринга 4
public class StudentControllerTest {

    @Autowired
    private RestTestClient restTestClient; // Используем актуальный RestTestClient вместо TestRestTemplate

    @Autowired
    private StudentRepository studentRepository;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
    }

    @org.junit.jupiter.api.Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setName("Гарри Поттер");
        student.setAge(11);


        Long id = this.restTestClient.post()
                .uri("/student")
                .body(student)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .returnResult().getResponseBody();

        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName("Рон Уизли");
        updatedStudent.setAge(12);


        this.restTestClient.put()
                .uri("/student")
                .body(updatedStudent)
                .exchange()
                .expectStatus().isOk();


        Student studentFromDb = studentRepository.findById(id).orElse(null);
        org.assertj.core.api.Assertions.assertThat(studentFromDb).isNotNull();
        org.assertj.core.api.Assertions.assertThat(studentFromDb.getName()).isEqualTo("Рон Уизли");
    }


    @org.junit.jupiter.api.Test
    public void testGetStudentsByAge() throws Exception {
        Student student = new Student();
        student.setName("Драко Малфой");
        student.setAge(15);

        this.restTestClient.post()
                .uri("/student")
                .body(student)
                .exchange()
                .expectStatus().isOk();


        this.restTestClient.get()
                .uri("/student?min=10&max=20") // Передаем параметры, которые реально ждет контроллер
                .exchange()
                .expectStatus().isOk()
                .expectBody(java.util.List.class)
                .value(list -> org.assertj.core.api.Assertions.assertThat(list).isNotEmpty());

    }
}
