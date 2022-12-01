package com.gclub.groclub.Databasehelper;

public class cartitem {
    String discount , mrp , price , productKey , product_desc , product_image , product_name ,quantity , save , categories;

    public cartitem(){

    }

    public cartitem(String discount, String mrp, String price, String categories , String productKey, String product_desc, String product_image, String product_name, String quantity, String save) {
        this.discount = discount;
        this.mrp = mrp;
        this.price = price;
        this.productKey = productKey;
        this.product_desc = product_desc;
        this.product_image = product_image;
        this.product_name = product_name;
        this.quantity = quantity;
        this.save = save;
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
