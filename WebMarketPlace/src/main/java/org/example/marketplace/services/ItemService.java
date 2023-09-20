package org.example.marketplace.services;
import org.example.marketplace.entities.Item;

public interface ItemService {
    public Iterable<Item> findAll();

    public Item save(Item item);
}
