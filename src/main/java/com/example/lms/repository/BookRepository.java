package com.example.lms.repository;

import com.example.lms.models.Book;
import com.example.lms.models.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findByBookNo(String bookNo);
    List<Book> findByAuthorName(String authorName);
    List<Book> findByCost(int cost);
    List<Book> findByBookType(BookType bookType);
    List<Book> findByCostLessThan(int cost);
}
