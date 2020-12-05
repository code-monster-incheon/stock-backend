package com.codemonster.stockbackend.domain.stocktheme;

import com.codemonster.stockbackend.domain.stock.Stock;
import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter @ToString
public class StockTheme {
    String name;
    String dailyPriceChangeRatio;
    List<Stock> stocks;
}
