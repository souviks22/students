package com.rssoftware.students.service;

import com.rssoftware.students.dto.Standard;
import com.rssoftware.students.entity.Address;
import com.rssoftware.students.entity.Student;
import com.rssoftware.students.exception.AgeLimitException;
import com.rssoftware.students.repository.AddressRepository;
import com.rssoftware.students.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Student> getStudents(int pageSize, int pageOffset) {
        if (pageSize <= 0 || pageOffset <= 0) return null;
        List<Student> students = studentRepository.findAll();
        int start = pageSize * (pageOffset - 1), end = pageSize * pageOffset;
        if (start >= students.size()) return null;
        return students.subList(start, Math.min(end, students.size()));
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) {
        if (student.getAge() < 10 || student.getAge() > 40) throw new AgeLimitException();
        addressRepository.save(student.getAddress());
        return studentRepository.save(student);
    }

    public Student updateStudentById(int id, Student updates) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) return null;
        if (updates.getName() != null) student.setName(updates.getName());
        if (updates.getAge() != null) student.setAge(updates.getAge());
        if (updates.getStandard() != null) student.setStandard(updates.getStandard());
        if (updates.getAddress() != null) {
            Address changes = updates.getAddress();
            Address address = student.getAddress();
            if (changes.getStreet() != null) address.setStreet(changes.getStreet());
            if (changes.getCity() != null) address.setCity(changes.getCity());
            if (changes.getState() != null) address.setState(changes.getState());
            if (changes.getPin() != null) address.setPin(changes.getPin());
        }
        return saveStudent(student);
    }

    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }

    public List<Standard> getStandardDistribution() {
        return studentRepository.getStandardDistribution();
    }
}
