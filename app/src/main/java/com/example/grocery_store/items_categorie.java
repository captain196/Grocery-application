package com.example.grocery_store;

import android.widget.ImageView;
import android.widget.TextView;

public class items_categorie  {

    String item_image;
    String item_name;
    String item_price;

    public items_categorie() {
    }

    public items_categorie(String item_image, String item_name, String item_price) {
        this.item_image = item_image;
        this.item_name = item_name;
        this.item_price = item_price;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }
}
