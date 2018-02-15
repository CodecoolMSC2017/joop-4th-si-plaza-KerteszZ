package com.codecool.plaza.api;

import java.util.Random;

public abstract class Product {
    protected long barcode;
    protected String manufacturer;
    protected String name;


    protected Product(String manufacturer,String name){

        this.barcode = generateBarcode();
        this.manufacturer = manufacturer;
        this.name = name;

    }

    public long getBarcode() {
        return barcode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getName() {
        return name;
    }

    public long generateBarcode() {
        Random randomLong = new Random();
        long barcode = 900000000 + randomLong.nextInt(100000000);
        return barcode;
    }

    @Override
    public String toString() {
        return name + " Manufacturer: " + manufacturer;
    }
}

