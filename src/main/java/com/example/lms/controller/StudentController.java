package com.example.lms.controller;


import com.example.lms.models.*;
import com.example.lms.request.StudentCreateRequest;
import com.example.lms.response.GenericResponse;
import com.example.lms.services.AuthorService;
import com.example.lms.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    private ResponseEntity<GenericResponse<Student>> createStudent(@RequestBody @Valid StudentCreateRequest studentCreateRequest) {
        // Validation should be here

        return studentService.createStudent(studentCreateRequest);
    }

    @GetMapping("/filter")
    private ResponseEntity<GenericResponse<List<Student>>> filter(@RequestParam StudentFilterType filterBy, @RequestParam Operator operator, @RequestParam String value) {
        // Validation should be here
        //  return bookService.filter(filterBy,operator,value);

        List<Student> list = studentService.filter(filterBy, operator, value);
        GenericResponse<List<Student>> response = new GenericResponse<>(list, "", "success", 200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
