package com.codemonster.stockbackend.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @GetMapping("/stocks")
    public HashMap<String, ArrayList<Stock>> getStocks() throws IOException {
        HashMap<String, ArrayList<Stock>> stocks = stockService.getStocks();
        return stocks;
    }
}
