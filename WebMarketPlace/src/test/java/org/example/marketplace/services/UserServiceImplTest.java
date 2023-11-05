package org.example.marketplace.services;

import org.example.marketplace.entities.*;
import org.example.marketplace.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserRepository user_repository;

    private UserService userService;

    private User user;

    private User user2;

    private User user3;

    private State alabama;

    private State alaska;

    private State arizona;

    private Item phone;

    private Item tv;

    private Item laptop;


    @BeforeEach
    void setUp()
    {
        user_repository = mock(UserRepository.class);
        userService = new UserServiceImpl(user_repository);

        phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
        tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());
        laptop = new Item("TV", "Samsung 4K TV", "/image", "Washington", 999.99, 5, Category.TECHNOLOGY.ordinal());

        setUpUsersAndStates();
        setUpMockReturns();
    }

    void setUpUsersAndStates()
    {
        alabama = new State("AL",
                18, false,
                18, false,
                21, false,
                25, false,
                25, false,
                18, false, 0.07);

        alaska = new State("AK",
                21, true,
                15, true,
                33, true,
                32, true,
                1, true,
                99, true, 0.06);

        arizona = new State("AZ",
                34, true,
                22, false,
                50, false,
                18, true,
                18, true,
                21, true, 0.2);


        user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 1000.50);
        user2 = new User("Jose", "Robles", "jRob", 17, alaska, 14567.87);
        user3 = new User("Josh", "Long", "jLong", 99, alaska, 105500.50);

        userService.setCurrentUser(user);
    }

    void setUpMockReturns()
    {
        Random random = new Random();
        when(user_repository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User saved_user = invocationOnMock.getArgument(0);
            saved_user.setId(random.nextLong());
            return saved_user;
        });
    }

    @Test
    void findAllTest() {
        when(user_repository.findAll()).thenReturn(List.of(user, user2, user3));

        List<User> expected = new ArrayList<>(List.of(user, user2, user3));
        assertEquals(userService.findAll(), expected);
    }

    @Test
    void saveTest() {
        User user4 = new User("Test", "Testing", "tTest", 42, alabama, 100.50);

        user4 = userService.save(user4);
        assertNotEquals(user4.getId(), null);
    }

    @Test
    void getUserTest() {
        when(user_repository.findById(1L)).thenReturn(Optional.ofNullable(user));

        User expected = user;

        assertEquals(userService.getUser(1L), expected);
    }

    @Test
    void userExistsTest() {
        when(user_repository.existsById(1L)).thenReturn(true);

        Boolean expected = true;

        assertEquals(userService.userExists(1L), expected);
    }

    @Test
    void getCurrentUserTest() {
        User expected = user;

        assertEquals(userService.getCurrentUser(), expected);
    }

    @Test
    void setCurrentUserTest() {
        User expected = user2;

        userService.setCurrentUser(expected);

        assertEquals(userService.getCurrentUser(), expected);
    }

    @Test
    void setCurrentUser2Test() {
        User expected = user;
        User nullUser = null;

        userService.setCurrentUser(nullUser);

        assertEquals(userService.getCurrentUser(), expected);
    }

    @Test
    void getTaxTest() {
        double expected = 100.0;

        assertEquals(userService.getTax(500), expected);
    }

    @Test
    void getTaxTest2() {
        Item itemToTax = phone;
        double expected = 16.8;

        assertEquals(userService.getTax(phone), expected);
    }

    @Test
    void getTaxTest3() {
        Item itemToTax = phone;
        double expected = 72.0;

        User user = user3;

        userService.setCurrentUser(user);

        assertEquals(userService.getTax(phone), expected);
    }

    @Test
    void getTotalWithTaxTest() {
        double expected = 600.0;

        assertEquals(userService.getTotalWithTax(500), expected);
    }

    @Test
    void makePurchaseTest() {
        Item purchaseItem = tv;
        int quantity = 1;
        User purchaseUser = user;
        double balanceAfter = -14751.43;

        userService.makePurchase(purchaseItem, quantity);

        assertEquals(purchaseUser.getBalance(), balanceAfter);
    }

    @Test
    void makePurchaseTest2() {
        Item purchaseItem = tv;
        int quantity = 1;
        User purchaseUser = user;
        double balanceAfter = -14751.43;

        userService.makePurchase(purchaseItem, quantity, user);

        assertEquals(purchaseUser.getBalance(), balanceAfter);
    }

    @Test
    void canUserAffordPurchaseTest() {
        double itemPrice = 45.33;
        User purchaseUser = user;
        boolean expected = true;

        assertEquals(userService.canUserAffordPurchase(itemPrice), expected);
    }

    @Test
    void canUserAffordPurchase2Test() {

        double itemPrice = 10_000;
        User purchaseUser = user;
        boolean expected = false;

        assertEquals(userService.canUserAffordPurchase(itemPrice), expected);
    }

    @Test
    void getLuxuryTaxTest() {
        // This is a private method and will not be tested
            // Luxury task is an internal method used by user service impl
                // and cannot be called elsewhere as it is not needed
    }
}