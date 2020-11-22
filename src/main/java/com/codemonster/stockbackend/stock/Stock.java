package com.codemonster.stockbackend.stock;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Builder
@Getter @Setter @ToString
public class Stock {
    String name;
    String changeRatio;
}
