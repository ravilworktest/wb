package org.example;

import org.example.database.DBConnection;
import org.example.logic.CompareData;
import org.example.models.catalog.Product;
import org.example.pageLoader.PageLoader;
import org.example.utils.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.utils.Util.startTimer;
import static org.example.utils.Util.stopTimer;

public class Main {

    public static void main(String[] args) {
      threadTest();
        //   debug();
    }

    public static void threadTest(){
        for(int i = 0; i < 4; i ++ ){
             new Worker().start();
        }
    }

    public static void debug(){
        PageLoader pl = new PageLoader();
        DBConnection connection = new DBConnection();
        connection.dbConnect();

        List<String> sellers = new ArrayList<>();

        sellers.add("10598");
        sellers.add("276");
     //   sellers.add("466700");
      /*     sellers.add("65605");
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
        sellers.add("382438"); // нет страниц
        sellers.add("48941");  // нет страниц
        sellers.add("974");    // OMSA > 9 страниц
        sellers.add("23266");  // РИВ ГОШ > 9 страниц
        sellers.add("5359");   // ТВОЕ > 9 страниц*/

        long t = startTimer();
        Map<Integer,Product> browserData = pl.loadBySeller(sellers);
        stopTimer(t,"GET PAGES");

        t = startTimer();
        Map<Integer,Product> dbData = connection.selectProductBySeller(sellers);
        stopTimer(t, "SELECT_BY_SELLER");

        CompareData compareData = new CompareData();
        t = startTimer();
        List<Product> newCards = compareData.findNewProducts(browserData, dbData);
        System.out.println("newCards: " + newCards.size());
        stopTimer(t, "FIND_NEW_CARD");

        t = startTimer();
        List<Product> cardWithChangedPrice = compareData.findProductsWithChangedPrice(browserData, dbData);
        System.out.println("cardWithChangedPrice: " + cardWithChangedPrice.size());
        List<Product> discountedProducts = compareData.calculateDiscount(cardWithChangedPrice);
        System.out.println("cardWithDiscount: " + discountedProducts.size());
        stopTimer(t, "FIND_CARD_WITH_CHANGED_PRICE");

        t = startTimer();
        List<Product> cardsWhichAreNoLongerOnSale = compareData.findProductsWhichAreNoLongerOnSale(browserData, dbData);
        System.out.println("cardsWhichAreNoLongerOnSale: " + cardsWhichAreNoLongerOnSale.size());
        stopTimer(t, "FIND_NO_SALE_CARD");

        t = startTimer();
        connection.insertProducts(newCards);
        stopTimer(t, "INSERT");

        t = startTimer();
        connection.updateProduct(cardWithChangedPrice);
        stopTimer(t, "UPDATE");

        t = startTimer();
        connection.insertDiscountProducts(discountedProducts);
        stopTimer(t, "INSERT SELL CARDS");

        connection.dbClose();

    }
}


/*
    List<String> sellers = new ArrayList<>();
        sellers.add("68808");
        sellers.add("35165");
        sellers.add("382438"); // нет страниц
        sellers.add("48941");  // нет страниц
        TestStart t = new TestStart(sellers);
        t.start();

        List<String> sellers2 = new ArrayList<>();
        sellers2.add("5359");  // ТВОЕ > 9 страниц
TestStart t2 = new TestStart(sellers2);
        t2.start();

                List<String> sellers3 = new ArrayList<>();
        sellers3.add("974");  //  OMSA > 9 страниц
        TestStart t3 = new TestStart(sellers3);
        t3.start();

        List<String> sellers4 = new ArrayList<>();
        sellers4.add("23266");  // РИВ ГОШ > 9 страниц
        TestStart t4 = new TestStart(sellers4);
        t4.start();

        List<String> sellers5 = new ArrayList<>();
        sellers5.add("35548");
        TestStart t5 = new TestStart(sellers5);
        t5.start();

 long start = start();
        Loader loader = new Loader();
        stop(start, "BROWSER_START");

        start = start();
        DBConnection connection = new DBConnection();
        connection.dbConnect();
        stop(start, "DB_CONNECT");


        for (int i = 0; i < 2; i++) {
            List<String> sellers = new ArrayList<>();
            sellers.add("68808");
            sellers.add("35165");
             sellers.add("382438"); // нет страниц
            sellers.add("48941");  // нет страниц
            sellers.add("5359");  // ТВОЕ > 9 страниц
           sellers.add("974");  //  OMSA > 9 страниц

            start = start();
                    List<Card> browserData = loader.parseBySeller(sellers);
        stop(start, "LOAD_FROM_BROWSER");
        // loader.closeBrowser();

        start = start();
        List<Card> dbData = connection.selectBySeller(sellers);
        stop(start, "SELECT_BY_SELLER");

        CompareData compareData = new CompareData();
        start = start();
        List<Card> newCards = compareData.findNewCards(browserData, dbData);
        stop(start, "FIND_NEW_CARD");

        start = start();
        List<Card> cardWithChangedPrice = compareData.findCardWithChangedPrice(browserData, dbData);
        stop(start, "FIND_CARD_WITH_CHANGED_PRICE");

        start = start();
        List<Card> cardsWhichAreNoLongerOnSale = compareData.findCardsWhichAreNoLongerOnSale(browserData, dbData);
        stop(start, "FIND_NO_SALE_CARD");

        System.out.println("cardsWhichAreNoLongerOnSale: " + cardsWhichAreNoLongerOnSale.size());

        System.out.println("newCards: " + newCards.size());
        System.out.println("cardWithChangedPrice: " + cardWithChangedPrice.size());
        cardWithChangedPrice.forEach(c -> System.out.println(c.print()));

        start = start();
        connection.insert(newCards);
        stop(start, "INSERT");

        start = start();
        connection.update(cardWithChangedPrice);
        stop(start, "UPDATE");
        }
        loader.closeBrowser();*/