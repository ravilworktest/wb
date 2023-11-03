package org.example.models.filters;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Filter> filters = new ArrayList<Filter>();
    private Object previews;
    private Integer total;
    public List<Filter> getFilters() {
        return filters;
    }
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    public Object getPreviews() {
        return previews;
    }
    public void setPreviews(Object previews) {
        this.previews = previews;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
}
