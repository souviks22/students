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
    public ResponseEntity<List<Student>> getStudents(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageOffset,
                                                     @RequestParam(required = false) String name, @RequestParam(required = false) Integer ageUp, @RequestParam(required = false) Integer ageDown,
                                                     @RequestParam(required = false) Integer standardUp, @RequestParam(required = false) Integer standardDown, @RequestParam(required = false) String street,
                                                     @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) Integer pin) {
        if (pageSize == null) pageSize = 5;
        if (pageOffset == null) pageOffset = 1;
        String namePattern = name == null ? "%" : "%" + name + "%";
        String streetPattern = street == null ? "%" : "%" + street + "%";
        String cityPattern = city == null ? "%" : "%" + city + "%";
        String statePattern = state == null ? "%" : "%" + state + "%";
        String pinPattern = pin == null ? "%" : "%" + pin + "%";
        Integer greaterThanAge = ageUp == null ? 0 : ageUp;
        Integer lessThanAge = ageDown == null ? 999 : ageDown;
        Integer greaterThanStandard = standardUp == null ? 0 : standardUp;
        Integer lessThanStandard = standardDown == null ? 999 : standardDown;
        List<Student> students = studentService.getStudents(pageSize, pageOffset, namePattern, greaterThanAge, lessThanAge, greaterThanStandard, lessThanStandard, streetPattern, cityPattern, statePattern, pinPattern);
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
