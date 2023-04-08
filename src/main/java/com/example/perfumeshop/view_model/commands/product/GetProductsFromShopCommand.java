package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class GetProductsFromShopCommand implements ICommand {
    private final int shopId;
    private List<ShopProduct> productList = new ArrayList<>();
    private final ProductPersistence productPersistence = new ProductPersistence();
    public GetProductsFromShopCommand(int shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean execute() {
        productList = productPersistence.getShopProducts().get(shopId);
        return productList.size() > 0;
    }

    public List<ShopProduct> getProductList() {
        return productList;
    }
}
