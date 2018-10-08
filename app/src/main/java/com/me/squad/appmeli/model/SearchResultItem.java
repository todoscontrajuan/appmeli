package com.me.squad.appmeli.model;

public class SearchResultItem {
    private String itemTitle;
    private String itemPrice;
    private int imageItemId;
    private boolean freeShipping;

    public SearchResultItem(String itemTitle, String itemPrice, int imageItemId, boolean freeShipping) {
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
        this.imageItemId = imageItemId;
        this.freeShipping = freeShipping;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getImageItemId() {
        return imageItemId;
    }

    public void setImageItemId(int imageItemId) {
        this.imageItemId = imageItemId;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }
}