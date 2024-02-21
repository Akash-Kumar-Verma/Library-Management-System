package com.example.lms.services;

import com.example.lms.models.*;
import com.example.lms.repository.AuthorRepository;
import com.example.lms.repository.BookRepository;
import com.example.lms.request.BookCreateRequest;
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
                switch (filterBy) {
                    case BOOK_NO:
                        return bookRepository.findByBookNo(value);
                    case AUTHOR_NAME:
                        return bookRepository.findByAuthorName(value);
                    case COST:
                        return bookRepository.findByCost(Integer.parseInt(value));
                    case BOOKTYPE:
                        return bookRepository.findByBookType(BookType.valueOf(value));
                }
            case LESS_THAN:
                switch (filterBy) {
                    case COST:
                        return bookRepository.findByCostLessThan(Integer.parseInt(value));
                }
            default:
                return new ArrayList<>();
        }
    }

    public void saveUpdate(Book book) {
        bookRepository.save(book);
    }

    // filter by urself for student
// author, should have one more column contact u have to make email nullable true (ddl auto should be update)
}
