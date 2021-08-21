package com.store.book.controller;

import com.store.book.api.BookApi;
import com.store.book.exception.BookNotFoundException;
import com.store.book.model.Book;
import com.store.book.model.BookModel;
import com.store.book.model.BookRequest;
import com.store.book.model.transformer.ModelTransformer;
import com.store.book.repos.BookRepository;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController implements BookApi {

    private final BookRepository bookRepository;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=1")
    @Override
    public ResponseEntity<Flux<Book>> addBooks(
            @RequestHeader(value = "X-API-VERSION") @Nullable String apiVersion,
            @RequestHeader(value = "X-CORRELATION-ID") @NotNull String correlationId,
            @Valid @RequestBody BookRequest bookRequest) {
        log.info("Add books request with correlation id {} and content {}", correlationId, bookRequest);
        Flux<Book> bookFlux = bookRequest.validateBookRequest()
                .map(b->ModelTransformer.getBook(b,null))
                .flatMap(this.bookRepository::save);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookFlux);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=1")
    @Override
    public Mono<Book> getBook(
           @RequestHeader (value = "X-API-VERSION") @Nullable String apiVersion,
           @RequestHeader (value = "X-CORRELATION-ID") @NotNull String correlationId,
           @PathVariable("id") long id){
        log.info("Get book request with correlation id {} and book id {}", correlationId, id);
        return this.bookRepository.findById(id).switchIfEmpty(Mono.error(new BookNotFoundException(id)));
    }


    @DeleteMapping(value = "/{id}", headers = "X-API-VERSION=1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public Mono<Void> deleteBook(
            @RequestHeader (value = "X-API-VERSION") @Nullable String apiVersion,
            @RequestHeader (value = "X-CORRELATION-ID") @NotNull String correlationId,
            @PathVariable("id") long id){
        log.info("Delete book request with correlation id {} and book id {}", correlationId, id);
        return this.bookRepository.deleteById(id);
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public Mono<Book> updateBook(
            @Parameter(description = "api version to be passed in the request" ) @RequestHeader (value = "X-API-VERSION") @Nullable String apiVersion,
            @Parameter(description = "correlation Id of the request" ) @RequestHeader (value = "X-CORRELATION-ID") @NotNull String correlationId,
            @PathVariable("id") long id, @Valid @RequestBody @NotNull BookModel book){
        log.info("Get book request with correlation id {}, book id {}, and content {}", correlationId, id, book);
        return this.bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)))
                .flatMap(e->Mono.just(book))
                .map(b->ModelTransformer.getBook(b,id))
                .flatMap(this.bookRepository::save);
    }




}
