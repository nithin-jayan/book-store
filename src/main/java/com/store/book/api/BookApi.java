package com.store.book.api;

import com.store.book.entity.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;
import javax.validation.constraints.NotNull;

import java.util.List;

public interface BookApi {
    @Operation(summary = "Add Books", operationId = "addBooks", description = "Add books to online book store" , tags={ "Book"})
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "405", description = "Method Not Allowed")
    @ApiResponse(responseCode = "409", description = "Conflict")
    @ApiResponse(responseCode = "415", description = "Unsupported Media Type")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ApiResponse(responseCode = "503", description = "Service Unavailable")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    default Flux<Book> addBooks(
            @Parameter(description = "api version to be passed in the request" ) @RequestHeader (value = "X-API-VERSION") @Nullable String apiVersion,
            @Parameter(description = "correlation Id of the request" ) @RequestHeader (value = "X-CORRELATION-ID") @NotNull String correlationId,
            @RequestBody List<Book> books){
        return  Flux.empty();
    }
}
