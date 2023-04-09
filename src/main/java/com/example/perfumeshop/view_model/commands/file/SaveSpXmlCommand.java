package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.view_model.commands.ICommand;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

public class SaveSpXmlCommand implements ICommand {

    private final List<ShopProduct> products;
    private final String fileName;

    public SaveSpXmlCommand(List<ShopProduct> products, String fileName) {
        this.products = products;
        this.fileName = fileName;
    }

    @Override
    public boolean execute() {
        ShopProductList shopProductList = new ShopProductList(products);
        try {
            JAXBContext context = JAXBContext.newInstance(ShopProductList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(fileName);
            marshaller.marshal(shopProductList, file);

            System.out.println("Shop products saved to XML file successfully.");
            return true;
        } catch (JAXBException e) {
            System.out.println("An error occurred while saving shop products to XML file: " + e.getMessage());
            return false;
        }
    }
}

