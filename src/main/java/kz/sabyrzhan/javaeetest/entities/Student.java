package kz.sabyrzhan.javaeetest.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "students")
@Entity
@Data
public class Student {
    public static final Student EMPTY_STUDENT = new Student();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String patronymic;
}
