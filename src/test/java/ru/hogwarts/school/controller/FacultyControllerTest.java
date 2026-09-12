package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;

import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class FacultyControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private FacultyRepository facultyRepository;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        facultyRepository.deleteAll();
    }

    @org.junit.jupiter.api.Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");

        Long id = this.restTestClient.post()
                .uri("/faculty")
                .body(faculty)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(id).isGreaterThan(0L);
    }

    @org.junit.jupiter.api.Test
    public void testPutFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Пуффендуй");
        faculty.setColor("Желтый");

        Long id = this.restTestClient.post()
                .uri("/faculty")
                .body(faculty)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .returnResult().getResponseBody();

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(id);
        updatedFaculty.setName("Когтевран");
        updatedFaculty.setColor("Синий");

        this.restTestClient.put()
                .uri("/faculty")
                .body(updatedFaculty)
                .exchange()
                .expectStatus().isOk();

        Faculty facultyFromDb = facultyRepository.findById(id).orElse(null);
        org.assertj.core.api.Assertions.assertThat(facultyFromDb).isNotNull();
        org.assertj.core.api.Assertions.assertThat(facultyFromDb.getName()).isEqualTo("Когтевран");
    }

    @org.junit.jupiter.api.Test
    public void testGetFacultyByColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Слизерин");
        faculty.setColor("Зеленый");

        this.restTestClient.post()
                .uri("/faculty")
                .body(faculty)
                .exchange()
                .expectStatus().isOk();


        this.restTestClient.get()
                .uri("/faculty?search=Зеленый")
                .exchange()
                .expectStatus().isOk()
                .expectBody(java.util.List.class)
                .value(list -> org.assertj.core.api.Assertions.assertThat(list).isNotEmpty());
    }
}
