package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.ShopProduct;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void deleteProduct(ProductVM product, int idShop) {

    }

    public void filterProducts(int idShop) {

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
