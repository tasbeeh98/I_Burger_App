package com.example.iburgerapp;

public class sOrder {
    private String oName;
    private int price;
    private int num;
    private String userId;
    private int rPrice;

    public sOrder() {

    }

    public sOrder(int rprice , int num) {
        this.rPrice = rprice ;
        this.num = num ;

    }


    public sOrder(String oName, int price, int num , String userId , int rPrice) {
        this.oName = oName;
        this.price = price;
        this.num = num;
        this.userId = userId;
        this.rPrice = rPrice ;
    }

    public int getrPrice() {
        return rPrice;
    }

    public void setrPrice(int rPrice) {
        this.rPrice = rPrice;
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