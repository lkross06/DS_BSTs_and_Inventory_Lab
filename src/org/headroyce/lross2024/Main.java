package org.headroyce.lross2024;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.*;
import java.io.FileNotFoundException;

/**
 * all the file parsing and management with a BST
 */
public class Main {

    /**
     * runs the program
     * @param args processes to run
     */
    public static void main (String[] args) {
        File inventory = new File("res/inventory.txt");
        BST<Item> nametree = scanFileToBST(inventory);
        createFile("res/storeData.txt", nametree);
    }

    /**
     * scans a file and returns a bst with the analyzed JSONs in the file
     * @param file file to analyze
     * @return a bst with serialized JSONs as java objects
     */
    private static BST<Item> scanFileToBST(File file){
        BST<Item> bst = new BST<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String i = sc.nextLine();

                JsonParser parser = new JsonParser();
                JsonObject obj = parser.parse(i).getAsJsonObject();
                String name = obj.get("name").getAsString();
                int stock = obj.get("stock").getAsInt();
                //substring() strips the "$" out of the cost
                double cost = Double.parseDouble(obj.get("cost").getAsString().substring(1));

                Item item = new Item(name);
                item.setPrice(cost);
                item.setStock(stock);

                checkForDupes(item, bst);
            }
            sc.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return bst;
    }

    /**
     * creates a new, empty file and writes the java objects in the bst to the file
     * @param path path of where to create the file
     * @param bst bst with java objects to serialize onto file
     */
    private static void createFile(String path, BST<Item> bst) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                writeToFile(file, bst);
            } else {
                if (file.delete()){
                    File newFile = new File(path);
                    writeToFile(newFile, bst);
                } else {
                    System.out.println("file couldn't be deleted :(");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes java objects to file as JSONs
     * @param file file to write to
     * @param bst bst containing java objects
     */
    private static void writeToFile(File file, BST<Item> bst){
        //TODO: bruh
        //TODO: rewrite price as string
        removeNegStock(bst);
        try {
            PrintWriter pw = new PrintWriter(file);
            List<Item> nametree_inorder = bst.inOrder();
            Gson gson = new Gson();
            pw.println("Number of Unique Items: " + nametree_inorder.size());
            pw.println("");

            //terminal yay
            System.out.println("Number of Unique Items: " + nametree_inorder.size());
            System.out.println("");

            for(int i = 0; i < nametree_inorder.size(); i++){
                JsonObject item = nametree_inorder.get(i).toJson();
                pw.println(item.toString());
                System.out.println(item.toString());

            }
            pw.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    /**
     * checks for duplicates and analyzes data appropriately
     * @param item item to be added to bst
     * @param bst bst to add items to
     */
    private static void checkForDupes(Item item, BST<Item> bst){
        Item found = bst.remove(item);
        //since BST is only sorted on name, even if you put the node back into the tree it wouldn't change the traversal
        if (found != null) {
            //remove, avg price + combine stock, and add back
            item.setStock(item.getStock() + found.getStock());
            //use Math.round(double*100)/100 to round to 2 decimal places
            item.setPrice(Math.round(((item.getPrice() + found.getPrice()) / 2) * 100.0) / 100.0);
        }
        bst.add(item);
    }

    /**
     * removes items with negative stock
     * @param bst bst to remove items from
     */
    private static void removeNegStock(BST<Item> bst){
        List<Item> list = bst.inOrder();
        for(int i = 0; i < list.size(); i++){
            Item item = list.get(i);
            if (item.getStock() <= 0){
                bst.remove(item);
            }
        }
    }
}
