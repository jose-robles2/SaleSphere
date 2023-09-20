package org.example.marketplace.repositories;

import org.example.marketplace.entities.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
