package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.model.persistence.ShopPersistence;

import java.util.List;

public class ShopPresenter {
    static ShopPersistence shopPersistence = new ShopPersistence();

    public static List<Shop> getShops() {
        return shopPersistence.findAll();
    }

    public static String getShopNameById(int id) {
        return shopPersistence.findById(id).getName();
    }
}
