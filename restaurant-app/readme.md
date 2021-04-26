##Command-line restaurant ordering app.

Made using JDK 8.

Used libraries:
- Jackson https://github.com/FasterXML/jackson
- OpenCSV http://opencsv.sourceforge.net/

Run via maven
```
mvn compile exec:java -Dexec.args="src\main\resources\menu.csv"
```

Alternatively a path to a custom csv file can be supplied.

###OrderSystem
OrderSystem is a class responsible for executing the ordering algorithm. 
The class's constructor accepts an `ItemMenu` object containing available meals and drinks.

```
OrderSystem sys = new OrderSystem(ItemMenu itemmenu);
```

An ItemMenu instance can be acquired from a CSV file by calling static method 
```
MenuBuilder.fromCSV(java.nio.file.Path path, int skip);
```
where `path` is a Path object leading to the CSV file of choice, and `skip` is the number of first rows in the file that should be ignored.

Item menu file should have the following structure:
- Each row corresponds to a single menu entry,
- Each row consists of three values `"cuisine name","item name",price` separated by commas. A different number of cells in a row will result in an exception.
- For desserts, drinks, and drink additives "dessert", "drink", and "additive" should be placed in "cuisine name" column respectively
- Meal cuisine names are case-sensitive and might result in separate cuisines being added to the cuisine list, if the capitalization is inconsistent. "Dessert", "Drink", and "Additive" are not case-sensitive and will end up in their respective categories regardless of capitalization.

An order is placed using OrderSystem's `run()` method. The method goes through steps necessary to make a **single** order, 
and returns a JSON string containing information about the placed order (which could be directed to another program for further use). All the information will also be printed on command line.


The app exclusively accepts integer inputs corresponding to options listed on the command line.  