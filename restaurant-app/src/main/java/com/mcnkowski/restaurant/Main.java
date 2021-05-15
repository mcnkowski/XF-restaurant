package com.mcnkowski.restaurant;

import com.mcnkowski.restaurant.models.ItemMenu;
import com.mcnkowski.restaurant.services.MenuBuilder;
import com.mcnkowski.restaurant.services.OrderSystem;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        //pass path to the csv file containing menu via args

        try {
            ItemMenu menu = MenuBuilder.fromCSV(Paths.get(args[0]),1);
            OrderSystem sys = new OrderSystem(menu);

            while(true) {
                sys.run();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No menu path has been supplied.");
        } catch (CsvValidationException e) {
            System.out.println("There was a problem processing the CSV file. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("There was a problem reading the file. " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Ordering system can't run in current state. " + e.getMessage());
        }
    }
}
