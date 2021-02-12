package kz.sabyrzhan.javaeetest.endpoints;

import kz.sabyrzhan.javaeetest.Application;
import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.entities.StudentScore;
import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes= Application.class,
        webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentsRestControllerTest extends CommonTest {


    @Test
    public void test_addNewStudent() {
        Student newStudent = new Student();
        newStudent.setName("Testname");
        newStudent.setSurname("Testsurname");
        newStudent.setPatronymic("Testpatronymic");
        webTestClient.post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newStudent))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(newStudent.getName())
                .jsonPath("$.surname").isEqualTo(newStudent.getSurname())
                .jsonPath("$.patronymic").isEqualTo(newStudent.getPatronymic());
    }

    @Test
    public void test_updateNewStudent() {
        Student newStudent = insertNewStudent();
        Student toUpdate = new Student();
        toUpdate.setSurname("T");
        toUpdate.setName("S");
        toUpdate.setPatronymic("K");

        webTestClient.put().uri("/students/" + newStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(toUpdate))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(toUpdate.getName())
                .jsonPath("$.surname").isEqualTo(toUpdate.getSurname())
                .jsonPath("$.patronymic").isEqualTo(toUpdate.getPatronymic());
    }

    @Test
    public void test_deleteStudent() {
        Student newStudent = insertNewStudent();

        webTestClient.delete().uri("/students/" + newStudent.getId()).exchange().expectStatus().isOk();
        webTestClient.delete().uri("/students/" + newStudent.getId()).exchange().expectStatus().isForbidden();
    }

    @Test
    public void test_getStudent() {
        Student newStudent = insertNewStudent();

        webTestClient.get().uri("/students").exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].id").isEqualTo(newStudent.getId());
    }

    @Test
    public void test_score() {
        Student newStudent = insertNewStudent();
        StudentSubject newSubject = insertNewSubject(newStudent.getId());
        insertScore(newStudent.getId(), newSubject.getId(), 2);
        insertScore(newStudent.getId(), newSubject.getId(), 3);
        insertScore(newStudent.getId(), newSubject.getId(), 4);
        insertScore(newStudent.getId(), newSubject.getId(), 5);

        webTestClient.get().uri("/students/" + newStudent.getId() + "/gpa").exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.gpa").isEqualTo(3.5f);
    }


}