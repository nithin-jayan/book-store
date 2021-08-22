package com.store.book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promo {
    private String promoCode;
    private String bookType;
    private String discount;
    private String expireDate;
}
