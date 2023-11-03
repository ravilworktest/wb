package org.example.models;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Card {
    private String seller;
    private int article;
    private int price;
    private String label;
    private String priceHistory;

    public Card(int article, int price, String label) {
        this.article = article;
        this.price = price;
        this.label = label;
    }
    public Card(String seller, int article, int price, String label, String priceHistory) {
        this.seller = seller;
        this.article = article;
        this.price = price;
        this.label = label;
        this.priceHistory = priceHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return getArticle() == card.getArticle()
               // && getPrice() == card.getPrice()
               // && getOldPrice() == card.getOldPrice()
                && Objects.equals(getSeller(), card.getSeller())
              //  && Objects.equals(getLabel(), card.getLabel())
              //  && Objects.equals(getPriceHistory(), card.getPriceHistory())
                ;
    }

    @Override
    public int hashCode() {
      //  return Objects.hash(getSeller(), getArticle(), getPrice(), getOldPrice(), getLabel(), getPriceHistory());
          return Objects.hash(getSeller(), getArticle());
    }

    public String getSeller() {
        return seller;
    }

    public int getArticle() {
        return article;
    }

    public int getPrice() {
        return price;
    }

    public String getLabel() {
        return label;
    }

    public String getPriceHistory() {
        return priceHistory;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPriceHistory(int oldPrice) {
        if (priceHistory == null){
            this.priceHistory = String.valueOf(oldPrice);
        }else {
            this.priceHistory += "," + oldPrice;
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "seller='" + seller + '\'' +
                ", article=" + article +
                ", price=" + price +
                ", label='" + label + '\'' +
                ", priceHistory='" + priceHistory + '\'' +
                '}';
    }


    public String print() {
        return String.format("New price: %s, historyPrice: %s, %s, Label: '%s' ", price, priceHistory, calc(), label);
    }

    private String calc(){
        List<String> list = Arrays.asList(priceHistory.split(","));
        int lastPrice = Integer.parseInt(list.getLast());

        if(lastPrice > price){
            return "discounted: " +  (lastPrice - price);
        }else {
            return "increased " + (price - lastPrice);
        }
    }
}
