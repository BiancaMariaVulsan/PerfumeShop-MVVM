package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.view_model.commands.ICommand;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveXMLCommand implements ICommand {
    private final List<Product> products;
    private final String fileName;

    public SaveXMLCommand(List<Product> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        try {
            JAXBContext context = JAXBContext.newInstance(Product.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            FileWriter writer = new FileWriter(fileName);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
            writer.write("<products>\n");
            for (Product product : products) {
                marshaller.marshal(product, writer);
            }
            writer.write("</products>\n");
            writer.flush();
            writer.close();
            System.out.println("Products saved to XML file successfully.");
            return true;
        } catch (IOException | JAXBException e) {
            System.out.println("An error occurred while saving products to XML file: " + e.getMessage());
            return false;
        }
    }
}
