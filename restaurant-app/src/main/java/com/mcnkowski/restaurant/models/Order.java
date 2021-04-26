package com.mcnkowski.restaurant.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.mcnkowski.restaurant.models.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Order {

    ArrayList<Item> items = new ArrayList<>(2);

    public void add(Item item){
        if (Objects.nonNull(item)) {
            items.add(item);
        }
    }

    public void addAll(Collection<Item> items){
        if (items.stream().allMatch(Objects::nonNull)) {
            this.items.addAll(items);
        }
    }

    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.valueOf(0.00);

        for (Item item : items) {
            total = total.add(item.getPrice());
        }

        return total;
    }

    public String receipt() {
        ObjectMapper objMapper = new ObjectMapper();
        OrderBean ob = new OrderBean(this.items,this.getTotal());

        try {
            return objMapper.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            return "{\"error\":\""+e.getMessage()+"\"}";
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        return items.toString();
    }

   @JsonRootName("order")
    private class OrderBean {
        @JsonProperty("items")
        Item[] items;

        @JsonProperty("total")
        BigDecimal total;

        OrderBean(ArrayList<Item> items, BigDecimal total) {
            this.items = items.toArray(new Item[0]);
            this.total = total;
        }
    }
}