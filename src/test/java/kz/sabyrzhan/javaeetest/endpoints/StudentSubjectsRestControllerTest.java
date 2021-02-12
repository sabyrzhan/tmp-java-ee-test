package kz.sabyrzhan.javaeetest.endpoints;

import kz.sabyrzhan.javaeetest.Application;
import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Random;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes= Application.class,
        webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentSubjectsRestControllerTest extends CommonTest {
    @Test
    public void test_addScore() {
        Random random = new Random(System.currentTimeMillis());
        Student newStudent = insertNewStudent();

        StudentSubject subject = new StudentSubject();
        subject.setName("Testsubject" + random.nextInt());

        webTestClient.post().uri("/students/" + newStudent.getId() + "/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(subject))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(subject.getName());
    }
}