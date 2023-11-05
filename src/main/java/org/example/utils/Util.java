package org.example.utils;

import org.example.models.catalog.Product;

import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Util {
    public static void delay(int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static long startTimer() {
        return System.currentTimeMillis();
    }

    public static void stopTimer(long start, String msg) {
        long end = System.currentTimeMillis();
        System.out.println(msg + " " + ((end - start) / 1000d) % 60 + " seconds");
    }

    public static void printProductCount(Map<Integer,Product> products) {
        products.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(groupingBy(Product::getSupplier, counting()))
                .entrySet()
                .forEach(e -> System.out.printf("Seller: %s cards: %s\r\n", e.getKey(), e.getValue()));
    }
   /* public static void printProductCount(List<Product> products) {
        products.stream()
                .collect(groupingBy(Product::getSupplier, counting()))
                .entrySet()
                .forEach(e -> System.out.printf("Seller: %s cards: %s\r\n", e.getKey(), e.getValue()));
    }*/

    public  static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
