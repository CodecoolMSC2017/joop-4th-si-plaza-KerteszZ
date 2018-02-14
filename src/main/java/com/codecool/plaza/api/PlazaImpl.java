package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {
    private List<Shop> shops;
    private boolean isOpen;

    public PlazaImpl() {
        shops = new ArrayList<>();
        isOpen = false;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        return shops;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) {
        return null;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        isOpen = true;
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
