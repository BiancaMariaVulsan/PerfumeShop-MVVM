package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.product.DeleteProductCommand;
import com.example.perfumeshop.view_model.commands.product.FilterProductsInShopCommand;
import com.example.perfumeshop.view_model.commands.product.GetProductsFromShopCommand;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EmployeeVM {
    private final StringProperty brandFilter = new SimpleStringProperty();
    private final BooleanProperty availabilityFilter = new SimpleBooleanProperty();
    private final DoubleProperty priceFilter = new SimpleDoubleProperty();
    private final ObservableList<ShopProduct> productItems = FXCollections.observableArrayList();

    // singleton
    private static EmployeeVM employeeVM = new EmployeeVM();
    private EmployeeVM() {}
    public static EmployeeVM getInstance() {
       return employeeVM;
    }

    public boolean deleteProduct(ProductVM product, int idShop) {
        ShopProduct productToDel = productItems.stream().filter(p -> p.getProduct().getId() == (product.getId())).toList().get(0);
        DeleteProductCommand deleteProductCommand = new DeleteProductCommand(idShop, productToDel.getProduct());
        productItems.remove(productToDel);
        return deleteProductCommand.execute();
    }

    public void filterProducts(int idShop) {
        GetProductsFromShopCommand getProductsFromShopCommand = new GetProductsFromShopCommand(idShop);
        getProductsFromShopCommand.execute();
        List<ShopProduct> shopProductList = getProductsFromShopCommand.getProductList();
        FilterProductsInShopCommand filterProductsCommand = new FilterProductsInShopCommand(shopProductList, getBrandFilter(), isAvailabilityFilter(), getPriceFilter());
        filterProductsCommand.execute();
    }

    public ObservableList<ShopProduct> getProductItems() {
        return productItems;
    }

    public String getBrandFilter() {
        return brandFilter.get();
    }

    public StringProperty brandFilterProperty() {
        return brandFilter;
    }

    public void setBrandFilter(String brandFilter) {
        this.brandFilter.set(brandFilter);
    }

    public boolean isAvailabilityFilter() {
        return availabilityFilter.get();
    }

    public BooleanProperty availabilityFilterProperty() {
        return availabilityFilter;
    }

    public void setAvailabilityFilter(boolean availabilityFilter) {
        this.availabilityFilter.set(availabilityFilter);
    }

    public double getPriceFilter() {
        return priceFilter.get();
    }

    public DoubleProperty priceFilterProperty() {
        return priceFilter;
    }

    public void setPriceFilter(double priceFilter) {
        this.priceFilter.set(priceFilter);
    }
}
