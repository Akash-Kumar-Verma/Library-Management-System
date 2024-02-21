package com.example.lms.controller;

import com.example.lms.models.Book;
import com.example.lms.models.FilterType;
import com.example.lms.models.Operator;
import com.example.lms.request.BookCreateRequest;
import com.example.lms.services.AuthorService;
import com.example.lms.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Book createBook(@RequestBody BookCreateRequest bookCreateRequest){
       // Validation should be here
        return bookService.createBook(bookCreateRequest);
    }

    @GetMapping("/filter")
    private List<Book> filter(@RequestParam FilterType filterBy, @RequestParam Operator operator, @RequestParam String value){
        // Validation should be here
        return bookService.filter(filterBy,operator,value);
    }
}
