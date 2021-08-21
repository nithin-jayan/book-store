package com.store.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Book", description = "Book Response Object with id")
public class Book {
    @Id
    @Schema(description = "Book Id")
    private long id;
    private String name;
    private String description;
    private String author;
    private String type;
    private BigDecimal price;
    private String isbn;

}
