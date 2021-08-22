package com.store.book.model;

import com.store.book.exception.ApiException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.store.book.common.BookConstants.CHECKOUT_EMPTY_CODE;
import static com.store.book.common.BookConstants.CHECKOUT_EMPTY_MSG;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CheckOutRequest", description = "Book checkOut request")
public class CheckoutRequest {

    @NotNull
    @Valid
    private List<Checkout> bookIds;

    private String promoCode;

    public Mono<CheckoutRequest> validateCheckOutRequest() {
        if(CollectionUtils.isEmpty(bookIds)){
            throw new ApiException(CHECKOUT_EMPTY_CODE, CHECKOUT_EMPTY_MSG);
        }
        return Mono.just(this);
    }
}
