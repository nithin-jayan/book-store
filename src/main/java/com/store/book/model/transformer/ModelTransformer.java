package com.store.book.model.transformer;

import com.store.book.model.Book;
import com.store.book.model.BookModel;

public class ModelTransformer {
    public static Book getBook(BookModel bookModel, Long id) {
        Book book = new Book();
        if(id!=null){
            book.setId(id);
        }
        book.setName(bookModel.getName());
        book.setDescription(bookModel.getDescription());
        book.setAuthor(bookModel.getAuthor());
        book.setType(bookModel.getType());
        book.setPrice(bookModel.getPrice());
        book.setIsbn(bookModel.getIsbn());
        return book;
    }
}
