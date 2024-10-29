package com.rssoftware.students.repository;

import com.rssoftware.students.dto.Standard;
import com.rssoftware.students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT standard, COUNT(*) AS students FROM student GROUP BY standard;", nativeQuery = true)
    List<Standard> getStandardDistribution();

    @Query(value = "SELECT * FROM student LIMIT :count OFFSET :skip;", nativeQuery = true)
    List<Student> getPaginatedStudents(@Param("count") int count, @Param("skip") int skip);
}
