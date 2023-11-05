package org.example.logic;

import org.example.models.catalog.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CompareData {

    /**
     * Метод находит новые товары, удаляет их из browserData.
     *
     * @param browserData data from browser
     * @param dbData      data from database
     * @return List of new cards
     */
    public List<Product> findNewProducts(Map<Integer, Product> browserData, Map<Integer, Product> dbData) {
        List<Product> newProducts = new ArrayList<>();

        for (Map.Entry<Integer, Product> entry : browserData.entrySet()) {
            if (!dbData.containsKey(entry.getKey())) {
                newProducts.add(entry.getValue());
                browserData.remove(entry.getKey());
            }
        }

        return newProducts;
    }

    /**
     * Метод находит карточки с измененной ценой.
     *
     * @param browserData data from browser
     * @param dbData      data from database
     * @return new list of cards in which the price has changed
     */
    public List<Product> findProductsWithChangedPrice(Map<Integer, Product> browserData, Map<Integer, Product> dbData) {
        List<Product> newPriceProducts = new ArrayList<>();

        for (Map.Entry<Integer, Product> entry : browserData.entrySet()) {
            var browserCard = entry.getValue();
            var dbCard = dbData.get(entry.getKey());
            if (dbCard.equals(browserCard) && !Objects.equals(dbCard.getSalePriceU(), browserCard.getSalePriceU())) {
                dbCard.setPriceHistory(dbCard.getSalePriceU());
                dbCard.setSalePriceU(browserCard.getSalePriceU());
                newPriceProducts.add(dbCard);
            }
        }

        return newPriceProducts;
    }


    /**
     * Метод находит карточки которые есть в бд но нет в браузере.
     * Считаем такие карточки снятыми с продажи.
     *
     * @param browserData data from browser
     * @param dbData      data from database
     * @return new list of cards
     */
    public List<Product> findProductsWhichAreNoLongerOnSale(Map<Integer, Product> browserData, Map<Integer, Product> dbData) {
        List<Product> deletedProducts = new ArrayList<>();
        for (Map.Entry<Integer, Product> entry : dbData.entrySet()) {
            if (!browserData.containsKey(entry.getKey())) {
                deletedProducts.add(entry.getValue());
            }
        }

        return deletedProducts;
    }


    public List<Product> calculateDiscount(List<Product> products) {
        List<Product> discount = new ArrayList<>();
        for (Product product : products) {
            double lastHistoryPrice = (double) product.getPriceHistoryAsInt().getLast() / 100;
            double price = (double) product.getSalePriceU() / 100;

            if (lastHistoryPrice > price) {
                product.setDiscount(calcPercent(price, lastHistoryPrice));
                product.setAvgDiscount(calcAvgPercent(price, product.getPriceHistoryAsInt()));
                if (product.getDiscount() > 25) {
                    discount.add(product);
                }
            }
        }

        return discount;
    }

    private Double calcPercent(double price, double lastHistoryPrice) {
        double delta = lastHistoryPrice - price;
        return round(delta / lastHistoryPrice * 100);

    }

    private Double calcAvgPercent(double price, List<Integer> historyPrices) {
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
