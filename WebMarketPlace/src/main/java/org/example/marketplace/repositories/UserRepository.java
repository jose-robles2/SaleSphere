package org.example.marketplace.repositories;

import org.example.marketplace.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
