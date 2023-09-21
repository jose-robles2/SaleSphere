package org.example.marketplace.services;

import org.example.marketplace.entities.Item;
import org.example.marketplace.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
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

    public Boolean buyItem(Item item, int quantity) {

        if (item.getStock() <= 0) {
            return false;
        }

        item.setStock(item.getStock() - 1);
        return true;
    }
}
