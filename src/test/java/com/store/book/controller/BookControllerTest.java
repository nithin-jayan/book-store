package com.store.book.controller;

import com.store.book.model.Book;
import com.store.book.model.BookModel;
import com.store.book.model.BookRequest;
import com.store.book.repos.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class BookControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void addBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.save(Mockito.any())).thenReturn(Mono.just(getBook()));

        //Act
       this.client.post()
                .uri("/books/")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getBookRequest(true)))
                .exchange()
                .expectStatus().isCreated() // Assert
                .expectBody()
                .jsonPath("$[0]name").isEqualTo("Wings of Fire");

    }

    @Test
    void addBooks_When_Invalid_Isbn_Test() {
        //Act
        this.client.post()
                .uri("/books/")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue( getBookRequest(false)))
                .exchange()
                .expectStatus().isBadRequest(); // Assert
    }

    @Test
    void addBooks_When_BookModel_Empty_Test() {
        //Act
        this.client.post()
                .uri("/books/")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue( getBookRequestWithEmptyBookModel(false)))
                .exchange()
                .expectStatus().is4xxClientError() // Assert
                .expectBody()
                .jsonPath("$.code").isEqualTo("1001");

    }

    @Test
    void getBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(getBook()));

        //Act
        this.client.get()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("AUTOBIOGRAPHY");

    }

    @Test
    void getAllBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.findAll()).thenReturn(Flux.just(getBook()));

        //Act
        this.client.get()
                .uri("/books/")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("AUTOBIOGRAPHY");

    }

    @Test
    void getBooksNotFoundTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.empty());

        //Act
        this.client.get()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void deleteBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(getBook()));
        Mockito.when(this.bookRepository.deleteById(Mockito.anyLong())).thenReturn(Mono.empty());

        //Act
        this.client.delete()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    void deleteBooksNotFoundTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.empty());
        Mockito.when(this.bookRepository.deleteById(Mockito.anyLong())).thenReturn(Mono.empty());

        //Act
        this.client.get()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void updateBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(getBook()));
        Mockito.when(this.bookRepository.save(Mockito.any())).thenReturn(Mono.just(getBook()));

        //Act
        this.client.put()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getBookModel(true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.type").isEqualTo("AUTOBIOGRAPHY");

    }

    @Test
    void updateBook_sWhenId_returnEmpty_Book_Fail_Test() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.empty());
        Mockito.when(this.bookRepository.save(Mockito.any())).thenReturn(Mono.just(getBook()));

        //Act
        this.client.put()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getBookModel(true)))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void  updateBook_When_Invalid_Isbn_Test() {
        //Act
        this.client.put()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue( getBookModel(false)))
                .exchange()
                .expectStatus().isBadRequest(); // Assert
    }

    private BookRequest getBookRequest(boolean success) {
        BookRequest bookRequest = new BookRequest();
        List<BookModel> books = new ArrayList<>();
        books.add(getBookModel(success));
        bookRequest.setBooks(books);
        return bookRequest;
    }

    private BookRequest getBookRequestWithEmptyBookModel(boolean success) {
        BookRequest bookRequest = new BookRequest();
        List<BookModel> books = new ArrayList<>();
        bookRequest.setBooks(books);
        return bookRequest;
    }

    private BookModel getBookModel(boolean success) {
        BookModel book = new BookModel();
        book.setName("Wings of Fire");
        book.setDescription("Kalam's autobiography");
        book.setAuthor("APJ Abdul Kalam");
        book.setType("AUTOBIOGRAPHY");
        book.setPrice(new BigDecimal("350.50"));
        book.setIsbn(success ? "ISBN-13: 978-0-596-52068-7": "SBN-13: 978-0-596");
        return book;
    }

    private Book getBook() {
        Book book = new Book();
        book.setName("Wings of Fire");
        book.setDescription("Kalam's autobiography");
        book.setAuthor("APJ Abdul Kalam");
        book.setType("AUTOBIOGRAPHY");
        book.setPrice(new BigDecimal("350.50"));
        book.setIsbn("9788173711466");
        return book;
    }
}
