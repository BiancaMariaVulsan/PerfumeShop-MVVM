package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.EmployeeVM;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.List;
import java.util.stream.Collectors;

public class FilterProductsInShopCommand implements ICommand {
    private String brand;
    private final boolean availability;
    private final double price;
    private final List<ShopProduct> filteredList;

    public FilterProductsInShopCommand(List<ShopProduct> filteredList, String brand, boolean availability, double price) {
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
        EmployeeVM employeeVM = EmployeeVM.getInstance();
        employeeVM.getProductItems().setAll(
                filteredList.stream()
                .filter(it -> brand.equals("") || it.getProduct().getBrand().toLowerCase().contains(brand.toLowerCase()))
                .filter(it -> !availability || (it.getStock() > 0))
                .filter(it -> price == 0.0 || it.getProduct().getPrice() <= price)
                .collect(Collectors.toList()));
        return true;
    }
}
