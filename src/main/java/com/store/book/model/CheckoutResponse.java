package com.store.book.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CheckOutResponse", description = "Book checkOut response")
public class CheckoutResponse {


    private List<Checkout> bookIds;

    private BigDecimal totalPrice;
}
