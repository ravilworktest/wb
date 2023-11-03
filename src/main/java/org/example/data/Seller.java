package org.example.data;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    private static List<String> sellers = new ArrayList<>();
    private static boolean isLoad = false;

    public static String getNext() {
        loadSellersList();
        if (sellers.isEmpty()) {
            return null;
        }
        String id = sellers.getLast();
        sellers.remove(id);
        return id;
    }

    private static void loadSellersList() {
        if (!isLoad) {
            sellers.add("10598");
            sellers.add("276");
            sellers.add("466700");
            sellers.add("65605");
            sellers.add("51123");
            sellers.add("45298");
            sellers.add("1090976");
            sellers.add("23531");
            sellers.add("1312586");
            sellers.add("1201958");
            sellers.add("1374227");
            sellers.add("1342529");
            sellers.add("68808");
            sellers.add("35165");
            sellers.add("382438");
            sellers.add("48941");
            sellers.add("974");
            sellers.add("23266");
            sellers.add("5359");
            isLoad = true;
        }
    }


}
