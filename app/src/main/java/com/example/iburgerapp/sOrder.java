package com.example.iburgerapp;

public class sOrder {
    private String oName;
    private int price;
    private int num;
    private String userId;

    public sOrder() {

    }


    public sOrder(String oName, int price, int num , String userId) {
        this.oName = oName;
        this.price = price;
        this.num = num;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}