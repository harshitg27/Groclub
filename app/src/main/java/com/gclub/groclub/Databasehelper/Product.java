package com.gclub.groclub.Databasehelper;

public class Product {
     public String key , quantity ,discount , description , image, category , mrp , product_name , sellPrice , date , time , saving ;

    public Product() {
    }


    public Product(String key, String quantity, String discount, String description, String image, String category, String mrp, String product_name, String sellPrice, String date, String time, String saving) {
        this.key = key;
        this.quantity = quantity;
        this.discount = discount;
        this.description = description;
        this.image = image;
        this.category = category;
        this.mrp = mrp;
        this.product_name = product_name;
        this.sellPrice = sellPrice;
        this.date = date;
        this.time = time;
        this.saving = saving;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSaving() {
        return saving;
    }

    public void setSaving(String saving) {
        this.saving = saving;
    }
}
