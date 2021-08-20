package com.store.book.api;

import com.store.book.model.Book;
import com.store.book.model.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BookApi {
    @Operation(summary = "Add Books", operationId = "addBooks", description = "Add books to online book store" , tags={ "Book"})
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Flux<Book>> addBooks(
            @Parameter(description = "api version to be passed in the request" ) @RequestHeader (value = "X-API-VERSION") @Nullable String apiVersion,
            @Parameter(description = "correlation Id of the request" ) @RequestHeader (value = "X-CORRELATION-ID") @NotNull String correlationId,
            @Valid @RequestBody  BookRequest bookRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Flux.empty());
    }
}
