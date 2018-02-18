package com.codecool.plaza.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodProduct extends Product {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private int calories;
    private Date bestBefore;
    private Date currentDate = new Date();

    public FoodProduct(String manufacturer,String name,int calories,String bestBefore) throws ParseException{
        super(manufacturer,name);
        this.barcode = generateBarcode();
        this.calories = calories;
        this.bestBefore = dateFormat.parse(bestBefore);
    }

    public boolean isStillConsumeable() {
        return (bestBefore.after(currentDate));
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return super.toString() + " Calories: " + calories + " Is it consumable: " + isStillConsumeable();
    }
}
