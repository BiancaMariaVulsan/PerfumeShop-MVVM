package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveCSVCommand implements ICommand {
    private final List<Product> products;
    private final String fileName;

    public SaveCSVCommand(List<Product> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        try {
            FileWriter csvWriter = new FileWriter(fileName);
            csvWriter.append("Name,Price,Brand\n");
            for (Product product : products) {
                csvWriter.append(product.getName());
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getPrice()));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getBrand()));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Products saved to CSV file successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while saving products to CSV file: " + e.getMessage());
            return false;
        }
    }
}

