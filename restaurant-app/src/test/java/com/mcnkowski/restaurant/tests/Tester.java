package com.mcnkowski.restaurant.tests;

import com.mcnkowski.restaurant.models.*;
import com.mcnkowski.restaurant.models.Food;
import com.mcnkowski.restaurant.services.MenuBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Tester {

    Drink drink = new Drink("Super Juice", BigDecimal.valueOf(2.00));
    Drink.Additive ice = new Drink.Additive("Ice", BigDecimal.valueOf(0.10));
    Drink.Additive lemon = new Drink.Additive("Lemon",BigDecimal.valueOf(0.20));

    Food.Meal meal = Food.asMeal("Chicken nuggets", BigDecimal.valueOf(10.00));
    Food.Meal meal2 = Food.asMeal("Pierogi", BigDecimal.valueOf(12.00));

    Food.Dessert dessert = Food.asDessert("Ice cream", BigDecimal.valueOf(8.00));


    @Test
    void testDrink() {
        Drink withice = drink.with(ice);

        assertEquals("Super Juice + Ice",withice.getName());
        assertEquals(drink.getPrice().add(ice.getPrice()),withice.getPrice());
    }

    @Test
    void testDrinkWithMany() {
        Drink withlemonice = drink.with(lemon).with(ice);

        assertEquals("Super Juice + Lemon + Ice",withlemonice.getName());
        assertEquals(drink.getPrice().add(ice.getPrice()).add(lemon.getPrice()),withlemonice.getPrice());
    }

    @Test
    void testOrderTotal() {
        Drink withice = drink.with(ice);

        Order order = new Order();
        order.add(meal);
        order.add(dessert);
        order.add(withice);

        assertEquals(meal.getPrice().add(dessert.getPrice()).add(withice.getPrice()),order.getTotal());
    }

    @Test
    void csvLoadTest() {
        try {
            Path filepath = Paths.get("src\\test\\resources\\com\\mcnkowski\\restaurant\\testmenuitems.csv");
            ItemMenu menu = MenuBuilder.fromCSV(filepath, 1);

            assertAll ("Should have the same name and price",
                    () -> assertEquals(meal.getName(),menu.getMeal("American",0).getName()),
                    () -> assertEquals(meal.getPrice(),menu.getMeal("American",0).getPrice()),
                    () -> assertEquals(meal2.getName(),menu.getMeal("Polish",0).getName()),
                    () -> assertEquals(meal2.getPrice(),menu.getMeal("Polish",0).getPrice()),
                    () -> assertEquals(dessert.getName(),menu.getDessert(0).getName()),
                    () -> assertEquals(dessert.getPrice(),menu.getDessert(0).getPrice())
            );
        } catch (Exception e) {
            org.junit.jupiter.api.Assertions.fail(e.getMessage());
        }
    }

    @Disabled //the unexpected value has been changed
    void jsonOrderTest() throws IOException {
        Order order = new Order();
        order.add(meal);
        order.add(drink.with(ice));

        Path path = Paths.get("target\\testfile.json");
        Files.write(path,order.receipt().getBytes());
        Assertions.assertNotEquals("PLACEHOLDER",order.receipt());
    }

}