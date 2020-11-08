package com.webplusgd.aps.model;

public class OrderGanttItem {
    private String orderId;
    private int progress;
    private int progressDelay;

    public OrderGanttItem(){}

    public OrderGanttItem(String orderId, int progress, int progressDelay) {
        this.orderId = orderId;
        this.progress = progress;
        this.progressDelay = progressDelay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgressDelay() {
        return progressDelay;
    }

    public void setProgressDelay(int progressDelay) {
        this.progressDelay = progressDelay;
    }
}
