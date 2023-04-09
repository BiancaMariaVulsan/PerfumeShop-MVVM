package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.ICommand;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveSpJsonCommand implements ICommand {
    private final List<ShopProduct> products;
    private final String fileName;

    public SaveSpJsonCommand(List<ShopProduct> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(fileName), products);
            System.out.println("Products saved to JSON file successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while saving products to JSON file: " + e.getMessage());
            return false;
        }
    }
}
