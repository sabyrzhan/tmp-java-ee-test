package kz.sabyrzhan.javaeetest.endpoints;

import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.entities.StudentScore;
import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

public class CommonTest {
    protected static final String BASE_URL = "http://localhost";

    @Autowired
    protected WebTestClient webTestClient;

    @LocalServerPort
    protected int port;

    protected StudentScore insertScore(int studentId, int subjectId, int score) {
        StudentScore scoreObject = new StudentScore();
        scoreObject.setScore(score);

        WebClient webClient = WebClient.create(BASE_URL + ":" + port);
        return webClient.post().uri("/students/" + studentId + "/subjects/" + subjectId + "/score")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(scoreObject))
                .retrieve()
                .bodyToMono(StudentScore.class)
                .block();
    }

    protected StudentSubject insertNewSubject(int studentId) {
        Random random = new Random(System.currentTimeMillis());
        StudentSubject subject = new StudentSubject();
        subject.setName("Newsubject"+random.nextInt());

        WebClient webClient = WebClient.create(BASE_URL + ":" + port);
        return webClient.post().uri("/students/" + studentId + "/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(subject))
                .retrieve()
                .bodyToMono(StudentSubject.class)
                .block();
    }

    protected Student insertNewStudent() {
        Random random = new Random(System.currentTimeMillis());
        Student newStudent = new Student();
        newStudent.setName("Testname" + random.nextInt());
        newStudent.setSurname("Testsurname" + random.nextInt());
        newStudent.setPatronymic("Testpatronymic" + random.nextInt());

        WebClient webClient = WebClient.create(BASE_URL + ":" + port);
        return webClient.post().uri("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newStudent))
                .retrieve()
                .bodyToMono(Student.class)
                .block();
    }
}
