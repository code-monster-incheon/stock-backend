package com.codemonster.stockbackend.interfaces.stock;

import com.codemonster.stockbackend.application.StockThemeService;
import com.codemonster.stockbackend.domain.stock.Stock;
import com.codemonster.stockbackend.application.StockService;
import com.codemonster.stockbackend.domain.stocktheme.StockTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class StockController {

    @Autowired
    StockThemeService stockThemeService;

    @GetMapping("/stocks")
    public List<StockTheme> getStocks() throws IOException {
        return stockThemeService.getStockThemes();
    }
}
