package kz.sabyrzhan.javaeetest.services;

import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.entities.StudentScore;
import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import kz.sabyrzhan.javaeetest.repositories.StudentScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentScoreService {
    @Autowired
    private StudentScoreRepository studentScoreRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentSubjectService studentSubjectService;

    public Mono<StudentScore> addScore(StudentScore studentScore) {
        Mono<Student> studentServiceMono = studentService.getById(studentScore.getStudentId()).defaultIfEmpty(Student.EMPTY_STUDENT);
        Mono<StudentSubject> studentSubjectMono = studentSubjectService.getSubject(studentScore.getStudentId(), studentScore.getSubjectId()).defaultIfEmpty(StudentSubject.EMPTY_SUBJECT);

        return studentServiceMono.zipWith(studentSubjectMono).flatMap(items -> {
            if (items.getT1() == Student.EMPTY_STUDENT ||
                items.getT2() == StudentSubject.EMPTY_SUBJECT ||
                items.getT1().getId() != items.getT2().getStudentId() ||
                (studentScore.getScore() < 2 || studentScore.getScore() > 5)) {
                return Mono.<StudentScore>error(new IllegalArgumentException("Student id, subject id and score (between [2,5]) are required"));
            }

            return Mono.just(studentScoreRepository.save(studentScore));
        });
    }

    public Mono<Float> calculateGpa(int studentId) {
        List<StudentScore> allScoresList = studentScoreRepository.findAllByStudentId(studentId);
        Map<Integer, Float> map = new HashMap<>();

        for (StudentScore score : allScoresList) {
            map.put(score.getScore(), map.getOrDefault(score.getScore(), 1f));
        }

        float total = 0;
        if (allScoresList.size() > 0) {
            for(int score: map.keySet()) {
                total += map.get(score) * score;
            }
            total = total / allScoresList.size();
        }

        return Mono.just(total);
    }
}
