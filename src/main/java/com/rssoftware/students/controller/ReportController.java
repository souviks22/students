package com.rssoftware.students.controller;

import com.rssoftware.students.entity.Student;
import com.rssoftware.students.service.ReportService;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/filters")
    public ResponseEntity<List<Student>> getFilteredStudents(@RequestParam(required = false) String name, @RequestParam(required = false) Integer ageUp, @RequestParam(required = false) Integer ageDown,
                                                             @RequestParam(required = false) Integer standardUp, @RequestParam(required = false) Integer standardDown, @RequestParam(required = false) String street,
                                                             @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) Integer pin) {
        String namePattern = name == null ? "%" : "%" + name + "%";
        String streetPattern = street == null ? "%" : "%" + street + "%";
        String cityPattern = city == null ? "%" : "%" + city + "%";
        String statePattern = state == null ? "%" : "%" + state + "%";
        String pinPattern = pin == null ? "%" : "%" + pin + "%";
        Integer greaterThanAge = ageUp == null ? 0 : ageUp;
        Integer lessThanAge = ageDown == null ? 999 : ageDown;
        Integer greaterThanStandard = standardUp == null ? 0 : standardUp;
        Integer lessThanStandard = standardDown == null ? 999 : standardDown;
        return ResponseEntity.ok(reportService.getStudentsByFilter(namePattern, greaterThanAge, lessThanAge, greaterThanStandard, lessThanStandard, streetPattern, cityPattern, statePattern, pinPattern));
    }

//    @GetMapping("/download")
//    public ResponseEntity<byte[]> generateFilteredReport() {
//
//    }


}
