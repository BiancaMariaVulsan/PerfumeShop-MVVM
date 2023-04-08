package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.view_model.commands.GetShopIdByNameCommand;
import com.example.perfumeshop.view_model.commands.GetShopsCommand;
import com.example.perfumeshop.view_model.commands.product.FilterAllProductsCommand;
import com.example.perfumeshop.view_model.commands.product.GetProductsCommand;
import com.example.perfumeshop.view_model.commands.product.SortProductsCommand;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class ManagerVM {
    private final StringProperty nameFilter = new SimpleStringProperty();
    private final StringProperty brandFilter = new SimpleStringProperty();
    private final BooleanProperty availabilityFilter = new SimpleBooleanProperty();
    private final DoubleProperty priceFilter = new SimpleDoubleProperty();
    private StringProperty shopNameProperty = new SimpleStringProperty();
    private final ObservableList productItems = FXCollections.observableArrayList();

    // singleton
    private static final ManagerVM managerVM = new ManagerVM();
    private ManagerVM() {}
    public static ManagerVM getInstance() {
        return managerVM;
    }

    public void filter() {
        GetProductsCommand getProductsCommand = new GetProductsCommand();
        getProductsCommand.execute();
        List<Product> productList = getProductsCommand.getProductList();
        GetShopIdByNameCommand getShopIdByNameCommand = new GetShopIdByNameCommand();
        getShopIdByNameCommand.setShopName(getShopNameProperty());
        getShopIdByNameCommand.execute();
        int shopId = getShopIdByNameCommand.getShopId();
        FilterAllProductsCommand filterAllProductsCommand = new FilterAllProductsCommand(productList, shopId, getNameFilter(), getBrandFilter(), isAvailabilityFilter(), getPriceFilter());
        filterAllProductsCommand.execute();
    }

    public void sortByName() {
        SortProductsCommand sortProductsCommand = new SortProductsCommand(true);
        sortProductsCommand.execute();
    }

    public void sortByPrice() {
        SortProductsCommand sortProductsCommand = new SortProductsCommand(false);
        sortProductsCommand.execute();
    }

    public void initShopCheckBox(ChoiceBox<String> shopChoiceBox) {
        List<Shop> shops = GetShopsCommand.getShops();
        for(Shop shop: shops) {
            shopChoiceBox.getItems().add(shop.getName());
        }
        shopNameProperty.set(shops.get(0).getName()); // suppose there is at least one shop
    }

    public String getNameFilter() {
        return nameFilter.get();
    }

    public StringProperty nameFilterProperty() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter.set(nameFilter);
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

    public ObservableList getProductItems() {
        return productItems;
    }

    public String getShopNameProperty() {
        return shopNameProperty.get();
    }

    public StringProperty shopNamePropertyProperty() {
        return shopNameProperty;
    }

    public void setShopNameProperty(String shopNameProperty) {
        this.shopNameProperty.set(shopNameProperty);
    }

}
