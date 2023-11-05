package org.example.marketplace.services;

import org.example.marketplace.entities.Category;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.User;
import org.example.marketplace.entities.State;
import org.example.marketplace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;

import static org.example.marketplace.entities.Category.*;

@Service
public class UserServiceImpl implements UserService{

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

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

        User temp = new User("test", "test", "test1234", 20,alabama, 4352.54);

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
        return currentUser.orElse(null);
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
        Double beforeFormat = this.currentUser.get().getState().getTaxRate() * total;
        return Double.parseDouble(decimalFormat.format(beforeFormat));
    }

    @Override
    public double getTax(Item item)
    {
        double beforeFormat = 0.0;
        double luxuryTax = getLuxuryTax(item);
        if (luxuryTax == 0.0) {
            beforeFormat = this.currentUser.get().getState().getTaxRate() * item.getPrice();
        }
        else {
            beforeFormat = (this.currentUser.get().getState().getTaxRate() + luxuryTax) * item.getPrice();
        }
        return Double.parseDouble(decimalFormat.format(beforeFormat));
    }

    @Override
    public double getTotalWithTax(double total)
    {
        Double beforeFormat = (this.currentUser.get().getState().getTaxRate() + 1) * total;
        return Double.parseDouble(decimalFormat.format(beforeFormat));
    }

    @Override
    public void makePurchase(Item item, int quantity)
    {
        Double luxuryTax = getTax(item);
        User user = getCurrentUser();

        double answer = user.getBalance() - ((item.getPrice() * quantity) * ((user.getState().getTaxRate() * luxuryTax) + 1));
        double formattedAnswer = Double.parseDouble(decimalFormat.format(answer));
        user.setBalance(formattedAnswer);
    }

    @Override
    public void makePurchase(Item item, int quantity, User user)
    {
        Double luxuryTax = getTax(item);

        double answer = user.getBalance() - ((item.getPrice() * quantity) * ((user.getState().getTaxRate() * luxuryTax) + 1));
        double formattedAnswer = Double.parseDouble(decimalFormat.format(answer));
        user.setBalance(formattedAnswer);
    }

    @Override
    public boolean canUserAffordPurchase(double itemPrice)
    {
        User user = getCurrentUser();
        return (user.getBalance() - (itemPrice * (user.getState().getTaxRate() + 1))) >= 0;
    }

    private double getLuxuryTax(Item item) {
        if (item.getCategory() < 0 || item.getCategory() >= Category.values().length) {
            return 0.0;
        }

        Category category = Category.values()[item.getCategory()]; // Convert category ordinal to enum

        String userState = currentUser.get().getState().getStateName();


        return switch (category) {
            case FIREARM -> (userState.equals("AZ") || userState.equals("AR")) ? 0.05 : 0;
            case ALCOHOL -> (userState.equals("AZ") || userState.equals("AK")) ? 0.10 : 0;
            case DRUGS -> (userState.equals("CA") || userState.equals("AR")) ? 0.15 : 0;
            case MEDICINE -> (userState.equals("AK") || userState.equals("CA")) ? 0.05 : 0;
            case TECHNOLOGY -> (userState.equals("AR") || userState.equals("AZ")) ? 0.07 : 0;
            case TOBACCO -> (userState.equals("CA") || userState.equals("AK")) ? 0.09 : 0;
        };
    }
}
