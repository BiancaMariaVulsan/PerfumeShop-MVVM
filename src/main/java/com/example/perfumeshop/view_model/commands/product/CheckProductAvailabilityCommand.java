package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.List;
import java.util.Map;

public class CheckProductAvailabilityCommand implements ICommand {
    private final ProductPersistence productPersistence = new ProductPersistence();
    private int productId;

    public CheckProductAvailabilityCommand(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean execute() {
        var productsMap = productPersistence.getShopProducts();
        for(Map.Entry<Integer, List<ShopProduct>> entry : productsMap.entrySet()) {
            for(ShopProduct shopProduct : entry.getValue()) {
                if(shopProduct.getProduct().getId() == productId && shopProduct.getStock() > 0)
                    return true;
            }
        }
        return false;
    }
}
