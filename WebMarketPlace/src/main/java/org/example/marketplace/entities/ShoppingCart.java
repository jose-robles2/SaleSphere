package org.example.marketplace.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private List<Item> cart;

    private double total;

    private final Map<String, Integer> itemTypeQuantity;

    private final Map<String, Integer> itemCountMap;

    public ShoppingCart(List<Item> cart, double total, Map<String, Integer> itemTypeQuantity, Map<String, Integer> itemCountMap) {
        this.cart = cart;
        this.total = total;
        this.itemTypeQuantity = itemTypeQuantity;
        this.itemCountMap = itemCountMap;
    }

    public ShoppingCart(List<Item> cart) {
        this.cart = cart;
        this.total = 0.0;
        this.itemTypeQuantity = new HashMap<>();
        this.itemCountMap = new HashMap<>();
    }

    public boolean add(Item item) {
        if (canAddToCart(item)) {
            cart.add(item);
            total += item.getPrice();
            updateItemTypeQuantityMap(item, 1);
            updateItemCountMap(item);
            return true;
        }
        return false;
    }
    public void remove(Item item) {
        if (cart.contains(item)) {
            cart.remove(item);
            total -= item.getPrice();
            updateItemTypeQuantityMap(item, -1);
        }
    }

    public void clear() {
        cart.clear();
        total = 0.0;
        itemTypeQuantity.clear();
        itemCountMap.clear();
    }

    public List<Item> getCartItems() { return new ArrayList<>(this.cart); }

    public int getSize() { return cart.size(); }

    public double getTotal() { return total; }

    public boolean contains(Item item) { return this.cart.contains(item); }

    public int getQuantityToDeductStock(Item item) {
        try {
            return itemTypeQuantity.get(item.getName());
        }
        catch (Exception e) {
            System.out.println("ERROR: Key "+ item.getName() + " not found. Exception: "+e);
            return 0;
        }
    }

    public boolean canAddToCart(Item item) {
        //Don't allow for more items in cart than available stock for a certain item
        if (itemCountMap.containsKey(item.getName()) && itemCountMap.get(item.getName()) == item.getStock() || item.getStock() == 0) {
            System.out.println("Number of items in cart for " + item.getName() + " cannot exceed item stock amount");
            return false;
        }
        return true;
    }

    private void updateItemTypeQuantityMap(Item item, int quantity) {
        if (!itemTypeQuantity.containsKey(item.getName())) {
            itemTypeQuantity.put(item.getName(), 1);
        }
        else {
            Integer count = itemTypeQuantity.get(item.getName());
            count += quantity;
            itemTypeQuantity.put(item.getName(), count);
        }
    }

    private void updateItemCountMap(Item item) {
        if (!itemCountMap.containsKey(item.getName())) {
            itemCountMap.put(item.getName(), 1);
        }
        else {
            Integer count = itemCountMap.get(item.getName());
            count += 1;
            itemCountMap.put(item.getName(), count);
        }
    }
}

