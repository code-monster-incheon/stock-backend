package com.codemonster.stockbackend.domain.stock;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter @ToString
public class Stock {
    String name;
    String dailyPriceChangeRatio;
}
