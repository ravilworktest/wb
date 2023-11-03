package org.example.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
public class Params {
    private Integer version;
    private String curr;
    private Integer payloadVersion;
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getCurr() {
        return curr;
    }
    public void setCurr(String curr) {
        this.curr = curr;
    }
    public Integer getPayloadVersion() {
        return payloadVersion;
    }
    public void setPayloadVersion(Integer payloadVersion) {
        this.payloadVersion = payloadVersion;
    }
}
