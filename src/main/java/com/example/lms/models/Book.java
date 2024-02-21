package com.example.lms.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    private String bookNo;

    private int cost;

    @Enumerated
    private BookType bookType;

    @ManyToOne
    @JoinColumn
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties("bookList")
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Txn> txnList;

}
