package com.example.perfumeshop.view_model.commands.file;

import com.example.perfumeshop.model.ShopProduct;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "shopProducts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopProductList {
    @XmlElement(name = "shopProduct")
    private List<ShopProduct> shopProducts;

    public ShopProductList() {
    }

    public ShopProductList(List<ShopProduct> shopProducts) {
        this.shopProducts = shopProducts;
    }

    @XmlElement(name = "shopProduct")
    public List<ShopProduct> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<ShopProduct> shopProducts) {
        this.shopProducts = shopProducts;
    }
}
