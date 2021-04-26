package com.mcnkowski.restaurant.services;

import com.mcnkowski.restaurant.models.ItemMenu;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

import static java.nio.file.Files.newBufferedReader;

public class MenuBuilder {
    public static ItemMenu fromCSV(java.nio.file.Path path, int skip)
            throws IOException, CsvValidationException {

        ItemMenu menu = new ItemMenu();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(newBufferedReader(path))
                .withSkipLines(skip)
                .withCSVParser(parser)
                .build();

        String[] line;
        while ((line = csvReader.readNext()) != null){
            //line[0]: cuisine, line[1]: dish name, line[2]: price
            if (line.length !=3) { throw new CsvValidationException("Unexpected number of columns"); }

            switch (line[0].toLowerCase(Locale.ROOT)) {
                default: //meal
                    menu.addMeal(line[1],new BigDecimal(line[2].trim()),line[0]);
                    break;

                case "dessert":
                    menu.addDessert(line[1],new BigDecimal(line[2].trim()));
                    break;

                case "drink":
                    menu.addDrink(line[1],new BigDecimal(line[2].trim()));
                    break;

                case "additive":
                    menu.addAdditive(line[1],new BigDecimal(line[2].trim()));
                    break;
            }

        }
        csvReader.close();
        return menu;
    }
}
