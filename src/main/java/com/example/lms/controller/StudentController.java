package com.example.lms.controller;

import com.example.lms.models.Book;
import com.example.lms.models.Student;
import com.example.lms.request.BookCreateRequest;
import com.example.lms.request.StudentCreateRequest;
import com.example.lms.services.AuthorService;
import com.example.lms.services.BookService;
import com.example.lms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    private Student createStudent(@RequestBody StudentCreateRequest studentCreateRequest){
        // Validation should be here

        return studentService.createStudent(studentCreateRequest);

    }
}
