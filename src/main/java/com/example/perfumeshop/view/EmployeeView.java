package com.example.perfumeshop.view;

import com.example.perfumeshop.view_model.EmployeeVM;
import com.example.perfumeshop.view_model.ProductVM;
import com.example.perfumeshop.view_model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeView implements Initializable {
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
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button saveCSV;
    @FXML
    private Button saveJSON;
    @FXML
    private Button saveXML;
    @FXML
    private Button saveTXT;

    @FXML
    private TextField brandFilter;
    @FXML
    private CheckBox availabilityFilter;
    @FXML
    private TextField priceFilter;

    private final int idShop;

    private final EmployeeVM employeeVM = EmployeeVM.getInstance();

    public EmployeeView(int idShop) {
        this.idShop = idShop;
    }

    private void bind() {
        brandFilter.textProperty().bindBidirectional(employeeVM.brandFilterProperty());
        availabilityFilter.selectedProperty().bindBidirectional(employeeVM.availabilityFilterProperty());
        StringConverter<Number> converter = new NumberStringConverter();
        priceFilter.textProperty().bindBidirectional(employeeVM.priceFilterProperty(),converter);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bind();
        ViewModel.populateTableProducts(productTableView, employeeVM.getProductItems(), nameColumn, brandColumn, availabilityColumn, priceColumn, idShop);
        addButton.setOnAction(e -> {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == AddProductView.class) {
                    return new AddProductView(idShop);
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        System.err.println("Could not load register controller " + type.getName());
                        throw new RuntimeException(exc);
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/add-product-view.fxml", controllerFactory);
        });
        deleteButton.setOnAction(e -> {
            ProductVM productVM = new ProductVM(productTableView.getSelectionModel().getSelectedItem());
            if(!employeeVM.deleteProduct(productVM, idShop)) {
                ViewModel.initAlarmBox("Error", "Eroor while trying to delete the product!", Alert.AlertType.ERROR);
            }
        });
        filterButton.setOnAction(e -> {
            employeeVM.filterProducts(idShop);
        });
        editButton.setOnAction(e -> {
            ProductVM product = new ProductVM(productTableView.getSelectionModel().getSelectedItem());
            if(productTableView.getSelectionModel().getSelectedItem() == null) {
                ViewModel.initAlarmBox("Warning", "Please select the product to be updated!", Alert.AlertType.WARNING);
                return;
            }
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == AddProductView.class) {
                    return new AddProductView(product, idShop);
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        System.err.println("Could not load register controller " + type.getName());
                        throw new RuntimeException(exc);
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/add-product-view.fxml", controllerFactory);
        });
        saveCSV.setOnAction(e -> {
            employeeVM.saveCSV();
        });
        saveJSON.setOnAction(e -> {
            employeeVM.saveJson();
        });
        saveXML.setOnAction(e -> {
            employeeVM.saveXML();
        });
        saveTXT.setOnAction(e -> {
            employeeVM.saveTXT();
        });
    }
}
