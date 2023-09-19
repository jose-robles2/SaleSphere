package org.example.cms.services;
import org.example.cms.entities.Item;

public interface ItemService {
    public Iterable<Item> findAll();

    public Item save(Item item);
}
