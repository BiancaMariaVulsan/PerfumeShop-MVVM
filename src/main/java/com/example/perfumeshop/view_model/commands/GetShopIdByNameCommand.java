package com.example.perfumeshop.view_model.commands;

public class GetShopIdByNameCommand implements ICommand {
    String shopName;
    int shopId;

    @Override
    public boolean execute() {
        return false;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }
}
