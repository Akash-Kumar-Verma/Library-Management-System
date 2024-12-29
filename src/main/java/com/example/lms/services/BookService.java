package com.example.lms.services;

import com.example.lms.models.*;
import com.example.lms.repository.AuthorRepository;
import com.example.lms.repository.BookRepository;
import com.example.lms.request.BookCreateRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    public Book createBook(BookCreateRequest bookCreateRequest) {

        Author authorFromDB = authorRepository.findByEmail(bookCreateRequest.getAuthorEmail());
        if (authorFromDB == null) {
            authorFromDB = authorRepository.save(bookCreateRequest.toAuthor());
        }

        Book book = bookCreateRequest.toBook();
        book.setAuthor(authorFromDB);
        return bookRepository.save(book);
    }

    public List<Book> filter(FilterType filterBy, Operator operator, String value) {
        switch (operator) {
            case EQUALS:
                return switch (filterBy) {
                    case BOOK_NO -> bookRepository.findByBookNo(value);
                    case AUTHOR_NAME -> bookRepository.findByAuthorName(value);
                    case COST -> bookRepository.findByCost(Integer.parseInt(value));
                    case BOOKTYPE -> bookRepository.findByBookType(BookType.valueOf(value));
                };
            case LESS_THAN:
                switch (filterBy) {
                    case COST:
                        return bookRepository.findByCostLessThan(Integer.parseInt(value));
                    case BOOK_NO:
                        return bookRepository.findByBookNoLessThan(value);
                }
            case GREATER_THAN:
                switch (filterBy){
                    case COST:
                        return bookRepository.findByCostGreaterThan(Integer.parseInt(value));
                    case BOOK_NO:
                        return bookRepository.findByBookNoGreaterThan((value));
                }
            default:
                return new ArrayList<>();
        }
    }

    public void saveUpdate(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public boolean SearchBookNo(String bookNo) {
        List<Book> books=bookRepository.findByBookNo(bookNo);
        return !books.isEmpty();
    }

    // filter by yourself for student
// author, should have one more column contact u have to make email nullable true (ddl auto should be update)
}
