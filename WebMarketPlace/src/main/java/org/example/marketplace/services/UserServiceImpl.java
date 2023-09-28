package org.example.marketplace.services;

import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private Optional<User> currentUser;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.currentUser = Optional.of(new User("Will", "Hiatt", "wHiatt", "Washington"));

    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long ID)
    {
        return userRepository.findById(ID).get();
    }

    public User getCurrentUser()
    {
        Optional<User> currentUser = this.currentUser;
        if (currentUser != null) {
            return currentUser.get();
        }
        return null;
    }

    public void setCurrentUser(User currUser)
    {
        this.currentUser = Optional.ofNullable(currUser);
    }
}