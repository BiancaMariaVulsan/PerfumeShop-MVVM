package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

public class AddProductCommand implements ICommand {
    private final Product product;
    private final ProductPersistence productPersistence = new ProductPersistence();

    public AddProductCommand(Product product) {
        this.product = product;
    }

    @Override
    public boolean execute() {
        try{
            productPersistence.insert(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
