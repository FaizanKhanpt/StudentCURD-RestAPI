package com.example.Students;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findBySubject(String subject);

    Optional<List<Student>> findByAgeGreaterThan(int age);
}