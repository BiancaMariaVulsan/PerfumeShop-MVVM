package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.ManagerVM;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.Comparator;

public class SortProductsCommand implements ICommand {
    boolean sortByName;

    public SortProductsCommand(boolean sortByName) {
        this.sortByName = sortByName;
    }

    @Override
    public boolean execute() {
        ManagerVM managerVM = ManagerVM.getInstance();
        var products = managerVM.getProductItems();
        if(sortByName) {
            managerVM.getProductItems().setAll(products.stream().sorted(Comparator.comparing(Product::getName)).toList());
        } else {
            managerVM.getProductItems().setAll(products.stream().sorted(Comparator.comparing(Product::getPrice)).toList());
        }
        return true;
    }
}
