package org.example.marketplace.services;

import org.example.marketplace.entities.Item;
import org.example.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
}
