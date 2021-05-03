package com.example.grocery_store;

public class cart_data {

    String name,price,image,amount;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getAmount() {
        return amount;
    }

    public cart_data() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public cart_data(String name, String price, String image, String amount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.amount = amount;
    }

}
