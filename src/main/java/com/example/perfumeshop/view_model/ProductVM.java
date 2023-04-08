package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.ShopProduct;
import javafx.beans.property.*;

public class ProductVM {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty brand;
    private final DoubleProperty price;
    private final IntegerProperty stock;

    private final ShopProduct shopProduct;

    public ProductVM(Object shopProduct) {
        this.shopProduct = (ShopProduct) shopProduct;
        this.id = new SimpleIntegerProperty(this.shopProduct.getProduct().getId());
        this.name = new SimpleStringProperty(this.shopProduct.getProduct().getName());
        this.brand = new SimpleStringProperty(this.shopProduct.getProduct().getBrand());
        this.price = new SimpleDoubleProperty(this.shopProduct.getProduct().getPrice());
        this.stock = new SimpleIntegerProperty(this.shopProduct.getStock());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getStock() {
        return stock.get();
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }
}
