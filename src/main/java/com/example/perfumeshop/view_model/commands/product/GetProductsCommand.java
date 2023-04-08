package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class GetProductsCommand implements ICommand {
    private List<Product> productList = new ArrayList<>();
    private final ProductPersistence productPersistence = new ProductPersistence();
    @Override
    public boolean execute() {
        productList = productPersistence.findAll();
        return productList.size() > 0;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
