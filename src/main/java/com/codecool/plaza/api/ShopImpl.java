package com.codecool.plaza.api;

import java.util.*;

public class ShopImpl implements Shop {
    private String name;
    private String owner;
    private Map<Long, ShopEntryImpl> products;
    private boolean isOpen;


    public ShopImpl(String name,String owner){
        this.name = name;
        this.owner = owner;
        products = new HashMap<>();
        isOpen = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void open() {
        isOpen = true;
    }

    @Override
    public void close() {
        isOpen = false;
    }

    @Override
    public Product findByName(String name) throws NoSuchProductException, ShopIsClosedException {
        if (!isOpen()) throw new ShopIsClosedException();
        for (ShopEntryImpl product:products.values()) {
            if(product.getProduct().getName().equals(name)){
                return product.getProduct();
            }
        }
        throw new NoSuchProductException();

    }

    @Override
    public boolean hasProduct(long barcode) throws ShopIsClosedException {
        if(!isOpen()) throw new ShopIsClosedException();
        for(ShopEntryImpl product:products.values()){
            if(product.getProduct().getBarcode() == barcode){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewProduct(Product product, int quantity, float price) throws ProductAlreadyExistsException, ShopIsClosedException {
        if (!isOpen()) throw new ShopIsClosedException();

        ShopEntryImpl newProduct = new ShopEntryImpl(product,quantity,price);
        if(products.values().contains(newProduct)) throw new ProductAlreadyExistsException();

        Random randomLong = new Random();
        long barcode = 900000000 + randomLong.nextInt(100000000);
        products.put(barcode,newProduct);
    }

    @Override
    public void addProduct(long barcode, int quantity) throws NoSuchProductException, ShopIsClosedException {
        if (!isOpen()) throw new ShopIsClosedException();
        boolean productFound = false;
        for(Map.Entry<Long, ShopEntryImpl> entry:products.entrySet()) {
            long productBarcode = entry.getKey();
            ShopEntryImpl product = entry.getValue();
            if(productBarcode == barcode){
                product.increaseQuantity(quantity);
                productFound = true;
                break;
            }
        }
        if(!productFound) throw new NoSuchProductException();

    }

    @Override
    public Product buyProduct(long barcode) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if(!isOpen) throw new ShopIsClosedException();
        Product pr = null;

        for(Map.Entry<Long, ShopEntryImpl> entry:products.entrySet()) {
            long productBarcode = entry.getKey();
            ShopEntryImpl product = entry.getValue();
            if(productBarcode == barcode){
                if (product.getQuantity() < 1) throw new OutOfStockException();
                product.decreaseQuantity(1);
                pr = product.getProduct();
            }
        }
        if(pr == null) throw new NoSuchProductException();
        return pr;
    }

    @Override
    public List<Product> buyProducts(long barcode, int quantity) throws NoSuchProductException, OutOfStockException, ShopIsClosedException {
        if(!isOpen) throw new ShopIsClosedException();
        boolean productFound = false;
        List<Product> productsList = new ArrayList<>();
        for(Map.Entry<Long, ShopEntryImpl> entry:products.entrySet()) {
            long productBarcode = entry.getKey();
            ShopEntryImpl product = entry.getValue();
            if(productBarcode == barcode){
                if (product.getQuantity() < quantity){
                    throw new OutOfStockException();
                }
                product.decreaseQuantity(quantity);
                Product pr = product.getProduct();
                productsList = Collections.nCopies(5, pr);
                productFound = true;
            }
        }
        if(productFound) return productsList;
        throw new NoSuchProductException();
    }

    class ShopEntryImpl {
        private Product product;
        private int quantity;
        private float price;

        ShopEntryImpl(Product product,int quantity,float price){
            this.product = product;
            this.quantity = quantity;
            this.price = price;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void increaseQuantity(int amount) {
            quantity += amount;
        }

        public void decreaseQuantity(int amount) {
            quantity -= amount;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return null;
        }
    }
}
