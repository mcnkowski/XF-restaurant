package com.mcnkowski.restaurant.services;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnkowski.restaurant.models.*;

public final class OrderSystem {

    private final ItemMenu menu;
    private final Scanner scanInput = new Scanner(System.in);

    public OrderSystem(ItemMenu menu) {
        this.menu = menu;
    }

    public String run() throws IllegalStateException, JsonProcessingException {

        if (menu == null) { throw new IllegalStateException("Item menu is null."); }
        //program won't run if any menu categories are empty, excluding drink additive menu
        if (menu.isEmpty()) { throw new IllegalStateException("Item menu is missing items in one of the categories."); }

        Order order = new Order();

        addLunch(order);
        addDrink(order);


        ObjectMapper objMapper = new ObjectMapper();
        if (order.isEmpty()) {
            System.out.println("No order has been placed.");
            return objMapper.writeValueAsString(new JsonMessage("empty order"));
        }

        System.out.println("1. Place order\n2. Cancel order");

        while(true) {
            int userInput = intInput();
            switch(userInput) {
                case 1:
                    System.out.println(order);
                    System.out.println("Total: " + order.getTotal());
                    return order.receipt();
                case 2:
                    System.out.println("Order has been cancelled.");
                    return objMapper.writeValueAsString(new JsonMessage("order cancelled"));
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }


    }

    //remember to compensate for zero-indexing
    private int intInput() {
        int input;
        while(true) {
            try{
               input = scanInput.nextInt();
               return input;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please use integers only.");
                scanInput.nextLine(); //consume newline
            }
        }
    }

    private void addLunch(Order order){
        int i = 1;
        int userInput;
        String cuisine;

        //list all cuisines
        System.out.println("Select cuisine: ");
        for(String c : menu.getCuisineNames()) {
            System.out.println(i++ + ". " + c);
        }
        System.out.println("0. None");


        while ((userInput = intInput()) != 0) {
             //input 0 to skip lunch selection

            try {
                cuisine = menu.getCuisineNames()[userInput-1];

                Food.Meal meal = selectMeal(cuisine);
                Food.Dessert dessert = selectDessert();

                order.add(meal);
                order.add(dessert);
                break;

            } catch (NullPointerException npe) {
                System.out.println("Attempted to access a list that doesn't exist." + npe.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid item index.");
            }
        }

    }

    private Food.Meal selectMeal(String cuisine) {
        int i = 1;

        //list meals for selected cuisine
        System.out.println("Select meal: ");
        for(Food.Meal meal : menu.showMeals(cuisine)) {
            System.out.println(i++ + ". " + meal.getName() + ", " + meal.getPrice());
        }
        //select meal
        while(true) {
            try{
                return menu.getMeal(cuisine,intInput()-1);

            } catch (NullPointerException npe) {
                System.out.println("Attempted to access a list that doesn't exist." + npe.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid item index.");
            }
        }
    }

    private Food.Dessert selectDessert() {
        int i = 1;

        //list available desserts
        System.out.println("Select dessert: ");
        for(Food.Dessert dessert : menu.showDesserts()) {
            System.out.println(i++ + ". " + dessert.getName() + ", " + dessert.getPrice());
        }

        //select dessert
        while(true) {
            try {
                return menu.getDessert(intInput()-1);

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid item index.");
            }
        }
    }

    private void addDrink(Order order) {
        int i = 1;
        int userInput;

        for(Drink drink : menu.showDrinks()) {
            System.out.println(i++ + ". " + drink.getName() + ", " + drink.getPrice());
        }
        System.out.println("0. None");


        //select drink
        while((userInput = intInput()) != 0) {
            //input 0 to skip drink selection

            try {
                Drink drink = menu.getDrink(userInput-1);
                drink = addToDrink(drink);

                order.add(drink);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid item index.");
            }
        }
    }

    private Drink addToDrink(Drink drink) {
        //if there are no additives that can be added, return regular drink
        if(menu.showAdditives().isEmpty()) {return drink;}

        int i = 1;
        int userInput;

        for(Drink.Additive addi : menu.showAdditives()) {
            System.out.println(i++ + ". " + addi.getName() + ", " + addi.getPrice());
        }
        System.out.println("0. Add selected to drink.");
        System.out.println("\nChoose additives one by one. Input 0 to add selected additives to drink.");


        HashSet<Drink.Additive> additives = new HashSet<>();

        while((userInput = intInput()) != 0) {
            try {
                Drink.Additive additive = menu.getAdditive(userInput-1);
                additives.add(additive);

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid item index.");
            }

            //print out selected additives
            System.out.print("Selected:");
            for(Drink.Additive a : additives) {
                System.out.print(" " + a.getName());
            }
            System.out.print("\n");
        }


        if (additives.isEmpty()) { return drink; }

        //create new drink with each additive included
        for(Drink.Additive a: additives) {
            drink = drink.with(a);
        }
        return drink;
    }

}