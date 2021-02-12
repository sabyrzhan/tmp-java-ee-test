package kz.sabyrzhan.javaeetest.endpoints;

import kz.sabyrzhan.javaeetest.endpoints.responses.GpaResponse;
import kz.sabyrzhan.javaeetest.entities.Student;
import kz.sabyrzhan.javaeetest.services.StudentScoreService;
import kz.sabyrzhan.javaeetest.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping(value = "/students")
@RestController
public class StudentsRestController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentScoreService studentScoreService;

    @GetMapping
    public Mono<List<Student>> getAll() {
        return studentService.getAll();
    }

    @PostMapping
    public Mono<Student> addStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

    @PutMapping("/{id}")
    public Mono<Student> updateStudent(@PathVariable int id,
                                       @RequestBody Student student) {
        student.setId(id);
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public Mono<Student> deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/{id}/gpa")
    public Mono<GpaResponse> calculateGpa(@PathVariable int id) {
        return studentScoreService.calculateGpa(id).map(gpa -> new GpaResponse(gpa));
    }
}
