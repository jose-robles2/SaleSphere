package org.example.marketplace.services;

import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private Optional<User> currentUser = this.getUser(0L);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(Long ID)
    {
        return userRepository.findById(ID);
    }

    public Optional<User> getCurrentUser()
    {
        return this.currentUser;
    }


}
