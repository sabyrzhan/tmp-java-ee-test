package kz.sabyrzhan.javaeetest.services;

import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Mono<Student> addNewStudent(Student student) {
        Optional<Student> existingStudentMono = studentRepository.findFirstByNameAndSurnameAndPatronymic(
                student.getName(), student.getSurname(), student.getPatronymic()
        );

        if (existingStudentMono.isPresent()) {
            return Mono.<Student>error(new IllegalArgumentException("Student with such data already exists"));
        }

        return Mono.just(studentRepository.save(student));
    }

    public Mono<Student> updateStudent(Student student) {
        Optional<Student> existingStudentOptinal = studentRepository
                .findById(student.getId());
        Optional<Student> otherExistingStudentOptional = studentRepository
                .findFirstByNameAndSurnameAndPatronymic(student.getName(), student.getSurname(), student.getPatronymic());


        if (!existingStudentOptinal.isPresent()) {
            return Mono.error(new IllegalArgumentException("Student not found"));
        }

        if (otherExistingStudentOptional.isPresent() && existingStudentOptinal.get().getId() != otherExistingStudentOptional.get().getId()) {
            return Mono.error(new IllegalArgumentException("Student with such data already exists"));
        }

        Student first = existingStudentOptinal.get();

        first.setName(student.getName());
        first.setSurname(student.getSurname());
        first.setPatronymic(student.getPatronymic());

        return Mono.just(studentRepository.save(first));
    }

    public Mono<Student> deleteStudent(int studentId) {
        Mono<Student> studentMono = getById(studentId);
        return studentMono.flatMap(student -> {
            if (student == Student.EMPTY_STUDENT) {
                return Mono.error(new IllegalArgumentException("Student not found"));
            }

            studentRepository.delete(student);
            return Mono.just(student);
        });
    }

    public Mono<List<Student>> getAll() {
        return Mono.just(studentRepository.findAllByOrderByIdDesc());
    }

    public Mono<Student> getById(int studentId) {
        return studentRepository.findById(studentId)
                .map(student -> Mono.just(student))
                .orElse(Mono.<Student>error(new IllegalArgumentException("Student not found")));
    }
}
