package com.example.perfumeshop.view_model.commands.product;

import com.example.perfumeshop.model.ShopProduct;
import com.example.perfumeshop.model.persistence.ProductPersistence;
import com.example.perfumeshop.view_model.ViewModel;
import com.example.perfumeshop.view_model.commands.ICommand;
import javafx.scene.control.Alert;

public class UpdateProductInShopCommand implements ICommand {
    ShopProduct shopProduct;
    int shopId;
    ProductPersistence productPersistence = new ProductPersistence();

    public UpdateProductInShopCommand(ShopProduct shopProduct, int shopId) {
        this.shopProduct = shopProduct;
        this.shopId = shopId;
    }

    @Override
    public boolean execute() {
        try {
            productPersistence.updateStockOfProduct(shopId, shopProduct.getProduct().getId(), shopProduct.getStock());
            return true;
        } catch (Exception e) {
            ViewModel.initAlarmBox("Error", "Something went wrong when trying to update the stock of the the product. Please make sure you insert valid properties!", Alert.AlertType.ERROR);
            return false;
        }
    }
}
