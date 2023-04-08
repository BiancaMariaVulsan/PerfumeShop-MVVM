package com.example.perfumeshop.view;

import com.example.perfumeshop.view_model.AddProductVM;
import com.example.perfumeshop.view_model.ProductVM;
import com.example.perfumeshop.view_model.ViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductView implements Initializable {
    @FXML
    private TextField nameText;
    @FXML
    private TextField brandText;
    @FXML
    private TextField stockText;
    @FXML
    private TextField priceText;
    @FXML
    private Button saveButton;
    private final int idShop;
    private ProductVM productVM;
    private final boolean isEditing;

    private AddProductVM addProductVM;


    public
    AddProductView(int idShop) {
        isEditing = false;
        this.idShop = idShop;
        this.addProductVM = new AddProductVM(idShop);
    }

    public AddProductView(ProductVM productVM, int idShop) {
        isEditing = true;
        this.idShop = idShop;
        this.productVM = productVM;
        this.addProductVM = new AddProductVM(idShop, productVM);
    }

    void bind() {
        nameText.textProperty().bindBidirectional(addProductVM.nameProperty());
        brandText.textProperty().bindBidirectional(addProductVM.brandProperty());
        StringConverter<Number> converter = new NumberStringConverter();
        stockText.textProperty().bindBidirectional(addProductVM.stockProperty(), converter);
        priceText.textProperty().bindBidirectional(addProductVM.priceProperty(), converter);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bind();
        if(isEditing) {
            addProductVM.setNameProperty(productVM.getName());
            addProductVM.setBrandProperty(productVM.getBrand());
            addProductVM.setPriceProperty(productVM.getPrice());
            addProductVM.setStockProperty(productVM.getStock());
            nameText.setDisable(true);
            brandText.setDisable(true);
            priceText.setDisable(true);
        }

        saveButton.setOnAction(e -> {
            if(!isEditing) {
                if(addProductVM.addProduct()) {
                    ViewModel.initAlarmBox("Successful insertion", "Product successfully registered!", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                }
            } else {
                addProductVM.setProductToUpdate(productVM);
                if(addProductVM.updateProduct()) {
                    ViewModel.initAlarmBox("Successful update", "Product successfully updated!", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }
}
