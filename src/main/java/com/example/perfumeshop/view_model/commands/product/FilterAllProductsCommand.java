package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.ManagerVM;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.List;
import java.util.stream.Collectors;

public class FilterAllProductsCommand implements ICommand {
    private String brand;
    private final boolean availability;
    private final double price;
    private final List<Product> filteredList;

    public FilterAllProductsCommand(List<Product> filteredList, String brand, boolean availability, double price) {
        this.brand = brand;
        this.availability = availability;
        this.price = price;
        this.filteredList = filteredList;
    }

    @Override
    public boolean execute() {
        if(brand == null) {
            brand = "";
        }
        ManagerVM managerVM = ManagerVM.getInstance();
        managerVM.getProductItems().setAll(
                filteredList.stream()
                        .filter(it -> brand.equals("") || it.getBrand().toLowerCase().contains(brand.toLowerCase()))
                        .filter(it -> price == 0.0 || it.getPrice() <= price)
                        .collect(Collectors.toList()));
        return true;
    }
}
