package com.webplusgd.aps.model;

import java.util.Date;

public class ResourceGanttItem {
    private String name;

    private Date fromDate;
    private Date toDate;
    private String task;
    private boolean delay;

    public ResourceGanttItem(){}

    public ResourceGanttItem(String name, Date fromDate, Date toDate, String task, boolean delay) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.task = task;
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

}
