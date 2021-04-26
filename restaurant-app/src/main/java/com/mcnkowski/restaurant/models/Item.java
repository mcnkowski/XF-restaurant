package com.mcnkowski.restaurant.models;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mcnkowski.restaurant.models.Drink;
import com.mcnkowski.restaurant.models.Food;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Food.class, name = "Food"),
        @JsonSubTypes.Type(value = Drink.class, name = "Drink"),
        @JsonSubTypes.Type(value = Drink.DrinkWrapper.class, name = "Drink")
})

public interface Item {
    String getName();
    BigDecimal getPrice(); //using BigDecimal when dealing with money
}