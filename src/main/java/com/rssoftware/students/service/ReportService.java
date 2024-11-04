package com.rssoftware.students.service;

import com.rssoftware.students.dto.FlatStudent;
import com.rssoftware.students.repository.StudentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private StudentRepository studentRepository;

    @Value("classpath:reports/students.jrxml")
    private Resource reportResource;

    public byte[] getStudentsPDFByFilter(String name, Integer ageUp, Integer ageDown, Integer standardUp, Integer standardDown, String street, String city, String state, String pin) {
        List<FlatStudent> students = studentRepository.getFlatStudentsByFilter(name, ageUp, ageDown, standardUp, standardDown, street, city, state, pin);
        try {
            JasperReport report = JasperCompileManager.compileReport(reportResource.getInputStream());
            JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(students);
            Map<String, Object> params = new HashMap<>();
            JasperPrint structure = JasperFillManager.fillReport(report, params, data);
            return JasperExportManager.exportReportToPdf(structure);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
