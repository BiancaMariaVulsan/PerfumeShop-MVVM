package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveSpCSVCommand implements ICommand {
    private final List<ShopProduct> products;
    private final String fileName;

    public SaveSpCSVCommand(List<ShopProduct> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        try {
            FileWriter csvWriter = new FileWriter(fileName);
            csvWriter.append("Name,Price,Brand,Stock\n");
            for (ShopProduct product : products) {
                csvWriter.append(product.getProduct().getName());
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getProduct().getPrice()));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getProduct().getBrand()));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(product.getStock()));
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
