package org.example.marketplace.services;

import org.example.marketplace.entities.User;

public interface UserService {
    public Iterable<User> findAll();

    public User save(User instructor);
}
