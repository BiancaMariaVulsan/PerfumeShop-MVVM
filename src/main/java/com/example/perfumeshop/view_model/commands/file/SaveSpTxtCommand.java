package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveSpTxtCommand implements ICommand {
    private final List<ShopProduct> products;
    private final String fileName;

    public SaveSpTxtCommand(List<ShopProduct> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (ShopProduct product : products) {
                writer.write("Name: " + product.getProduct().getName() + "\n");
                writer.write("Price: " + product.getProduct().getPrice() + "\n");
                writer.write("Brand: " + product.getProduct().getBrand() + "\n");
                writer.write("Stock: " + product.getStock()+ "\n\n");
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
