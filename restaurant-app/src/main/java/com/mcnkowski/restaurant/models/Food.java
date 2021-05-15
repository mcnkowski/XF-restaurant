package com.mcnkowski.restaurant.models;

import java.math.BigDecimal;

public class Food implements Item {

    protected String name;
    protected BigDecimal price;

    public Food(String name, BigDecimal price){
        this.name = name;
        this.price = price.setScale(2);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Meal asMeal() {
        return new Meal(this.getName(),this.getPrice());
    }

    public static Meal asMeal(String name,BigDecimal price) {
        return new Meal(name,price);
    }

    public Dessert asDessert(){
        return new Dessert(this.getName(),this.getPrice());
    }

    public static Dessert asDessert(String name,BigDecimal price) {
        return new Dessert(name,price);
    }

    @Override
    public String toString() {
        return getName() + " " + getPrice().toString();
    }

    public static class Meal extends Food {
        Meal(String name,BigDecimal price) {
            super(name,price);
        }
    }

    public static class Dessert extends Food {
        Dessert(String name,BigDecimal price) {
            super(name,price);
        }
    }
}
