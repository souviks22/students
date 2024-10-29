package com.rssoftware.students.service;

import com.rssoftware.students.entity.Student;
import com.rssoftware.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudentsByFilter(String name, Integer ageUp, Integer ageDown, Integer standardUp, Integer standardDown, String street, String city, String state, String pin) {
        return studentRepository.getStudentsByFilter(name, ageUp, ageDown, standardUp, standardDown, street, city, state, pin);
    }

}
