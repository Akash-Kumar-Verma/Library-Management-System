package com.example.lms.controller;

import com.example.lms.models.Book;
import com.example.lms.models.FilterType;
import com.example.lms.models.Operator;
import com.example.lms.request.BookCreateRequest;
import com.example.lms.response.GenericResponse;
import com.example.lms.services.AuthorService;
import com.example.lms.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @PostMapping("/create")
    private ResponseEntity<GenericResponse<Book>> createBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        // Validation should be here
        Book book = bookService.createBook(bookCreateRequest);

        GenericResponse<Book> response = new GenericResponse<Book>(book, "", "Sucess", 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filter")
    private ResponseEntity<GenericResponse<List<Book>>> filter(@RequestParam FilterType filterBy, @RequestParam Operator operator, @RequestParam String value) {
        // Validation should be here
        //  return bookService.filter(filterBy,operator,value);

        List<Book> list = bookService.filter(filterBy, operator, value);
        GenericResponse<List<Book>> response = new GenericResponse<List<Book>>(list, "", "success", 200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
