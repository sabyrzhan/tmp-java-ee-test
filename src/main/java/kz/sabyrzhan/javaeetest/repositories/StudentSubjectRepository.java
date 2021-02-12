package kz.sabyrzhan.javaeetest.repositories;

import kz.sabyrzhan.javaeetest.entities.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject, Integer> {
    List<StudentSubject> findByStudentIdOrderById(int studentId);
    Optional<StudentSubject> findByStudentIdAndId(int studentId, int id);
    Optional<StudentSubject> findByStudentIdAndName(int studentId, String name);
}
