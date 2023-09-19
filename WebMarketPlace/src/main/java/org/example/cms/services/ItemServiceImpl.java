package org.example.cms.services;

import org.example.cms.entities.Item;
import org.example.cms.repositories.ItemRepository;

public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
