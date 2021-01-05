package com.example.iburgerapp;

public class fOrder {
    private String oName;
    private int price;
    private String userId;


    public fOrder() {

    }

    public fOrder(String oName, int price, String userId ) {
        this.oName = oName;
        this.price = price;
        this.userId = userId;
          }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

