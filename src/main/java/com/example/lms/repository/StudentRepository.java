package com.example.lms.repository;

import com.example.lms.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByPhoneNo(String phoneNo);
}
