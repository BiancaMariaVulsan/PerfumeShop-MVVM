package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.model.persistence.ShopPersistence;

public class GetShopIdByNameCommand implements ICommand {
    String shopName;
    int shopId = 0;

    private final ShopPersistence shopPersistence = new ShopPersistence();

    @Override
    public boolean execute() {
        try {
            shopId = shopPersistence.findAll().stream().filter(s -> s.getName().equals(shopName)).map(Shop::getId).toList().get(0);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }
}
