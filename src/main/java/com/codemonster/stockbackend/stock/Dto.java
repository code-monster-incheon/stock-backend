package com.codemonster.stockbackend.stock;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Dto {

    private Dto()
    {
        throw new AssertionError("Dto outer class");
    }

    @Getter
    @Setter
    public static class Stock
    {
        String name;
        String changeRatio;

        @Builder
        public Stock(String name, String changeRatio) {
            this.name = name;
            this.changeRatio = changeRatio;
        }
    }

    @Getter
    @Setter
    public static class StockParam
    {
        private String title;
        private List<Dto.Stock> stockList = new ArrayList<>();

        @Builder
        public StockParam(String title, List<Dto.Stock> stockList)
        {
            this.title = title;
            this.stockList = stockList;
        }
    }

    @Getter
    @Setter
    public static class StockParamResponse
    {
        private List<Dto.StockParam> stockParamList = new ArrayList<>();

        @Builder
        public StockParamResponse(List<Dto.StockParam> stockParamList)
        {
            this.stockParamList = stockParamList;
        }
    }
}
