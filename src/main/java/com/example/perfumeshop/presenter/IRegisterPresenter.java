package com.example.perfumeshop.presenter;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;

public interface IRegisterPresenter {
    void setProgressIndicator();
    void register();
    void updatePerson(Person personToUpdate);
    void initShopCheckBox();
    void enableShopChoiceBox(Role role);
}
