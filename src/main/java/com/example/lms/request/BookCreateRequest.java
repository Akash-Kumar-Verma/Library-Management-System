package com.example.lms.request;

import com.example.lms.models.Author;
import com.example.lms.models.Book;
import com.example.lms.models.BookType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateRequest {
    @NotBlank(message = "Name  must not be blank")
    private String name;
    @NotBlank(message = "book no must not be blank")
    private String bookNo;

    @Positive
    private Integer cost;

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
