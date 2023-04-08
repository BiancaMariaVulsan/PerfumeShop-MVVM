package com.example.perfumeshop.view;

import com.example.perfumeshop.view_model.ManagerVM;
import com.example.perfumeshop.view_model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerView implements Initializable {
    @FXML
    private TableView productTableView;

    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn brandColumn;
    @FXML
    private TableColumn availabilityColumn;
    @FXML
    private TableColumn priceColumn;

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

    private final ManagerVM managerVM = ManagerVM.getInstance();

    private void bind() {
        nameFilter.textProperty().bindBidirectional(managerVM.nameFilterProperty());
        brandFilter.textProperty().bindBidirectional(managerVM.brandFilterProperty());
        availabilityFilter.selectedProperty().bindBidirectional(managerVM.availabilityFilterProperty());
        StringConverter<Number> converter = new NumberStringConverter();
        priceFilter.textProperty().bindBidirectional(managerVM.priceFilterProperty(),converter);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bind();
        ViewModel.populateTableProducts(productTableView, managerVM.getProductItems(), nameColumn, brandColumn, availabilityColumn, priceColumn);

        filterButton.setOnAction(e -> {
            managerVM.filter();
        });
        sortNameButton.setOnAction(e -> {
            managerVM.sortByName();
        });
        sortPriceButton.setOnAction(e -> {
            managerVM.sortByPrice();
        });
    }
}
