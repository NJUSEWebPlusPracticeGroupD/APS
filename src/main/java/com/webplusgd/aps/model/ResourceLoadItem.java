package com.webplusgd.aps.model;

import java.util.Date;
import java.util.List;

public class ResourceLoadItem {
    private Date date;

    private String name;

    private List<Double> rates;

    public ResourceLoadItem(Date date, String name, List<Double> rates) {
        this.date = date;
        this.name = name;
        this.rates = rates;
    }

    public ResourceLoadItem() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getRates() {
        return rates;
    }

    public void setRates(List<Double> rates) {
        this.rates = rates;
    }
}
