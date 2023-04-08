package com.example.perfumeshop.view;

import com.example.perfumeshop.view_model.AdminVM;
import com.example.perfumeshop.view_model.PersonVM;
import com.example.perfumeshop.view_model.ViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminView implements Initializable {
    @FXML
    private TableView personTableView;
    @FXML
    private TableColumn firstNameColumn;
    @FXML
    private TableColumn lastNameColumn;
    @FXML
    private TableColumn roleColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;

    private final ViewModel viewModel = new ViewModel();
    private final AdminVM adminVM = AdminVM.getInstance();

    public void bind() {
        firstNameColumn.textProperty().bindBidirectional(adminVM.firstNameColumnProperty());
        lastNameColumn.textProperty().bindBidirectional(adminVM.lastNameColumnProperty());
        roleColumn.textProperty().bindBidirectional(adminVM.roleColumnProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList items = adminVM.getPersonItems();
        viewModel.populateTablePersons(personTableView, items, firstNameColumn, lastNameColumn, roleColumn);
        adminVM.setPersonItems(items);
        bind();

        addButton.setOnAction(e -> {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == RegisterView.class) {
                    return new RegisterView();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        System.err.println("Could not load register controller " + type.getName());
                        throw new RuntimeException(exc);
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/register-view.fxml", controllerFactory);
        });
        deleteButton.setOnAction(e -> {
            PersonVM personVM = new PersonVM(personTableView.getSelectionModel().getSelectedItem());
            adminVM.deletePerson(personVM);
        });
        editButton.setOnAction(e -> {
            PersonVM personVM = new PersonVM(personTableView.getSelectionModel().getSelectedItem());
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == RegisterView.class) {
                    return new RegisterView(personVM);
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        System.err.println("Could not load register controller " + type.getName());
                        throw new RuntimeException(exc);
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/register-view.fxml", controllerFactory);
        });
    }
}
