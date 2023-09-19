package org.example.cms.services;

import org.example.cms.entities.User;

public interface UserService {
    public Iterable<User> findAll();

    public User save(User instructor);
}
