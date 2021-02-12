package kz.sabyrzhan.javaeetest.repositories;

import kz.sabyrzhan.javaeetest.entities.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentScoreRepository extends JpaRepository<StudentScore, String> {
    List<StudentScore> findAllByStudentId(int studentId);
}
