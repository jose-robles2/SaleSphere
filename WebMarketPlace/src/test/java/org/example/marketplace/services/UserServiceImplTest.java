//package org.example.marketplace.services;
//
//import org.example.marketplace.entities.Category;
//import org.example.marketplace.entities.Item;
//import org.example.marketplace.entities.State;
//import org.example.marketplace.entities.User;
//import org.example.marketplace.repositories.ItemRepository;
//import org.example.marketplace.repositories.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//
//class UserServiceImplTest {
//
//    private UserRepository user_repository;
//
//    private UserServiceImpl user_service;
//
//    private UserService userService;
//
//    private User user;
//    private User user2;
//    private User user3;
//    private State alabama;
//    private State alaska;
//    private State arizona;
//
//    @BeforeEach
//    void setUp()
//    {
//        user_repository = mock(UserRepository.class);
//        user_service = new UserServiceImpl(user_repository);
//
//        setUpUsersAndStates();
//        setUpMockReturns();
//    }
//
//    void setUpUsersAndStates()
//    {
//        alabama = new State("AL",
//                18, false,
//                18, false,
//                21, false,
//                25, false,
//                25, false,
//                18, false, 0.07);
//
//        alaska = new State("AK",
//                21, true,
//                15, true,
//                33, true,
//                32, true,
//                1, true,
//                99, true, 0.06);
//
//        arizona = new State("AZ",
//                34, true,
//                22, false,
//                50, false,
//                18, true,
//                18, true,
//                21, true, 0.2);
//
//
//        user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 1000.50);
//        user2 = new User("Jose", "Robles", "jRob", 17, alaska, 14567.87);
//        user3 = new User("Josh", "Long", "jLong", 99, alaska, 105500.50);
//
//        user_service.setCurrentUser(user);
//    }
//
//    void setUpMockReturns()
//    {
//        Random random = new Random();
//        when(user_repository.save(any(User.class))).thenAnswer(invocationOnMock -> {
//            User saved_user = invocationOnMock.getArgument(0);
//            saved_user.setId(random.nextLong());
//            return saved_user;
//        });
//    }
//
//    @Test
//    void findAll() {
//        when(user_repository.findAll()).thenReturn(List.of(user, user2, user3));
//
//        List<User> expected = new ArrayList<>(List.of(user, user2, user3));
//        assertEquals(user_service.findAll(), expected);
//    }
//
//    @Test
//    void save() {
//        User user4 = new User("Test", "Testing", "tTest", 42, alabama, 100.50);
//
//        user4 = user_service.save(user4);
//        assertNotEquals(user4.getId(), null);
//    }
//
//    @Test
//    void getUser() {
//        when(user_repository.findById(1L)).thenReturn(Optional.ofNullable(user));
//
//        User expected = user;
//
//        assertEquals(user_service.getUser(1L), expected);
//    }
//
//    @Test
//    void userExists() {
//        when(user_repository.existsById(1L)).thenReturn(true);
//
//        Boolean expected = true;
//
//        assertEquals(user_service.userExists(1L), expected);
//    }
//
//    @Test
//    void getCurrentUser() {
//        User expected = user;
//
//        assertEquals(user_service.getCurrentUser(), expected);
//    }
//
//    @Test
//    void setCurrentUser() {
//        User expected = user2;
//
//        user_service.setCurrentUser(expected);
//
//        assertEquals(user_service.getCurrentUser(), expected);
//    }
//
//    @Test
//    void setCurrentUser2() {
//        User expected = user;
//        User nullUser = null;
//
//        user_service.setCurrentUser(nullUser);
//
//        assertEquals(user_service.getCurrentUser(), expected);
//    }
//
//    @Test
//    void getTax() {
//        double expected = 100.0;
//
//        assertEquals(user_service.getTax(500), expected);
//    }
//
//    @Test
//    void getTotalWithTax() {
//        double expected = 600.0;
//
//        assertEquals(user_service.getTotalWithTax(500), expected);
//    }
//
//    @Test
//    void makePurchase() {
//        double itemPrice = 31.50;
//        int quantity = 3;
//        User purchaseUser = user;
//        double balanceAfter = 887.1;
//
//        user_service.makePurchase(itemPrice, quantity, purchaseUser);
//
//        assertEquals(purchaseUser.getBalance(), balanceAfter);
//    }
//
//    @Test
//    void checkBalance() {
//        double itemPrice = 45.33;
//        User purchaseUser = user;
//        boolean expected = true;
//
//        assertEquals(user_service.checkBalance(itemPrice, purchaseUser), expected);
//    }
//
//    @Test
//    void checkBalance2() {
//        double itemPrice = 4500.33;
//        User purchaseUser = user;
//        boolean expected = false;
//
//        assertEquals(user_service.checkBalance(itemPrice, purchaseUser), expected);
//    }
//}