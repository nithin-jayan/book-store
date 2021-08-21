package com.store.book.exception;

import com.store.book.common.BookConstants;
import lombok.Data;

@Data
public class BookNotFoundException extends RuntimeException {

    private Error error;

    public BookNotFoundException(Long id) {
        super(BookConstants.BOOK_NO_FOUND_MSG);
        String message =BookConstants.BOOK_NO_FOUND_MSG+" with Id " + id;
        this.error = new Error(BookConstants.BOOK_NO_FOUND_CODE, message);
    }
}
