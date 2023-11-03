package org.example.logic;

import org.example.models.catalog.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompareData {

    /**
     * Метод находит новые товары, удаляет их из browserData.
     * @param browserData data from browser
     * @param dbData data from database
     * @return List of new cards
     */
    public List<Product> findNewProducts(List<Product> browserData, List<Product> dbData) {
        List<Product> newProducts = new ArrayList<>();
        boolean isFinded;

        for (Product browserCard : browserData) {
            isFinded = false;
            for (Product dbCard : dbData) {
                if (dbCard.equals(browserCard)) {
                    isFinded = true;
                    break;
                }
            }
            if (!isFinded) {
                newProducts.add(browserCard);
            }
        }

        for (Product product : newProducts) {
            browserData.remove(product);
        }

        return newProducts;
    }

    /**
     *  Метод находит карточки с измененной ценой.
     * @param browserData data from browser
     * @param dbData data from database
     * @return new list of cards in which the price has changed
     */
    public List<Product> findProductsWithChangedPrice(List<Product> browserData, List<Product> dbData) {
        List<Product> newPriceProducts = new ArrayList<>();

        for (Product browserCard : browserData) {
            for (Product dbCard : dbData) {
                if (dbCard.equals(browserCard) && !Objects.equals(dbCard.getSalePriceU(), browserCard.getSalePriceU())) {
                    dbCard.setPriceHistory(dbCard.getSalePriceU());
                    dbCard.setSalePriceU(browserCard.getSalePriceU());
                    newPriceProducts.add(dbCard);
                    break;
                }
            }
        }

        return newPriceProducts;
    }


    /**
     * Метод находит карточки которые есть в бд но нет в браузере.
     * Считаем такие карточки снятыми с продажи.
     * @param browserData data from browser
     * @param dbData data from database
     * @return new list of cards
     */
    public List<Product> findProductsWhichAreNoLongerOnSale(List<Product> browserData, List<Product> dbData) {
        List<Product> deletedProducts = new ArrayList<>();
        boolean isFinded;

        for (Product dbCard : dbData) {
            isFinded = false;
            for (Product browserCard : browserData) {
                if (browserCard.equals(dbCard)) {
                    isFinded = true;
                    break;
                }
            }
            if (!isFinded) {
                deletedProducts.add(dbCard);
            }
        }

        return deletedProducts;
    }


    public List<Product> calculateDiscount(List<Product> products) {
        List<Product> discount = new ArrayList<>();
        for(Product product: products){
            double lastHistoryPrice = (double) product.getPriceHistoryAsInt().getLast() / 100;
            double price = (double) product.getSalePriceU() / 100;

            if(lastHistoryPrice > price){
                 product.setDiscount(calcPercent(price, lastHistoryPrice));
                 product.setAvgDiscount(calcAvgPercent(price, product.getPriceHistoryAsInt()));
                 if(product.getDiscount() > 25){
                    discount.add(product);
                 }
            }
        }

        return discount.stream()
                .sorted((o1, o2)->o1.getDiscount().compareTo(o2.getDiscount()))
                .toList();
    }



    private Double calcPercent(double price, double lastHistoryPrice){
        double delta = lastHistoryPrice - price;
        double percent = round(delta / lastHistoryPrice * 100);

        return percent;
    }

    private Double calcAvgPercent(double price, List<Integer> historyPrices){
        double avgPrice = (historyPrices.stream()
                 .mapToDouble(Double::valueOf)
                 .sum() / historyPrices.size()) / 100;

        return calcPercent(price, avgPrice);
    }


    public static double round(double value) {
        int places = 2;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



}
/*  List<String> list = Arrays.asList(priceHistory.split(","));
        int lastPrice = Integer.parseInt(list.getLast()) / 100;
        int currentPrice = priceU / 100;

        if(lastPrice > currentPrice){
            return "DISCOUNTED: " +  (lastPrice - currentPrice);
        }else {
            return "increased " + (currentPrice - lastPrice);
        }*/