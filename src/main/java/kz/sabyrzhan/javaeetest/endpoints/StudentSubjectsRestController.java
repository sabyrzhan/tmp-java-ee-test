package kz.sabyrzhan.javaeetest.endpoints;

import kz.sabyrzhan.javaeetest.entities.StudentScore;
import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import kz.sabyrzhan.javaeetest.services.StudentScoreService;
import kz.sabyrzhan.javaeetest.services.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping(value = "/students/{studentId}/subjects")
@RestController
public class StudentSubjectsRestController {
    @Autowired
    private StudentSubjectService studentSubjectService;
    @Autowired
    private StudentScoreService studentScoreService;

    @GetMapping
    public Mono<List<StudentSubject>> getStudentSubjects(@PathVariable int studentId) {
        return studentSubjectService.getByStudentId(studentId);
    }

    @GetMapping("/{subjectId}")
    public Mono<StudentSubject> getSubject(@PathVariable int studentId, @PathVariable int subjectId) {
        return studentSubjectService.getSubject(studentId, subjectId);
    }

    @PostMapping
    public Mono<StudentSubject> addSubject(@PathVariable int studentId,
                                           @RequestBody StudentSubject subject) {
        subject.setStudentId(studentId);
        return studentSubjectService.addSubject(subject);
    }

    @DeleteMapping("/{subjectId}")
    public Mono<StudentSubject> deleteSubject(@PathVariable int studentId,
                                              @PathVariable int subjectId) {
        return studentSubjectService.deleteSubject(studentId, subjectId);
    }

    @PostMapping("/{subjectId}/score")
    public Mono<StudentScore> addScore(@RequestBody StudentScore score,
                                         @PathVariable int studentId,
                                         @PathVariable int subjectId) {
        score.setStudentId(studentId);
        score.setSubjectId(subjectId);

        return studentScoreService.addScore(score);
    }
}