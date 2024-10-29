package com.rssoftware.students.controller;

import com.rssoftware.students.dto.Standard;
import com.rssoftware.students.entity.Student;
import com.rssoftware.students.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageOffset) {
        if (pageSize == null) pageSize = 10;
        if (pageOffset == null) pageOffset = 1;
        List<Student> students = studentService.getStudents(pageSize, pageOffset);
        if (students == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Student student = studentService.getStudentById(id);
        if (student == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable int id, @RequestBody Student updates) {
        Student student = studentService.updateStudentById(id, updates);
        if (student == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/standards")
    public List<Standard> getStandardDistribution() {
        return studentService.getStandardDistribution();
    }
}
