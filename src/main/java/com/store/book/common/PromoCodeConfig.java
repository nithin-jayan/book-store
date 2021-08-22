package com.store.book.common;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@ConfigurationProperties(prefix = "promocodes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeConfig {
    private List<Promo> promos;
}
