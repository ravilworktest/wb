package org.example.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
public class Color {
    private String name;
    private Integer id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
