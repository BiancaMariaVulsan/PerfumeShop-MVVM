package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.Product;
import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

public class AddProductInShopCommand implements ICommand {
    private ShopProduct shopProduct;
    int shopId;
    private static final ProductPersistence productPersistence = new ProductPersistence();

    public AddProductInShopCommand(ShopProduct shopProduct, int shopId) {
        this.shopProduct = shopProduct;
        this.shopId = shopId;
    }

    @Override
    public boolean execute() {
        try {
            productPersistence.insert(shopProduct.getProduct());
            Product insertedProduct = productPersistence.findAll()
                    .stream().filter(p -> p.getName().equals(shopProduct.getProduct().getName()) && p.getBrand().equals(shopProduct.getProduct().getBrand())
                            && p.getPrice() == shopProduct.getProduct().getPrice())
                    .findFirst()
                    .orElse(null);
            productPersistence.insertProductInShop(shopId, insertedProduct.getId(), shopProduct.getStock());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
