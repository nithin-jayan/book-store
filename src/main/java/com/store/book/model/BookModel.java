package com.store.book.model;

import com.store.book.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static com.store.book.common.BookConstants.BOOK_TYPE_INVALID_CODE;
import static com.store.book.common.BookConstants.BOOK_TYPE_INVALID_MSG;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {

    @NotBlank
    @Size(max = 200, message = "Max 200 characters allowed for book name")
    private String name;

    @Size(max = 255, message = "Max 200 characters allowed for book description")
    private String description;

    @NotBlank
    @Size(max = 200, message = "Max 200 characters allowed for book author")
    private String author;

    @NotBlank
    @Size(max = 100, message = "Max 200 characters allowed for book type")
    private String type;

    @NotNull
    private BigDecimal price;

    @Pattern(regexp = "^(?:ISBN(?:-13)?:?\\ )?(?=[0-9]{13}$|(?=(?:[0-9]+[-\\ ]){4})" +
            "[-\\ 0-9]{17}$)97[89][-\\ ]?[0-9]{1,5}[-\\ ]?[0-9]+[-\\ ]?[0-9]+[-\\ ]?[0-9]$",
            message = "Not a valid ISBN number")
    private String isbn;

    public BookModel validateBookModel() {
        if(getType() != null) {
            Optional<BookType> bookType = Stream.of(BookType.values())
                    .filter(t -> t.getCode().equals(getType()))
                    .findFirst();
            if(!bookType.isPresent()){
                throw new ApiException(BOOK_TYPE_INVALID_CODE, BOOK_TYPE_INVALID_MSG);
            }
        }
        return this;
    }

}
