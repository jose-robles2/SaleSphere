package org.example.marketplace.services;

import org.example.marketplace.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Iterable<User> findAll();

    public User save(User instructor);

    public User getUser(Long ID);

    User getCurrentUser();

    public void setCurrentUser(User currUser);

    public double getTax(double total);

    public double getTotalWithTax(double total);

    public boolean userExists(Long ID);

    public void makePurchase(double itemPrice, User user);

    public boolean checkBalance(double itemPrice, User user);
}
