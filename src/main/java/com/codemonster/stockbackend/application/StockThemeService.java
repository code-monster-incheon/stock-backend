package com.codemonster.stockbackend.application;

import com.codemonster.stockbackend.domain.stock.Stock;
import com.codemonster.stockbackend.domain.stocktheme.StockTheme;
import com.codemonster.stockbackend.interfaces.common.StockConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockThemeService {

    @Autowired
    StockService stockService;

    public List<StockTheme> getStockThemes() throws IOException {
        Document document = Jsoup.connect(StockConfig.stockThemeUrl).get();

        List<StockTheme> stockThemes = new ArrayList<>();

        List<String> stockThemeNames = getStockThemeNames(document);
        List<String> stockThemeDailyPriceChangeRatios = getStockThemeDailyPriceChangeRatios(document);
        List<String> stockThemeUrls = getStockThemeUrls(document);

        for(int i = 0; i < stockThemeNames.size(); i++){
            String stockThemeName = stockThemeNames.get(i);
            String stockThemeDailyPriceChangeRatio = stockThemeDailyPriceChangeRatios.get(i);
            String stockThemeUrl = stockThemeUrls.get(i);

            List<Stock> stocks = stockService.getStocksByTheme(stockThemeUrl);

            StockTheme stockTheme = StockTheme.builder()
                    .name(stockThemeName)
                    .dailyPriceChangeRatio(stockThemeDailyPriceChangeRatio)
                    .stocks(stocks)
                    .build();

            stockThemes.add(stockTheme);
        }

        return stockThemes;
    }

    private List<String> getStockThemeNames(Document document) {
         List<String> stockThemeNames = new ArrayList<>();

         String targetHtmlTag = "td.col_type1";
         Elements elements = document.select(targetHtmlTag);

        for(Element element : elements){
            String stockCategoryName = element.text();
            stockThemeNames.add(stockCategoryName);
        }

        return stockThemeNames;
    }

    private List<String> getStockThemeUrls(Document document) {
        List<String> stockThemeUrls = new ArrayList<>();

        String targetHtmlTag = "td.col_type1 > a";
        Elements elements = document.select(targetHtmlTag);

        for(Element element : elements){
            String stockThemeUrl = element.attr("href");
            stockThemeUrls.add(StockConfig.baseUrl + stockThemeUrl);
        }

        return stockThemeUrls;
    }

    private List<String> getStockThemeDailyPriceChangeRatios(Document document) {
        List<String> stockThemeDailyPriceChangeRatios = new ArrayList<>();

        String targetHtmlTag = "td.col_type2";
        Elements elements = document.select(targetHtmlTag);

        for(Element element : elements){
            String stockThemeDailyPriceChangeRatio = element.text();
            stockThemeDailyPriceChangeRatios.add(stockThemeDailyPriceChangeRatio);
        }

        return stockThemeDailyPriceChangeRatios;
    }
}
