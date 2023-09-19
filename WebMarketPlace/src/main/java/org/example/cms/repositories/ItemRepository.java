package org.example.cms.repositories;

import org.example.cms.entities.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
