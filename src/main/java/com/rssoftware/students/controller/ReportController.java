package com.rssoftware.students.controller;

import com.rssoftware.students.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> getFilteredStudents(@RequestParam(required = false) String name, @RequestParam(required = false) Integer ageUp, @RequestParam(required = false) Integer ageDown,
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
        byte[] pdf = reportService.getStudentsPDFByFilter(namePattern, greaterThanAge, lessThanAge, greaterThanStandard, lessThanStandard, streetPattern, cityPattern, statePattern, pinPattern);
        if(pdf == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(pdf));
    }

}
