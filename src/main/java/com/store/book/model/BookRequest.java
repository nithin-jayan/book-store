package com.store.book.model;


import com.store.book.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import static com.store.book.common.BookConstants.BOOK_LIST_EMPTY_CODE;
import static com.store.book.common.BookConstants.BOOK_LIST_EMPTY_MSG;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    @NotNull
    @Valid
    private List<BookModel> books;


    public Flux<BookModel> validateBookRequest() {
        if(CollectionUtils.isEmpty(books)){
            throw new ApiException(BOOK_LIST_EMPTY_CODE, BOOK_LIST_EMPTY_MSG);
        }
        books.stream().forEach(BookModel::validateBookModel);
        return Flux.fromIterable(this.getBooks());
    }
}
