package com.store.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private long id;
    private String name;
    private String description;
    private String author;
    private String type;
    private BigDecimal price;
    private String isbn;

}
