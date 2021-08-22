package com.store.book.controller;


import com.store.book.model.*;
import com.store.book.repos.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureWebTestClient
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
    void addBooks_When_Invalid_BookType_Test() {
        //Arrange
        BookRequest bookRequest = getBookRequest(true);
        List<BookModel> books = bookRequest.getBooks();
        BookModel bookModel = books.get(0);
        bookModel.setType("MATHS");

        //Act
        this.client.post()
                .uri("/books/")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bookRequest))
                .exchange()
                .expectStatus().is4xxClientError(); // Assert
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

    @Test
    void  updateBook_When_Invalid_BookType_Test() {
        BookModel bookModel = getBookModel(true);
        bookModel.setType("MATHS");
        //Act
        this.client.put()
                .uri("/books/1")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue( getBookModel(false)))
                .exchange()
                .expectStatus().is4xxClientError(); // Assert
    }

    @Test
    void checkoutBooksSuccessTest() {
        //Arrange
        Mockito.when(this.bookRepository.findById(Mockito.anyLong())).thenReturn(Mono.just(getBookTech()));

        //Act
        this.client.post()
                .uri("/books/checkout")
                .header("X-API-VERSION", "1")
                .header("X-CORRELATION-ID", "123")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getCheckoutRequest()))
                .exchange()
                .expectStatus().is5xxServerError();

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


    private Book getBookTech() {
        Book book = new Book();
        book.setId(1l);
        book.setName("No Rules Rules");
        book.setDescription("Netflix and the Culture of Reinvention");
        book.setAuthor("Reed Hastings, Erin Meyer");
        book.setType("TECHNOLOGY");
        book.setPrice(new BigDecimal("1550.00"));
        book.setIsbn("ISBN-13: 979-0-596-52068-3");
        return book;
    }

    private CheckoutRequest getCheckoutRequest() {
        CheckoutRequest checkoutReq = new CheckoutRequest();
        List<Checkout> bookIds = new ArrayList<>();
        Checkout checkout = new Checkout();
        checkout.setBookId(1l);
        bookIds.add(checkout);
        checkoutReq.setBookIds(bookIds);
        checkoutReq.setPromoCode("OFFERFIC10");
        return checkoutReq;
    }
}
