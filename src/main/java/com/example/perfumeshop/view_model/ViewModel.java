package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.ProductPresenter;
import com.example.perfumeshop.view_model.commands.person.GetPersonsCommand;
import com.example.perfumeshop.view_model.commands.product.CheckProductAvailabilityCommand;
import com.example.perfumeshop.view_model.commands.product.GetProductsCommand;
import com.example.perfumeshop.view_model.commands.product.GetProductsFromShopCommand;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class ViewModel {
    static ProductPresenter productPresenter = new ProductPresenter();

    public static void loadFXML(String fxmlFile, Callback<Class<?>, Object> controllerFactory) {
        Stage programStage = new Stage();
        Parent programRoot;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewModel.class.getResource(fxmlFile));
            fxmlLoader.setControllerFactory(controllerFactory);
            var path = ViewModel.class.getResource(fxmlFile);
            fxmlLoader.setLocation(path);
            programRoot = fxmlLoader.load();
            Scene programScene = new Scene(programRoot);
            String css = ViewModel.class.getResource("/com/example/perfumeshop/start.css").toExternalForm();
            programScene.getStylesheets().add(css);
            programStage.setTitle("Running Program");
            programStage.setScene(programScene);
            programStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static Optional<ButtonType> initAlarmBox(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        confirm.setDefaultButton(false);
        confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        return alert.showAndWait();
    }

    public static void populateTableProducts(TableView<Product> productTableView, ObservableList<Product> productItems, TableColumn<Product, String> nameColumn,
                                             TableColumn<Product ,String> brandColumn, TableColumn<Product, Boolean> availabilityColumn, TableColumn<Product, Number> priceColumn){
        GetProductsCommand getProductsCommand = new GetProductsCommand();
        productItems.clear();
        productTableView.getItems().clear();
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        brandColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getBrand()));
        availabilityColumn.setCellValueFactory(cellData -> {
                CheckProductAvailabilityCommand checkProductAvailabilityCommand = new CheckProductAvailabilityCommand(cellData.getValue().getId());
                return new ReadOnlyBooleanWrapper(checkProductAvailabilityCommand.execute());
        });
        priceColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getPrice()));
        if(getProductsCommand.execute()) {
            productItems.addAll(getProductsCommand.getProductList());
        } else {
            ViewModel.initAlarmBox("Error", "Error while popilating the table!", Alert.AlertType.ERROR);
        }
        productTableView.setItems(productItems);
    }

    public static void populateTableProducts(TableView<ShopProduct> productTableView, ObservableList<ShopProduct> productItems, TableColumn<ShopProduct, String> nameColumn,
                                             TableColumn<ShopProduct ,String> brandColumn, TableColumn<ShopProduct, Boolean> availabilityColumn, TableColumn<ShopProduct, Number> priceColumn,
                                             int idShop){
        GetProductsFromShopCommand getProductsFromShopCommand = new GetProductsFromShopCommand(idShop);
        productItems.clear();
        productTableView.getItems().clear();
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduct().getName()));
        brandColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getProduct().getBrand()));
        availabilityColumn.setCellValueFactory(cellData -> new ReadOnlyBooleanWrapper(cellData.getValue().getStock() > 0));
        priceColumn.setCellValueFactory(cellData -> new ReadOnlyDoubleWrapper(cellData.getValue().getProduct().getPrice()));
        if(getProductsFromShopCommand.execute()) {
            productItems.addAll(getProductsFromShopCommand.getProductList());
        } else {
            ViewModel.initAlarmBox("Error", "Error while popilating the table!", Alert.AlertType.ERROR);
        }
        productTableView.setItems(productItems);
    }

    public void populateTablePersons(TableView<Person> personTableView, ObservableList<Person> personItems, TableColumn<Person, String> firstNameColumn,
                                            TableColumn<Person ,String> lastNameColumn, TableColumn<Person, String> roleColumn){
        GetPersonsCommand getPersonsCommand = new GetPersonsCommand();
        personItems.clear();
        personTableView.getItems().clear();
        firstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getRole().toString()));
        if(getPersonsCommand.execute()) {
            personItems.addAll(getPersonsCommand.getPersons());
        } else {
            ViewModel.initAlarmBox("Error", "Error while popilating the table!", Alert.AlertType.ERROR);
        }
        personTableView.setItems(personItems);
    }
}
