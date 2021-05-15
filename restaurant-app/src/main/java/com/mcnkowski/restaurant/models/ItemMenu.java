package com.mcnkowski.restaurant.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import static java.util.Collections.unmodifiableList;

import com.mcnkowski.restaurant.models.Food.*;
import com.mcnkowski.restaurant.models.Drink.*;



public class ItemMenu {

    private final TreeMap<String,ArrayList<Meal>> mealmenu = new TreeMap<>();
    private final ArrayList<Dessert> dessertmenu = new ArrayList<>();
    private final ArrayList<Drink> drinkmenu = new ArrayList<>();
    private final ArrayList<Additive> additives = new ArrayList<>();

    public void addMeal(String name, BigDecimal price, String cuisine) {
        Meal meal = Food.asMeal(name,price);
        if (mealmenu.containsKey(cuisine)) {
            mealmenu.get(cuisine).add(meal);

        } else {
            //create new cuisine entry if one of such name doesn't exist
            ArrayList <Meal> list = new ArrayList<>();
            list.add(meal);
            mealmenu.put(cuisine,list);

        }

    }

    public String[] getCuisineNames() {
        return mealmenu.keySet().toArray(new String[0]);
    }

    //unmodifiableList protects the menu from being edited from outside
    public List<Meal> showMeals(String cuisine) {
        return unmodifiableList(mealmenu.get(cuisine));
    }

    public Meal getMeal(String cuisine, int id) {
        return mealmenu.get(cuisine).get(id);
    }

    public void addDessert(String name, BigDecimal price) {
        Dessert dessert = Food.asDessert(name,price);
        dessertmenu.add(dessert);
    }

    public List<Dessert> showDesserts() {
        return unmodifiableList(dessertmenu);
    }

    public Dessert getDessert(int id) {
        return dessertmenu.get(id);
    }

    public void addDrink(String name, BigDecimal price) {
        Drink drink = new Drink(name,price);
        drinkmenu.add(drink);
    }

    public List<Drink> showDrinks() {
        return unmodifiableList(drinkmenu);
    }

    public Drink getDrink(int id) {
        return drinkmenu.get(id);
    }

    public void addAdditive(String name, BigDecimal price) {
        Additive additive = new Additive(name,price);
        additives.add(additive);
    }

    public List<Additive> showAdditives() {
        return unmodifiableList(additives);
    }

    public Additive getAdditive(int id) {
        return additives.get(id);
    }


    public boolean isEmpty() {
        return mealmenu.isEmpty() && dessertmenu.isEmpty() && drinkmenu.isEmpty();
    }
}
