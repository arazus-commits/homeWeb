package ru.hogwarts.school.controller;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;

import org.springframework.test.web.servlet.client.RestTestClient;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.Collections;

@WebMvcTest(FacultyController.class)
@AutoConfigureRestTestClient
public class FacultyControllerTest {
    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private FacultyService facultyService;

    @org.junit.jupiter.api.Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");

        Faculty savedFaculty = new Faculty();
        savedFaculty.setId(1L);
        savedFaculty.setName("Гриффиндор");
        savedFaculty.setColor("Красный");
        Mockito.when(facultyService.createFaculty(Mockito.any(Faculty.class))).thenReturn(savedFaculty);

        this.restTestClient.post()
                .uri("/faculty")
                .body(faculty)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Long.class)
                .isEqualTo(1L);
    }

    @org.junit.jupiter.api.Test
    public void testGetFacultyByColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Слизерин");
        faculty.setColor("Зеленый");

        Mockito.when(facultyService.findByNameOrColor("Зеленый", "Зеленый"))
                .thenReturn(Collections.singletonList(faculty));

        this.restTestClient.get()
                .uri("/faculty?search=Зеленый")
                .exchange()
                .expectStatus().isOk()
                .expectBody(java.util.List.class)
                .value(list -> org.assertj.core.api.Assertions.assertThat(list).isNotEmpty());
    }
}

