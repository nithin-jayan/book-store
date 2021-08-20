package com.store.book.controller;

import com.store.book.api.BookApi;
import com.store.book.model.Book;
import com.store.book.model.BookRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@RestController("/books")
public class BookController implements BookApi {

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=1")
    @Override
    public Flux<Book> addBooks(
            @RequestHeader(value = "X-API-VERSION") @Nullable String apiVersion,
            @RequestHeader(value = "X-CORRELATION-ID") @NotNull String correlationId,
            @RequestBody @Valid BookRequest bookRequest) {
        return Flux.empty();
    }
}
