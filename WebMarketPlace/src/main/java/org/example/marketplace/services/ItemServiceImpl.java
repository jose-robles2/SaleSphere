package org.example.marketplace.services;

import org.example.marketplace.entities.Item;
import org.example.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final List<Item> shoppingCart;

    private final HashMap<String, Integer> itemCountMap = new HashMap<>();

    public ItemServiceImpl(ItemRepository itemRepository, List<Item> shoppingCart) {
        this.itemRepository = itemRepository;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item save(Item item) { return itemRepository.save(item); }

    @Override
    public void delete(Item item) { this.itemRepository.delete(item); }

    @Override
    public Item buyItem(Item item, int quantity) {
        if (item.getStock() <= 0) {
            System.out.println("ERROR: item is no longer in stock...");
            return item;
        }
        // Validate purchase restrictions
        item.setStock(item.getStock() - quantity);
        return item;
    }

    @Override
    public void addItemToCart(Item item) {
        // Don't allow for more items in cart than available stock for a certain item
        if (itemCountMap.containsKey(item.getName()) && itemCountMap.get(item.getName()) == item.getStock() || item.getStock() == 0) {
            System.out.println("Number of items in cart for " + item.getName() + " cannot exceed item stock amount");
            return;
        }

        // Add the item to the cart and the itemCount map
        shoppingCart.add(item);
        if (!itemCountMap.containsKey(item.getName())) {
            itemCountMap.put(item.getName(), 1);
        }
        else {
            Integer count = itemCountMap.get(item.getName());
            count += 1;
            itemCountMap.put(item.getName(), count);
        }
    }

    @Override
    public int getQuantityToDeductStock(Item targetItem) {
        int quantity = 0;
        for (Item item : shoppingCart) {
            if (item.getName().equals(targetItem.getName())) {
                quantity += 1;
            }
        }
        return quantity;
    }

    @Override
    public List<Item> getShoppingCartItems() {
        return new ArrayList<Item>(shoppingCart);
    }

    @Override
    public int getShoppingCartSize() {
        return shoppingCart.size();
    }

    @Override
    public double getShoppingCartTotal() {
        double total = 0.0;
        for (Item item : shoppingCart) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public void clearShoppingCart() {
        itemCountMap.clear();
        shoppingCart.clear();
    }
}
