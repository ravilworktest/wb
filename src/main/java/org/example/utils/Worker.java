package org.example.utils;

import org.example.data.Seller;
import org.example.database.DBConnection;
import org.example.logic.CompareData;
import org.example.models.catalog.Product;
import org.example.pageLoader.PageLoader;

import java.util.Collections;
import java.util.List;

public class Worker extends Thread {

    DBConnection connection = new DBConnection();

   @Override
    public void run(){
       String sellerId = null;
       System.out.println("Worker started " + Thread.currentThread());
       do {
           sellerId = Seller.getNext();
           if(sellerId == null){
               System.out.println("Worker stoped " + Thread.currentThread());
               break;
           }
           proccess(Collections.singletonList(sellerId));
       }while (sellerId != null);

    }

    public void proccess(List<String> sellers){
        connection.dbConnect();

        PageLoader pl = new PageLoader();
        List<Product> browserData = pl.loadBySeller(sellers);
        List<Product> dbData = connection.selectProductBySeller(sellers);

        CompareData compareData = new CompareData();
        List<Product> newCards = compareData.findNewProductss(browserData, dbData);
        List<Product> cardWithChangedPrice = compareData.findProductsWithChangedPrice(browserData, dbData);
        List<Product> discountedProducts = compareData.calculateDiscount(cardWithChangedPrice);
        connection.insertDiscountProducts(discountedProducts);
        List<Product> cardsWhichAreNoLongerOnSale = compareData.findProductsWhichAreNoLongerOnSale(browserData, dbData);
        connection.insertProducts(newCards);
        connection.updateProduct(cardWithChangedPrice);
    }









/*
    private void test(List<String> sellers)   {
        long start = startTimer();
        Loader loader = new Loader();
        stopTimer(start, "BROWSER_START");

        start = startTimer();
        DBConnection connection = new DBConnection();
        connection.dbConnect();
        stopTimer(start, "DB_CONNECT");

        for (int i = 0; i < 2; i++) {
            sellers = new ArrayList<>();
            sellers.add("68808");
            sellers.add("35165");
            sellers.add("382438"); // нет страниц
            sellers.add("48941");  // нет страниц
            // sellers.add("5359");  // ТВОЕ > 9 страниц
            // sellers.add("974");

             start = startTimer();
            List<Card> browserData = loader.parseBySeller(sellers);
            stopTimer(start, "LOAD_FROM_BROWSER");
            // loader.closeBrowser();

            start = startTimer();
            List<Card> dbData = connection.selectBySeller(sellers);
            stopTimer(start, "SELECT_BY_SELLER");

            CompareData compareData = new CompareData();
            start = startTimer();
            List<Card> newCards = compareData.findNewCards(browserData, dbData);
            stopTimer(start, "FIND_NEW_CARD");

            start = startTimer();
            List<Card> cardWithChangedPrice = compareData.findCardWithChangedPrice(browserData, dbData);
            stopTimer(start, "FIND_CARD_WITH_CHANGED_PRICE");

            start = startTimer();
            List<Card> cardsWhichAreNoLongerOnSale = compareData.findCardsWhichAreNoLongerOnSale(browserData, dbData);
            stopTimer(start, "FIND_NO_SALE_CARD");

            System.out.println("cardsWhichAreNoLongerOnSale: " + cardsWhichAreNoLongerOnSale.size());

            System.out.println("newCards: " + newCards.size());
            System.out.println("cardWithChangedPrice: " + cardWithChangedPrice.size());
            cardWithChangedPrice.forEach(c -> System.out.println(c.print()));

            start = startTimer();
            connection.insert(newCards);
            stopTimer(start, "INSERT");

            start = startTimer();
            connection.update(cardWithChangedPrice);
            stopTimer(start, "UPDATE");
        }
        loader.closeBrowser();
    }

 */
}
