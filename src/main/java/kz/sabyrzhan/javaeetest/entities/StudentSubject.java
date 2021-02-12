package kz.sabyrzhan.javaeetest.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "student_subjects")
@Entity
@Data
public class StudentSubject {
    public static final StudentSubject EMPTY_SUBJECT = new StudentSubject();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int studentId;
    private String name;
}