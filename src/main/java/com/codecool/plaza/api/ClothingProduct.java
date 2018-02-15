package com.codecool.plaza.api;

public class ClothingProduct extends Product{
    private String material;
    private String type;

    public ClothingProduct(String manufacturer, String name,String material,String type) {
        super(manufacturer, name);
        this.barcode = generateBarcode();
        this.material = material;
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + " Material: " + material + " Type: " + type;
    }
}
