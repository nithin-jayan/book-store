package com.store.book.repos;

import com.store.book.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class BookRepositoryTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void bookSaveTest(){
        //Arrange
        Book book = getBook();

        //Act
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(databaseClient, H2Dialect.INSTANCE);
        template.insert(Book.class).using(book).then().as(StepVerifier::create).verifyComplete();
        Flux<Book> bookFlux = bookRepository.findByName(book.getName());

        //Assert
        bookFlux.as(StepVerifier::create).assertNext(actual -> {
                    assertThat(actual.getName()).isEqualTo("Wings of Fire");
                    assertThat(actual.getAuthor()).isEqualTo("APJ Abdul Kalam");
                }).verifyComplete();

        //Teardown
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
    }

    @Test
    void findByIdTest(){
        //Arrange
        Book book = getBook();

        //Act
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
        Mono<Book> bookMono = bookRepository.save(book).flatMap(b -> bookRepository.findById(b.getId()));

        //Assert
        bookMono.as(StepVerifier::create).assertNext(actual -> {
            assertThat(actual.getName()).isEqualTo("Wings of Fire");
            assertThat(actual.getAuthor()).isEqualTo("APJ Abdul Kalam");
        }).verifyComplete();

        //Teardown
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
    }

    @Test
    void findByIdsTest(){
        //Arrange
        Book book1 = getBook();
        Book book2 =  getBook2();


        //Act
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
        bookRepository.save(book1).concatWith(bookRepository.save(book2)).as(StepVerifier::create).expectNextCount(2).verifyComplete();
        Flux<Book> bookFlux = bookRepository.findByIds(Arrays.asList(1l,2l));

        //Assert
        bookFlux.as(StepVerifier::create).expectNextCount(2).verifyComplete();

        //Teardown
        bookRepository.deleteAll().as(StepVerifier::create).verifyComplete();
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

    private Book getBook2() {
        Book book = new Book();
        book.setName("An Autobiography");
        book.setDescription("Nehru's autobiography");
        book.setAuthor("Jawaharlal Nehru");
        book.setType("AUTOBIOGRAPHY");
        book.setPrice(new BigDecimal("250.50"));
        book.setIsbn("9788173711467");
        return book;
    }

}
