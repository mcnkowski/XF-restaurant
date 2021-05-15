package com.mcnkowski.restaurant.models;

import java.math.BigDecimal;

public class Drink implements Item {

    protected String name;
    protected BigDecimal price;


    public Drink(String name, BigDecimal price){
        this.name = name;
        this.price = price.setScale(2);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Drink with(Additive item) {
        return new DrinkWrapper(this,item);
    }

    @Override
    public String toString() {
        return getName() + " " + getPrice().toString();
    }

    static class DrinkWrapper extends Drink{

        private final Additive addition;

        DrinkWrapper(Drink drink, Additive with){
            super(drink.getName(),drink.getPrice());
            addition = with;
        }

        public String getName() {
            return super.getName() + " + " + addition.getName();
        }

        public BigDecimal getPrice() {
            return super.getPrice().add(addition.getPrice());
        }

    }

    public static class Additive implements Item{

        private final String name;
        private final BigDecimal price;

        public Additive(String name, BigDecimal price){
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return getName() + " " + getPrice().toString();
        }

    }
}


