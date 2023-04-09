package com.example.perfumeshop.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "shopProduct")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopProduct {
    @XmlElement(name = "product")
    private Product product;

    @XmlElement(name = "stock")
    private int stock;

    public ShopProduct(Product product, int stock) {
        this.product = product;
        this.stock = stock;
    }
    public Product getProduct() {
        return product;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
