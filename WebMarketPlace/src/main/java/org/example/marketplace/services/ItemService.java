package org.example.marketplace.services;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;

import java.util.List;

public interface ItemService {
    public Iterable<Item> findAll();

    public Item save(Item item);

    public void delete(Item item);

    public Item buyItem(Item item, int quantity);

    public void addItemToCart(Item item);

    public int getQuantityToDeductStock(Item item);

    public List<Item> getShoppingCartItems();

    public int getShoppingCartSize();

    public double getShoppingCartTotal();

    public void clearShoppingCart();

    public String getErrorMessage(Item item, int quantity);

    public boolean checkPurchase(User currUser, Item item);
}
