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

    private User user4;

    private User user5;

    private State alabama;

    private State alaska;

    private State arizona;

    private State arkansas;

    private State california;

    private Item phone;

    private Item tv;

    private Item firearm;

    private Item alcohol;

    private Item drug;

    private Item medicine;

    private Item tobacco;

    @BeforeEach
    void setUp()
    {
        user_repository = mock(UserRepository.class);
        userService = new UserServiceImpl(user_repository);

        phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
        tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());

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
                0, true,
                13, true,
                21, true, 0.2);

        arkansas = new State("AR",
                99, true,
                18, true,
                21, true,
                25, true,
                25, true,
                18, false, 0.13);

        california = new State("CA",
                22, false,
                48, true,
                44, true,
                31, true,
                24, true,
                30, true, 0.20);

        firearm = new Item("Handgun", "Kimber Stainless 45 Auto", "/image", "Idaho", 699.99, 3, Category.FIREARM.ordinal());
        alcohol = new Item("Rum", "Malibu Caribbean Rum", "/image", "Oregon", 19.99, 4, Category.ALCOHOL.ordinal());
        medicine = new Item("Hydrocodone Painkillers", "Hydrocodone Max Strength", "/image", "California", 59.99, 4, Category.MEDICINE.ordinal());
        drug= new Item("Classic Marijuana", "Large weed pack", "/image", "California", 9.99, 0, Category.DRUGS.ordinal());
        tobacco =  new Item("Classic Cigarettes", "Large tobacco cigarette pack", "/image", "Washington", 9.99, 4, Category.TOBACCO.ordinal());


        user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 1000.50);
        user2 = new User("Jose", "Robles", "jRob", 17, alaska, 14567.87);
        user3 = new User("Josh", "Long", "jLong", 99, alaska, 105500.50);
        user4 = new User("Jose", "Robles", "jRob", 99, alaska, 14567.87);
        user5 = new User("Jose", "Robles", "jRob", 99, arkansas, 14567.87);

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
    void saveTest2() {
        User user4 = new User("Test", "Testing", "tTest", 42, alabama, 100.50);

        User user5 = userService.save(user4);
        User user6 = userService.save(user4);

        assertEquals(user5, user6);
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
    void getTaxTestTechnology1() {
        Item itemToTax = phone;
        double expected = 240;

        user.setState(california);
        userService.setCurrentUser(user);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestTechnology2() {
        Item itemToTax = phone;
        double expected = 324.0;

        user3.setState(arizona);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestTechnology3() {
        Item itemToTax = phone;
        double expected = 240.0;

        user3.setState(arkansas);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestFirearm1() {
        Item itemToTax = firearm;
        double expected = 175.0;

        user.setState(arizona);
        userService.setCurrentUser(user);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestFirearm2() {
        Item itemToTax = firearm;
        double expected = 42.0;

        user3.setState(alaska);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestFirearm3() {
        Item itemToTax = firearm;
        double expected = 126.0;

        user3.setState(arkansas);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestAlcohol1() {
        Item itemToTax = alcohol;
        double expected = 6.0;

        user3.setState(arizona);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestAlcohol2() {
        Item itemToTax = alcohol;
        double expected = 2.6;

        user3.setState(arkansas);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestAlcohol3() {
        Item itemToTax = alcohol;
        double expected = 3.2;

        user3.setState(alaska);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestDrug1() {
        Item itemToTax = drug;
        double expected = 3.5;

        user3.setState(california);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestDrug2() {
        Item itemToTax = drug;
        double expected = 0.6;

        user3.setState(alaska);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestDrug3() {
        Item itemToTax = drug;
        double expected = 2.8;

        user3.setState(arkansas);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestMedicine1() {
        Item itemToTax = medicine;
        double expected = 6.6;

        user3.setState(alaska);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestMedicine2() {
        Item itemToTax = medicine;
        double expected = 12.0;

        user3.setState(arizona);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestMedicine3() {
        Item itemToTax = medicine;
        double expected = 15.0;

        user3.setState(california);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestTobacco1() {
        Item itemToTax = tobacco;
        double expected = 2.9;

        user3.setState(california);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestTobacco2() {
        Item itemToTax = tobacco;
        double expected = 1.3;

        user3.setState(arkansas);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestTobacco3() {
        Item itemToTax = tobacco;
        double expected = 1.5;

        user3.setState(alaska);
        userService.setCurrentUser(user3);

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestInvalidItem() {
        Item itemToTax = new Item("temp","temp","temp","temp",0.0,0,14231);
        double expected = 0.0;

        assertEquals(userService.getTax(itemToTax), expected);
    }

    @Test
    void getTaxTestInvalidItem2() {
        Item itemToTax = new Item("temp","temp","temp","temp",0.0,0,-1);
        double expected = 0.0;

        assertEquals(userService.getTax(itemToTax), expected);
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
        double balanceAfter = -262558.3;

        userService.makePurchase(purchaseItem, quantity);

        assertEquals(purchaseUser.getBalance(), balanceAfter);
    }

    @Test
    void makePurchaseTest2() {
        Item purchaseItem = tv;
        int quantity = 1;
        User purchaseUser = user;
        double balanceAfter = -262558.3;

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
}