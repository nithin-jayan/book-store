package com.store.book.repos;

import com.store.book.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("SELECT * FROM book WHERE name = :name")
    Flux<Book> findByName(String name);

    @Query("SELECT * FROM book WHERE id = :id")
    Mono<Book> findById(long id);

    @Query("SELECT * FROM book WHERE id in (:ids)")
    Flux<Book> findByIds(List<Long> ids);
}
