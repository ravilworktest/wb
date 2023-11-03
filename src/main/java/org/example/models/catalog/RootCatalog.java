package org.example.models.catalog;

public class RootCatalog {
    private Integer state;
    private Integer version;
    private Params params;
    private Data data;
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public Params getParams() {
        return params;
    }
    public void setParams(Params params) {
        this.params = params;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}
