package com.webplusgd.aps.model;

import java.util.Date;
import java.util.List;

public class ResourceLoadChart {
    private double totalEquipmentLoadRate;
    private double totalPersonnelLoadRate;
    private Date startDate;
    private List<ResourceLoadItem> resourceLoadItems;

    public ResourceLoadChart(double totalEquipmentLoadRate, double totalPersonnelLoadRate, Date startDate, List<ResourceLoadItem> resourceLoadItems) {
        this.totalEquipmentLoadRate = totalEquipmentLoadRate;
        this.totalPersonnelLoadRate = totalPersonnelLoadRate;
        this.startDate = startDate;
        this.resourceLoadItems = resourceLoadItems;
    }

    public ResourceLoadChart(){}

    public List<ResourceLoadItem> getResourceLoadItems() {
        return resourceLoadItems;
    }

    public void setResourceLoadItems(List<ResourceLoadItem> resourceLoadItems) {
        this.resourceLoadItems = resourceLoadItems;
    }


    public double getTotalEquipmentLoadRate() {
        return totalEquipmentLoadRate;
    }

    public void setTotalEquipmentLoadRate(double totalEquipmentLoadRate) {
        this.totalEquipmentLoadRate = totalEquipmentLoadRate;
    }

    public double getTotalPersonnelLoadRate() {
        return totalPersonnelLoadRate;
    }

    public void setTotalPersonnelLoadRate(double totalPersonnelLoadRate) {
        this.totalPersonnelLoadRate = totalPersonnelLoadRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
