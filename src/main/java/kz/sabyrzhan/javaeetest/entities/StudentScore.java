package kz.sabyrzhan.javaeetest.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "student_scores")
@Entity
@Data
public class StudentScore {
    public static final StudentScore EMPTY_STUDENT = new StudentScore();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private int studentId;
    private int subjectId;
    private int score;
}
