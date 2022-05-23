package org.headroyce.lross2024;

import com.google.gson.JsonObject;

/**
 * an item advertised in the shop
 */
public class Item implements Comparable<Item> {
    private String name;  // Cannot be null
    private int stock;    // All values allowed
    private double price; // Cannot be negative

    /**
     * constructs a new item with a name, sets price and stock to 0
     * @param name name of the item
     */
    public Item(String name){
        if(name == null) name = "";

        this.name = name;
        this.stock = 0;
        this.price = 0;
    }

    /**
     * sets name, if param is null set name blank
     * @param name name of the item
     */
    public void setName(String name) {
        if (name == null){
            name = "";
        }
        this.name = name;
    }

    /**
     * sets price, if price is negative set to 0
     * @param price price of the item
     */
    public void setPrice(double price) {
        if (price < 0){
            price = 0;
        }
        this.price = price;
    }

    /**
     * sets stock
     * @param stock stock of the item
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets the price
     * @return price of item
     */
    public double getPrice() {
        return price;
    }

    /**
     * gets the stock
     * @return stock of item
     */
    public int getStock() {
        return stock;
    }

    /**
     * gets the name of the item
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * prints the item's attributes into the terminal
     */
    public void print(){
        System.out.println("name: " + this.getName() + ", stock: " + this.getStock() + ", price: " + this.getPrice());
    }

    /**
     * compares the names of 2 items alphabetically
     * @param other other item to compare
     * @return -1 if this is less than, 0 if they are equal, 1 if this is greater than
     */
    public int compareTo( Item other ) {
        return this.name.compareTo(other.name);
    }

    /**
     * exports this as json object and reformats the price
     * @return json object
     */
    public JsonObject toJson(){
        JsonObject rtn = new JsonObject();
        rtn.addProperty("name", this.name);
        rtn.addProperty("stock", this.stock);
        String parsedPrice = "$" + this.price;
        if (parsedPrice.length() == 5){
            parsedPrice += "0";
        }
        rtn.addProperty("price", parsedPrice);

        return rtn;
    }
}