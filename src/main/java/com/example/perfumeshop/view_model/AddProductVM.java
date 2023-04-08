package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.product.AddProductCommand;
import com.example.perfumeshop.view_model.commands.product.AddProductInShopCommand;
import com.example.perfumeshop.view_model.commands.product.GetProductsFromShopCommand;
import com.example.perfumeshop.view_model.commands.product.UpdateProductInShopCommand;
import javafx.beans.property.*;

public class AddProductVM {

    private final StringProperty nameProperty = new SimpleStringProperty();
    private final StringProperty brandProperty = new SimpleStringProperty();
    private final IntegerProperty stockProperty = new SimpleIntegerProperty();
    private final DoubleProperty priceProperty = new SimpleDoubleProperty();
    private int idShop;

    private ShopProduct productToUpdate;
    EmployeeVM employeeVM = EmployeeVM.getInstance();

    public AddProductVM(int idShop, ProductVM productToUpdate) {
        this.productToUpdate = employeeVM.getProductItems().stream().filter(p -> p.getProduct().getId() == (productToUpdate.getId())).toList().get(0);
        this.idShop = idShop;
    }

    public AddProductVM(int idShop) {
        this.idShop = idShop;
    }

    public boolean addProduct() {
        EmployeeVM employeeVM = EmployeeVM.getInstance();
        Product product = new Product(nameProperty.get(), brandProperty.get(), priceProperty.get());
        ShopProduct shopProduct = new ShopProduct(product, getStockProperty());
        AddProductInShopCommand addProductInShopCommand = new AddProductInShopCommand(shopProduct, idShop);
        if(addProductInShopCommand.execute()) {
            employeeVM.getProductItems().add(shopProduct);
            return true;
        }
        return false;
    }

    public boolean updateProduct() {
        int index = employeeVM.getProductItems().indexOf(productToUpdate);
        Product product = new Product(nameProperty.get(), brandProperty.get(), priceProperty.get());
        ShopProduct productUpdated = new ShopProduct(product, getStockProperty());
        UpdateProductInShopCommand updateProductInShopCommand = new UpdateProductInShopCommand(productUpdated, idShop);
        if(updateProductInShopCommand.execute()) {
            employeeVM.getProductItems().set(index, productUpdated);
            return true;
        }
        return false;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    public String getBrandProperty() {
        return brandProperty.get();
    }

    public StringProperty brandProperty() {
        return brandProperty;
    }

    public void setBrandProperty(String brandProperty) {
        this.brandProperty.set(brandProperty);
    }

    public int getStockProperty() {
        return stockProperty.get();
    }

    public IntegerProperty stockProperty() {
        return stockProperty;
    }

    public void setStockProperty(int stockProperty) {
        this.stockProperty.set(stockProperty);
    }

    public double getPriceProperty() {
        return priceProperty.get();
    }

    public DoubleProperty priceProperty() {
        return priceProperty;
    }

    public void setPriceProperty(double priceProperty) {
        this.priceProperty.set(priceProperty);
    }

    public void setProductToUpdate(ProductVM productToUpdate) {
//        this.productToUpdate = productToUpdate;
    }
}
