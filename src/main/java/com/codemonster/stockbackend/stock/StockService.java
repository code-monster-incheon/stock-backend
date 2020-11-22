package com.codemonster.stockbackend.stock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class StockService {


    Document document;
    String baseUrl = "https://finance.naver.com";

    HashMap<String, ArrayList<Stock>> getStocks() throws IOException {
        ArrayList<String> stockCategoryUrls = getStockCategoryUrls();
        ArrayList<String> stockCategoryNames = getStockCategoryNames();

        HashMap<String, ArrayList<Stock>> stocks = getStocksByCategory(stockCategoryUrls, stockCategoryNames);

        return stocks;
    }

    private ArrayList<String> getStockCategoryNames() throws IOException {
        String stockThemeUrl = "/sise/theme.nhn";
        document = Jsoup.connect(baseUrl + stockThemeUrl).get();

        ArrayList<String> stockCategoryNames = new ArrayList<>();
        Elements elements = document.select("td.col_type1");

        for(Element element : elements){
            String stockCategoryName = element.text();
            stockCategoryNames.add(stockCategoryName);
        }

        return stockCategoryNames;
    }

    private ArrayList<String> getStockCategoryUrls() throws IOException {
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

    private HashMap<String, ArrayList<Stock>> getStocksByCategory(ArrayList<String> stockCategoryUrls, ArrayList<String> stockCategoryNames) throws IOException {
        HashMap<String, ArrayList<Stock>> results = new HashMap<>();

        for (int idx = 0; idx < stockCategoryUrls.size(); idx++){
            String stockCategoryUrl = stockCategoryUrls.get(idx);
            String stockCategoryName = stockCategoryNames.get(idx);
            document = Jsoup.connect(stockCategoryUrl).get();

            ArrayList<String> stockNames = new ArrayList<>();
            ArrayList<String> stockChangeRatios = new ArrayList<>();
            ArrayList<Stock> stocks = new ArrayList<>();


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

            // first element is category's total change ratio, NOT stock's
            stockChangeRatios.remove(0);

            for(int i = 0; i < stockNames.size(); i++){
                Stock stock = new Stock(stockNames.get(i), stockChangeRatios.get(i));
                stocks.add(stock);
            }

            results.put(stockCategoryName, stocks);
        }

        return results;
    }
}
