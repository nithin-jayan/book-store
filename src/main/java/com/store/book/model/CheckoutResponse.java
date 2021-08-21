package com.store.book.model;

import com.store.book.exception.ApiException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

import static com.store.book.common.BookConstants.CHECKOUT_EMPTY_CODE;
import static com.store.book.common.BookConstants.CHECKOUT_EMPTY_MSG;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CheckOutResponse", description = "Book checkOut response")
public class CheckoutResponse {


    private List<Checkout> bookIds;

    private BigDecimal totalPrice;
}
