package com.codemonster.stockbackend.application;

import com.codemonster.stockbackend.domain.stock.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StockService {

    List<Stock> getStocksByTheme(String stockThemeUr) throws IOException {
        Document document = Jsoup.connect(stockThemeUr).get();

        List<Stock> stocks = new ArrayList<>();

        List<String> stockNames = getStockNames(document);
        List<String> stockDailyPriceChangeRatios = getStockDailyPriceChangeRatios(document);

        for(int i = 0; i < stockNames.size(); i++){
            String stockName = stockNames.get(i);
            String stockDailyPriceChangeRatio = stockDailyPriceChangeRatios.get(i);

            Stock stock = Stock.builder()
                    .name(stockName)
                    .dailyPriceChangeRatio(stockDailyPriceChangeRatio)
                    .build();

            stocks.add(stock);
        }

        return stocks;
    }

    private List<String> getStockNames(Document document) {
        List<String> stockNames = new ArrayList<>();

        String targetHtmlTag = "div.name_area > a";
        Elements elements = document.select(targetHtmlTag);

        for(Element element : elements){
            String stockCategoryName = element.text();
            stockNames.add(stockCategoryName);
        }

        return stockNames;
    }

    private List<String> getStockDailyPriceChangeRatios(Document document) {
        List<String> stockDailyPriceChangeRatios = new ArrayList<>();

        String targetHtmlTag = "td.number";
        Elements elements = document.select(targetHtmlTag);

        for (Element element : elements) {
            if (element.text().endsWith("%")) {
                String stockDailyPriceChangeRatio = element.text();
                stockDailyPriceChangeRatios.add(stockDailyPriceChangeRatio);
            }
        }

        // 첫 번째 html tag 는 주식테마의 등락률 이며 개별 주식 등락률이 아님
        stockDailyPriceChangeRatios.remove(0);

        return stockDailyPriceChangeRatios;
    }
}
