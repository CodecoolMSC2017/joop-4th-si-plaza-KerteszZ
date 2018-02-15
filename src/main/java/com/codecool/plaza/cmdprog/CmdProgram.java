package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdProgram {
    private List<Product> cart;
    private List<Float> prices;
    private PlazaImpl plaza;
    private Scanner scan = new Scanner(System.in);

    public CmdProgram(){
        cart = new ArrayList<>();
        prices = new ArrayList<>();
    }

    public void run() {
        System.out.println("There's no plaza created yet! Press\n 1) to create a new plaza.\n 2) to exit");

        String choice = scan.nextLine();
        if (choice.equals("1")) {
            System.out.println("What should your plaza be called?");
            String name = scan.nextLine();
            plaza = new PlazaImpl(name);
            mainMenu();

        }
        else {
            System.out.println("See you next time!");
            System.exit(0);
        }
    }

    public void mainMenu() {
        System.out.flush();

        while (true) {
            System.out.println("Welcome to " + plaza.getName() + " plaza.Press:");
            System.out.println(" 1) to list all shops.\n " +
                    "2) to add a new shop.\n " +
                    "3) to remove an existing shop.\n " +
                    "4) enter a shop by name.\n " +
                    "5) to open the plaza.\n " +
                    "6) to close the plaza.\n " +
                    "7) to check if the plaza is open or not");
            String mainMenuChoice = scan.nextLine();
            if (mainMenuChoice.equals("1")) {
                try {
                    for (Shop shop:plaza.getShops()) {
                        System.out.println(shop.toString());
                    }
                }catch (PlazaIsClosedException ex) {
                    System.out.println("Plaza is closed");
                }
            }
            else if (mainMenuChoice.equals("2")) {
                System.out.println("Enter the name of the shop.");
                String name = scan.nextLine();
                System.out.println("Enter name of the owner.");
                String owner = scan.nextLine();
                Shop newShop = new ShopImpl(name,owner);
                try {
                    plaza.addShop(newShop);
                }catch (PlazaIsClosedException ex) {
                    System.out.println("Plaza is closed");
                }catch (ShopAlreadyExistsException ex) {
                    System.out.println("The plaza already has a shop with this name.");
                }
                System.out.println("Shop added.");
            }
            else if (mainMenuChoice.equals("3")) {
                try {
                    System.out.println(plaza.getShops().toString());
                    System.out.println("Enter name of shop to remove");
                    String name = scan.nextLine();
                    try {
                        plaza.removeShop(plaza.findShopByName(name));
                    }catch (NoSuchShopException ex) {
                        System.out.println("Shop not found with this name.");
                    }
                } catch (PlazaIsClosedException ex) {
                    System.out.println("Plaza is closed.");
                }

            }
            else if (mainMenuChoice.equals("4")) {
                System.out.println("Enter name of shop to enter");
                String shopName = scan.nextLine();
                try {
                    Shop shop = plaza.findShopByName(shopName);
                    shopMenu(shop);

                }catch (PlazaIsClosedException ex) {
                    System.out.println("Plaza is closed.");
                }catch (NoSuchShopException ex) {
                    System.out.println("No shop found with this name.");
                }
            }

            else if (mainMenuChoice.equals("5")) {
                plaza.open();
                System.out.println("Plaza is open.");
            }

            else if (mainMenuChoice.equals("6")) {
                plaza.close();
                System.out.println("Plaza is closed.");
            }
        }



    }

    public void shopMenu(Shop shop) {
        System.out.println("Welcome to the " + shop.getName() + " !Press");
        System.out.println(" 1) to list available products." +
                "\n 2) to find products by name." +
                "\n 3) to display the shop's owner." +
                "\n 4) to open the shop." +
                "\n 5) to close the shop." +
                "\n 6) to add new product to the shop." +
                "\n 7) to add existing products to the shop." +
                "\n 8) to buy a product by barcode." +
                "\n 9) to check price by barcode." +
                "\n 10) exit shop.");
    }
}
