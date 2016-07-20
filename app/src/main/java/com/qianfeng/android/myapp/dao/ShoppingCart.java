package com.qianfeng.android.myapp.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "SHOPPING_CART".
 */
public class ShoppingCart {

    private Long id;
    /** Not-null value. */
    private String serviceTitle;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String originalPrice;
    /** Not-null value. */
    private String imageUrl;
    private int buyNum;
    private int minBuyNum;

    public ShoppingCart() {
    }

    public ShoppingCart(Long id) {
        this.id = id;
    }

    public ShoppingCart(Long id, String serviceTitle, String name, String originalPrice, String imageUrl, int buyNum, int minBuyNum) {
        this.id = id;
        this.serviceTitle = serviceTitle;
        this.name = name;
        this.originalPrice = originalPrice;
        this.imageUrl = imageUrl;
        this.buyNum = buyNum;
        this.minBuyNum = minBuyNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getServiceTitle() {
        return serviceTitle;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getOriginalPrice() {
        return originalPrice;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    /** Not-null value. */
    public String getImageUrl() {
        return imageUrl;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getMinBuyNum() {
        return minBuyNum;
    }

    public void setMinBuyNum(int minBuyNum) {
        this.minBuyNum = minBuyNum;
    }

}