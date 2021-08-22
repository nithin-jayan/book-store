package com.store.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "checkout", description = "Book checkout model")
public class Checkout {
    @Schema(description = "Book Id")
    @NotNull
    private Long bookId;

}
