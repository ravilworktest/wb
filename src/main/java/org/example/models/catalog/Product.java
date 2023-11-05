package org.example.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("__sort")
    private Integer sort;
    private Integer ksort;
    private Integer time1;
    private Integer time2;
    private Integer dist;
    private Integer id;
    private Integer root;
    private Integer kindId;
    private Integer subjectId;
    private Integer subjectParentId;
    private String name;
    private String brand;
    private Integer brandId;
    private Integer siteBrandId;
    private String supplier;
    private Integer supplierId;
    private Integer sale;
    private Integer priceU;
    private Integer salePriceU;
    private Integer logisticsCost;
    private Integer saleConditions;
    private Integer returnCost;
    private Integer pics;
    private Integer rating;
    private Double reviewRating;
    private Integer feedbacks;
    private Integer panelPromoId;
    private String promoTextCard;
    private String promoTextCat;
    private Integer volume;
    private Integer viewFlags;
    private List<Color> colors = new ArrayList<>();
    private List<Size> sizes = new ArrayList<>();
    private Boolean diffPrice;
    private Object log;
    private Boolean isNew;
    private String priceHistory;
    private Double discount;
    private Double avgDiscount;
    private Long dbRecordId;

    public Product(){}

    public Product(long dbRecordId, int seller, int article, int price, String label, String priceHistory){
        this.dbRecordId = dbRecordId;
        this.supplierId = seller;
        this.id = article;
        this.salePriceU = price;
        this.name = label;
        this.priceHistory = priceHistory;
    }

   /* public Product(int seller, int article, int price, String label, String priceHistory){
        this.supplierId = seller;
        this.id = article;
        this.salePriceU = price;
        this.name = label;
        this.priceHistory = priceHistory;
    }*/

    public String getPriceHistory() {
        return priceHistory;
    }

    public String getPriceHistoryInRub() {
        return Arrays.stream(priceHistory.split(","))
                .map(v -> Integer.parseInt(v) / 100)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Integer> getPriceHistoryAsInt(){
        return Arrays.stream(priceHistory.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId()) && Objects.equals(getSupplierId(), product.getSupplierId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSupplierId());
    }

    public void setPriceHistory(String priceHistory) {
        this.priceHistory = priceHistory;
    }

    public void setPriceHistory(int oldPrice) {
        if (priceHistory == null){
            this.priceHistory = String.valueOf(oldPrice);
        }else {
            this.priceHistory += "," + oldPrice;
        }
    }

    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getKsort() {
        return ksort;
    }
    public void setKsort(Integer ksort) {
        this.ksort = ksort;
    }
    public Integer getTime1() {
        return time1;
    }
    public void setTime1(Integer time1) {
        this.time1 = time1;
    }
    public Integer getTime2() {
        return time2;
    }
    public void setTime2(Integer time2) {
        this.time2 = time2;
    }
    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getRoot() {
        return root;
    }
    public void setRoot(Integer root) {
        this.root = root;
    }
    public Integer getKindId() {
        return kindId;
    }
    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }
    public Integer getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    public Integer getSubjectParentId() {
        return subjectParentId;
    }
    public void setSubjectParentId(Integer subjectParentId) {
        this.subjectParentId = subjectParentId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Integer getBrandId() {
        return brandId;
    }
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
    public Integer getSiteBrandId() {
        return siteBrandId;
    }
    public void setSiteBrandId(Integer siteBrandId) {
        this.siteBrandId = siteBrandId;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public Integer getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
    public Integer getSale() {
        return sale;
    }
    public void setSale(Integer sale) {
        this.sale = sale;
    }
    public Integer getPriceU() {
        return priceU;
    }
    public void setPriceU(Integer priceU) {
        this.priceU = priceU;
    }
    public Integer getSalePriceU() {
        return salePriceU;
    }
    public Integer getSalePriceUInRub() {
        return salePriceU / 100;
    }
    public void setSalePriceU(Integer salePriceU) {
        this.salePriceU = salePriceU;
    }
    public Integer getLogisticsCost() {
        return logisticsCost;
    }
    public void setLogisticsCost(Integer logisticsCost) {
        this.logisticsCost = logisticsCost;
    }
    public Integer getSaleConditions() {
        return saleConditions;
    }
    public void setSaleConditions(Integer saleConditions) {
        this.saleConditions = saleConditions;
    }
    public Integer getReturnCost() {
        return returnCost;
    }
    public void setReturnCost(Integer returnCost) {
        this.returnCost = returnCost;
    }
    public Integer getPics() {
        return pics;
    }
    public void setPics(Integer pics) {
        this.pics = pics;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Double getReviewRating() {
        return reviewRating;
    }
    public void setReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }
    public Integer getFeedbacks() {
        return feedbacks;
    }
    public void setFeedbacks(Integer feedbacks) {
        this.feedbacks = feedbacks;
    }
    public Integer getPanelPromoId() {
        return panelPromoId;
    }
    public void setPanelPromoId(Integer panelPromoId) {
        this.panelPromoId = panelPromoId;
    }
    public String getPromoTextCard() {
        return promoTextCard;
    }
    public void setPromoTextCard(String promoTextCard) {
        this.promoTextCard = promoTextCard;
    }
    public String getPromoTextCat() {
        return promoTextCat;
    }
    public void setPromoTextCat(String promoTextCat) {
        this.promoTextCat = promoTextCat;
    }
    public Integer getVolume() {
        return volume;
    }
    public void setVolume(Integer volume) {
        this.volume = volume;
    }
    public Integer getViewFlags() {
        return viewFlags;
    }
    public void setViewFlags(Integer viewFlags) {
        this.viewFlags = viewFlags;
    }
    public List<Color> getColors() {
        return colors;
    }
    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
    public List<Size> getSizes() {
        return sizes;
    }
    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }
    public Boolean getDiffPrice() {
        return diffPrice;
    }
    public void setDiffPrice(Boolean diffPrice) {
        this.diffPrice = diffPrice;
    }
    public Object getLog() {
        return log;
    }
    public void setLog(Object log) {
        this.log = log;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAvgDiscount() {
        return avgDiscount;
    }

    public void setAvgDiscount(Double avgDiscount) {
        this.avgDiscount = avgDiscount;
    }

    public Long getDbRecordId() {
        return dbRecordId;
    }

    public void setDbRecordId(Long dbRecordId) {
        this.dbRecordId = dbRecordId;
    }

    public String print() {
        return String.format("New price: %s, historyPrice: %s, discount: %s, avg discount: %s, Label: '%s', Id: %s ",
                salePriceU / 100, printHystory(), discount, avgDiscount, name, id);
    }

    private String printHystory(){
         if(priceHistory == null){
             return "[no history]";
         }

        List<Integer> collect = getPriceHistoryAsInt()
                .stream()
                .map(v -> v / 100)
                .toList();

        return collect.toString();
    }

    private String calc(){
        List<String> list = Arrays.asList(priceHistory.split(","));
        int lastPrice = Integer.parseInt(list.getLast()) / 100;
        int currentPrice = salePriceU / 100;

        if(lastPrice > currentPrice){
            return "DISCOUNTED: " +  (lastPrice - currentPrice);
        }else {
            return "increased " + (currentPrice - lastPrice);
        }
    }
}
