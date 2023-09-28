package org.example.marketplace.services;

import org.example.marketplace.entities.User;
import org.example.marketplace.entities.State;
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

        State alabama = new State("AL",
                18, true,
                18, true,
                21, true,
                25, true,
                25, true,
                18, true, 0.07);

        User temp = new User("test", "test", "test1234", 20,alabama);

        this.currentUser = Optional.of(temp);

    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public User getUser(Long ID) { return userRepository.findById(ID).get(); }
    @Override
    public boolean userExists(Long ID) { return userRepository.existsById(ID); }
    @Override
    public User getCurrentUser()
    {
        Optional<User> currentUser = this.currentUser;
        if (currentUser != null) {
            return currentUser.get();
        }
        return null;
    }
    @Override
    public void setCurrentUser(User currUser)
    {
        if(currUser != null)
        {
            this.currentUser = Optional.ofNullable(currUser);
        }
    }

    @Override
    public double getTax(double total)
    {
        return (this.currentUser.get().getState().getTaxRate()) * total;
    }
    @Override
    public double getTotalWithTax(double total)
    {
        return (this.currentUser.get().getState().getTaxRate() + 1) * total;
    }
}