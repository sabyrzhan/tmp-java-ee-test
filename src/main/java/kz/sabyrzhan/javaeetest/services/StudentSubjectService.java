package kz.sabyrzhan.javaeetest.services;

import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import kz.sabyrzhan.javaeetest.repositories.StudentSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class StudentSubjectService {
    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    public Mono<List<StudentSubject>> getByStudentId(int studentId) {
        return Mono.just(studentSubjectRepository.findByStudentIdOrderById(studentId));
    }

    public Mono<StudentSubject> addSubject(StudentSubject studentSubject) {
        Optional<StudentSubject> existingSubjectOptional = studentSubjectRepository.findByStudentIdAndName(studentSubject.getStudentId(), studentSubject.getName());
        return existingSubjectOptional
                .map(existingSubject -> Mono.<StudentSubject>error(new IllegalArgumentException("Such subject already added")))
                .orElse(Mono.defer(() -> Mono.just(studentSubjectRepository.save(studentSubject))));
    }

    public Mono<StudentSubject> getSubject(int studentId, int subjectId) {
        return studentSubjectRepository.findByStudentIdAndId(studentId, subjectId)
                .map(studentSubject -> Mono.just(studentSubject))
                .orElse(Mono.<StudentSubject>error(new IllegalArgumentException("Student subject not found")));
    }

    public Mono<StudentSubject> deleteSubject(int studentId, int subjectId) {
        Mono<StudentSubject> studentSubjectMono = getSubject(studentId, subjectId).defaultIfEmpty(StudentSubject.EMPTY_SUBJECT);
        return studentSubjectMono.flatMap(studentSubject -> {
            if (studentSubject == StudentSubject.EMPTY_SUBJECT) {
                return Mono.<StudentSubject>error(new IllegalArgumentException("Student subject not found"));
            }

            studentSubjectRepository.delete(studentSubject);
            return Mono.just(studentSubject);
        });
    }
}
