package com.example.perfumeshop.view;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.ViewModel;
import com.example.perfumeshop.view_model.commands.ICommand;
import com.example.perfumeshop.view_model.commands.ProductPresenter;
import com.example.perfumeshop.view_model.commands.FilterProductsCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerView implements Initializable {
    @FXML
    private TableView<Product> productTableView;
    private final ObservableList<Product> productItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product ,String> brandColumn;
    @FXML
    private TableColumn<Product, Boolean> availabilityColumn;
    @FXML
    private TableColumn<Product, Number> priceColumn;

    @FXML
    private TextField nameFilter;
    @FXML
    private TextField brandFilter;
    @FXML
    private CheckBox availabilityFilter;
    @FXML
    private TextField priceFilter;

    @FXML
    private Button filterButton;
    @FXML
    private Button sortNameButton;
    @FXML
    private Button sortPriceButton;

    ProductPresenter productPresenter = new ProductPresenter();
    private final ICommand searchCommand = new FilterProductsCommand();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ViewModel.populateTableProducts(productTableView, productItems, nameColumn, brandColumn, availabilityColumn, priceColumn);

        filterButton.setOnAction(e -> {
            var filteredItems = productPresenter.filterProducts(nameFilter, brandFilter, availabilityFilter, priceFilter);
            ViewModel.populateTableProductsFiltered(productTableView, productItems, nameColumn, brandColumn, availabilityColumn, priceColumn, filteredItems);
        });
        sortNameButton.setOnAction(e -> {
            var sortedItems = productPresenter.sortByName();
            ViewModel.populateTableProductsFiltered(productTableView, productItems, nameColumn, brandColumn, availabilityColumn, priceColumn, sortedItems);
        });
        sortPriceButton.setOnAction(e -> {
            var sortedItems = productPresenter.sortByPrice();
            ViewModel.populateTableProductsFiltered(productTableView, productItems, nameColumn, brandColumn, availabilityColumn, priceColumn, sortedItems);
        });
    }
}
