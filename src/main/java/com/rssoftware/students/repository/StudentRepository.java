package com.rssoftware.students.repository;

import com.rssoftware.students.dto.FlatStudent;
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

    @Query(value = """
            SELECT student.id, name, age, standard, address_id FROM student
            INNER JOIN address ON student.address_id = address.id
            WHERE name LIKE :name AND age > :ageUp AND age < :ageDown AND
            standard > :standardUp AND standard < :standardDown AND
            street LIKE :street AND city LIKE :city AND
            pin LIKE :pin AND state LIKE :state
            LIMIT :count OFFSET :skip;
            """,
            nativeQuery = true)
    List<Student> getPaginatedStudentsByFilter(@Param("count") int count, @Param("skip") int skip,
                                      @Param("name") String name, @Param("ageUp") Integer ageUp, @Param("ageDown") Integer ageDown,
                                      @Param("standardUp") Integer standardUp, @Param("standardDown") Integer standardDown, @Param("street") String street,
                                      @Param("city") String city, @Param("state") String state, @Param("pin") String pin);

    @Query(value = """
            SELECT student.id, name, age, standard,
            street, city, state, pin FROM student
            INNER JOIN address ON student.address_id = address.id
            WHERE name LIKE :name AND age > :ageUp AND age < :ageDown AND
            standard > :standardUp AND standard < :standardDown AND
            street LIKE :street AND city LIKE :city AND
            pin LIKE :pin AND state LIKE :state;
            """,
            nativeQuery = true)
    List<FlatStudent> getFlatStudentsByFilter(@Param("name") String name, @Param("ageUp") Integer ageUp, @Param("ageDown") Integer ageDown,
                                              @Param("standardUp") Integer standardUp, @Param("standardDown") Integer standardDown, @Param("street") String street,
                                              @Param("city") String city, @Param("state") String state, @Param("pin") String pin);

}
