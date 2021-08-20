package com.store.book.repos;

import com.store.book.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("SELECT * FROM book WHERE name = :name")
    Flux<Book> findByName(String name);
}
