package com.codecool.plaza.cmdprog;

import com.codecool.plaza.api.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
                    "7) to check if the plaza is open or not.\n " +
                    "8) to check the content of your cart.\n " +
                    "9) to check how much money you spent so far.\n " +
                    "10) to exit plaza.");
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

            else if (mainMenuChoice.equals("7")) {
                if (plaza.isOpen()) {
                    System.out.println("Plaza is open!");
                }
                else {
                    System.out.println("Plaza is closed.Come back later!");
                }
            }
            else if (mainMenuChoice.equals("8")) {
                if (cart.size() == 0) {
                    System.out.println("Your cart is empty.");
                }
                else {
                    showCartContent();
                }
            }
            else if (mainMenuChoice.equals("9")) {
                if (prices.size() == 0) {
                    System.out.println("You haven't spent money yet.");
                }
                else {
                    System.out.println(showSpendings());
                }
            }
            else if(mainMenuChoice.equals("10")) {
                System.out.println("Plaza session ending.\n" +
                        "You bought: ");
                showCartContent();
                System.out.println("You spent: " + showSpendings() + "Ft");
                System.out.println("Press enter to confirm exit.");
                scan.nextLine();
                System.exit(0);
            }
        }



    }

    public void shopMenu(Shop shop) {
        while (true) {
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

            String shopChoice = scan.nextLine();
            if (shopChoice.equals("1")) {
                try {
                    for (Product product:shop.getProducts()) {
                        System.out.println(product.toString());
                    }

                }catch (ShopIsClosedException ex) {
                    System.out.println("Shop is closed");
                }
            }
            else if (shopChoice.equals("2")) {
                try {
                    String nameOfProduct = scan.nextLine();
                    System.out.println(shop.findByName(nameOfProduct).toString());
                }catch (ShopIsClosedException ex) {
                    System.out.println("Shop is closed.");
                }catch (NoSuchProductException ex) {
                    System.out.println("No such product with this name.");
                }
            }
            else if (shopChoice.equals("3")) {
                System.out.println(shop.getOwner());
            }
            else if (shopChoice.equals("4")) {
                shop.open();
                System.out.println("Shop is open.");
            }
            else if (shopChoice.equals("5")) {
                shop.close();
                System.out.println("Shop is closed.");
            }
            else if (shopChoice.equals("6")) {
                while (true) {
                    System.out.println("Choose the type of product to add: 1) Food 2) Clothes");
                    String productChoice = scan.nextLine();
                    if (productChoice.equals("1")) {
                        System.out.println("Enter name of product.");
                        String name = scan.nextLine();
                        System.out.println("Enter manufacturer.");
                        String manufacturer = scan.nextLine();

                        System.out.println("Enter best before date yyyy/mm/dd format please.");
                        String bestBefore = scan.nextLine();
                        System.out.println("Enter quantity.");
                        int quantity = scan.nextInt();
                        System.out.println("Enter calories.");
                        int calories = scan.nextInt();
                        System.out.println("Enter price");
                        float price = scan.nextInt();

                        try {
                            Product newProduct = new FoodProduct(manufacturer,name,calories,bestBefore);
                            shop.addNewProduct(newProduct,quantity,price);
                            break;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }catch (ProductAlreadyExistsException ex){
                            System.out.println("Product already in store.");
                        }catch (ShopIsClosedException ex) {
                            System.out.println("Shop is closed");
                            break;
                        }
                        break;

                    }
                    else if (productChoice.equals("2")) {
                        System.out.println("Enter name of product.");
                        String name = scan.nextLine();
                        System.out.println("Enter manufacturer.");
                        String manufacturer = scan.nextLine();
                        System.out.println("Enter material.");
                        String material = scan.nextLine();
                        System.out.println("Enter type.");
                        String type = scan.nextLine();
                        System.out.println("Enter quantity.");
                        int quantity = scan.nextInt();
                        System.out.println("Enter price");
                        float price = scan.nextInt();

                        try {
                            Product newProduct = new ClothingProduct(manufacturer,name,material,type);
                            shop.addNewProduct(newProduct, quantity, price);
                            break;
                        }catch (ProductAlreadyExistsException ex){
                            System.out.println("Product already in store.");
                        }catch (ShopIsClosedException ex) {
                            System.out.println("Shop is closed");
                            break;
                        }
                    }
                    else {
                        System.out.println("You entered an invalid input,please try again.");
                    }
                }
            }
            else if (shopChoice.equals("7")) {
                System.out.println("Enter barcode of product.");
                long barcode = scan.nextInt();
                System.out.println("Enter amount to add.");
                int quantity = scan.nextInt();
                try {
                    shop.addProduct(barcode, quantity);
                }catch (NoSuchProductException ex) {
                    System.out.println("No product found with this barcode.");
                }catch (ShopIsClosedException ex) {
                    System.out.println("Shop is closed.");
                }
            }
            else if (shopChoice.equals("8")) {
                System.out.println("Enter barcode of product to buy.");
                long barcode = scan.nextInt();
                try {
                    cart.add(shop.buyProduct(barcode));
                    prices.add(shop.getPrice(barcode));

                }catch (NoSuchProductException ex) {
                    System.out.println("No product found with that barcode.");
                }catch (OutOfStockException ex) {
                    System.out.println("No more items of the given product.");
                }catch (ShopIsClosedException ex) {
                    System.out.println("Shop is closed.");
                }
            }
            else if(shopChoice.equals("9")) {
                System.out.println("Enter barcode of the item you want the price of.");
                long barcode = scan.nextInt();

                try {
                    System.out.println("Price of the product: " + shop.getPrice(barcode));
                }catch (NoSuchProductException ex) {
                    System.out.println("No product with that barcode.");
                }catch (ShopIsClosedException ex) {
                    System.out.println("Shop is closed.");
                }
            }
            else if (shopChoice.equals("10")) {
                break;
            }
            else {
                System.out.println("Invalid input,try again please.");
            }

        }
    }

    public void showCartContent() {
        for (Product product:cart) {
            System.out.println(product.toString());

        }
    }

    public float showSpendings() {
        float spendings = 0;
        for (Float spending:prices) {
            spendings += spending;
        }
        return spendings;
    }


}
