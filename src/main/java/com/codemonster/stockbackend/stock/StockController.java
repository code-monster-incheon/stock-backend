package com.codemonster.stockbackend.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks")
    public Dto.StockParamResponse getStocks() throws IOException
    {
        List<Dto.StockParam> stockParamList = stockService.getStocks();
        return Dto.StockParamResponse
                .builder()
                .stockParamList(stockParamList)
                .build();
    }

}
