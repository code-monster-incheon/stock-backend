package com.codemonster.stockbackend.stock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockService {


    Document document;
    String baseUrl = "https://finance.naver.com";

    List<Dto.StockParam> getStocks() throws IOException {
        List<String> stockCategoryUrls = getStockCategoryUrls();
        List<String> stockCategoryNames = getStockCategoryNames();

        Map<String, List<Dto.Stock>> stocks = getStocksByCategory(stockCategoryUrls, stockCategoryNames);

        return stocks
                .entrySet()
                .stream()
                .map(stock-> Dto.StockParam.builder().title(stock.getKey()).stockList(stock.getValue()).build())
                .collect(Collectors.toList());
    }

    private List<String> getStockCategoryNames() throws IOException {

        String stockThemeUrl = "/sise/theme.nhn";
        document = Jsoup.connect(baseUrl + stockThemeUrl).get();

        Elements elements = document.select("td.col_type1");

        return elements.stream()
                .map(Element::text).collect(Collectors.toList());
    }

    private List<String> getStockCategoryUrls() throws IOException {
        String stockThemeUrl = "/sise/theme.nhn";
        document = Jsoup.connect(baseUrl + stockThemeUrl).get();

        ArrayList<String> stockCategoryUrls = new ArrayList<>();
        Elements elements = document.select("td.col_type1");

        for(Element element : elements){
            String url = element.select("a").attr("href");
            stockCategoryUrls.add(baseUrl + url);
        }

        return stockCategoryUrls;
    }

    private Map<String, List<Dto.Stock>> getStocksByCategory(
            List<String> stockCategoryUrls
            , List<String> stockCategoryNames) throws IOException {
        HashMap<String, List<Dto.Stock>> results = new HashMap<>();

        for (int idx = 0; idx < stockCategoryUrls.size(); idx++){
            String stockCategoryUrl = stockCategoryUrls.get(idx);
            String stockCategoryName = stockCategoryNames.get(idx);
            document = Jsoup.connect(stockCategoryUrl).get();

            ArrayList<String> stockNames = new ArrayList<>();
            ArrayList<String> stockChangeRatios = new ArrayList<>();
            List<Dto.Stock> stocks = new ArrayList<>();


            Elements elements = document.select("div.name_area > a");

            for (Element element : elements) {
                String stockName = element.text();
                stockNames.add(stockName);
            }


            elements = document.select("td.number");

            for (Element element : elements) {
                if (element.text().endsWith("%")) {
                    String changeRatio = element.text();
                    stockChangeRatios.add(changeRatio);
                }
            }

            stockChangeRatios.remove(0);

            for(int i = 0; i < stockNames.size(); i++){
                stocks.add(Dto.Stock.builder().name(stockNames.get(i)).changeRatio(stockChangeRatios.get(i)).build());
            }

            results.put(stockCategoryName, stocks);
        }

        return results;
    }

}
