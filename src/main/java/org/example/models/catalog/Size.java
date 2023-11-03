package org.example.models.catalog;

public class Size {
    private String name;
    private String origName;
    private Integer rank;
    private Integer optionId;
    private Integer returnCost;
    private Integer wh;
    private String sign;
    private String payload;
    private Boolean sale;
    private Integer priceU;
    private Integer salePriceU;
    private Integer logisticsCost;

    public Integer getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(Integer logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public Integer getSalePriceU() {
        return salePriceU;
    }

    public void setSalePriceU(Integer salePriceU) {
        this.salePriceU = salePriceU;
    }

    public Integer getPriceU() {
        return priceU;
    }
    public void setPriceU(Integer priceU) {
        this.priceU = priceU;
    }
    public Boolean getSale() {
        return sale;
    }
    public void setSale(Boolean sale) {
        this.sale = sale;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrigName() {
        return origName;
    }
    public void setOrigName(String origName) {
        this.origName = origName;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Integer getOptionId() {
        return optionId;
    }
    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
    public Integer getReturnCost() {
        return returnCost;
    }
    public void setReturnCost(Integer returnCost) {
        this.returnCost = returnCost;
    }
    public Integer getWh() {
        return wh;
    }
    public void setWh(Integer wh) {
        this.wh = wh;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
