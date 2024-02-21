package com.example.lms.request;

import com.example.lms.models.Author;
import com.example.lms.models.Book;
import com.example.lms.models.BookType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {

    private String name;

    private String bookNo;

    private int cost;

    private BookType bookType;

    private String authorName;

    private String authorEmail;

    public Author toAuthor() {
        return Author.builder()
                .name(this.authorName)
                .email(this.authorEmail)
                .build();
    }

    public Book toBook() {
        return Book.builder()
                .name(this.name)
                .bookNo(this.bookNo)
                .cost(this.cost)
                .bookType(this.bookType)
                .build();
    }

}
