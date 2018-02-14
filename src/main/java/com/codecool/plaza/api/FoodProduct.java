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

    public FoodProduct(long barcode,String manufacturer,String name,int calories,String bestBefore) throws ParseException{
        super(barcode,manufacturer,name);
        this.calories = calories;
        this.bestBefore = dateFormat.parse(bestBefore);
    }

    public boolean isStillConsumeable() {
        return (currentDate.after(bestBefore));
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
