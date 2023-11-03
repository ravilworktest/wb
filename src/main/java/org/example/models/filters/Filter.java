package org.example.models.filters;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private String name;
    private String key;
    private Integer maxselect;
    private List<Item> items = new ArrayList<Item>();
    private Integer minPriceU;
    private Integer maxPriceU;
    private Integer multiselect;
    private Boolean isFull;

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Integer getMaxselect() {
        return maxselect;
    }
    public void setMaxselect(Integer maxselect) {
        this.maxselect = maxselect;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public Integer getMinPriceU() {
        return minPriceU;
    }
    public void setMinPriceU(Integer minPriceU) {
        this.minPriceU = minPriceU;
    }
    public Integer getMaxPriceU() {
        return maxPriceU;
    }
    public void setMaxPriceU(Integer maxPriceU) {
        this.maxPriceU = maxPriceU;
    }
    public Integer getMultiselect() {
        return multiselect;
    }
    public void setMultiselect(Integer multiselect) {
        this.multiselect = multiselect;
    }
}
