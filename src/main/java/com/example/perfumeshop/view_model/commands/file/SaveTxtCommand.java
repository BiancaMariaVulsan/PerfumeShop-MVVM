package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveTxtCommand implements ICommand {
    private final List<Product> products;
    private final String fileName;

    public SaveTxtCommand(List<Product> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Product product : products) {
                writer.write("Name: " + product.getName() + "\n");
                writer.write("Price: " + product.getPrice() + "\n");
                writer.write("Brand: " + product.getBrand() + "\n\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Products saved to TXT file successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while saving products to TXT file: " + e.getMessage());
            return false;
        }
    }
}
