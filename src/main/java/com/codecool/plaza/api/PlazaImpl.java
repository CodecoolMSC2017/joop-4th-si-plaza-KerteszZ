package com.codecool.plaza.api;

import java.util.ArrayList;
import java.util.List;

public class PlazaImpl implements Plaza {
    private List<Shop> shops;
    private boolean isOpen;
    private String name;

    public PlazaImpl(String name) {
        this.name = name;
        shops = new ArrayList<>();
        isOpen = false;
    }

    @Override
    public List<Shop> getShops() throws PlazaIsClosedException {
        if (!isOpen()) {
            throw new PlazaIsClosedException();
        }
        return shops;
    }

    @Override
    public void addShop(Shop shop) throws ShopAlreadyExistsException, PlazaIsClosedException {
        if (!isOpen()) {
            throw new PlazaIsClosedException();
        }
        if (shops.contains(shop)) {
            throw new ShopAlreadyExistsException();
        }
        shops.add(shop);
    }

    @Override
    public void removeShop(Shop shop) throws NoSuchShopException, PlazaIsClosedException {
        if (!isOpen()) {
            throw new PlazaIsClosedException();
        }
        if (!shops.contains(shop)) {
            throw new NoSuchShopException();
        }
        shops.remove(shop);
    }

    @Override
    public Shop findShopByName(String name) throws NoSuchShopException ,PlazaIsClosedException{
        if (!isOpen()) {
            throw new PlazaIsClosedException();
        }
        for (Shop shop : shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        throw new NoSuchShopException();
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

    public String getName() {
        return name;
    }
}
