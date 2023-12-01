package org.example.marketplace;

import org.example.marketplace.controllers.ItemController;
import org.example.marketplace.entities.Category;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.State;
import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.ItemRepository;
import org.example.marketplace.repositories.UserRepository;
import org.example.marketplace.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PairwiseIntegrationTests {

    Model model;

    ItemController itemController;

    UserService userService;

    UserRepository userRepository;

    ItemService itemService;

    ItemRepository itemRepository;

    StateService stateService;

    Item phone;

    Item tv;

    State arizona;

    State california;

    User user;

    User user2;

    // ItemController.buyItemHelper --> ItemService.buy
    @Nested
    class ItemController_ItemService_Pairwise_Integration_Test {

        @BeforeEach
        void setUp() {
            userService = mock(UserService.class);
            itemRepository = mock(ItemRepository.class);
            itemService = new ItemServiceImpl(itemRepository);
            stateService = mock(StateService.class);
            model = mock(Model.class);

            itemController = new ItemController(userService, itemService, stateService);

            setUpEntities();
            setUpMockreturns();
        }

        void setUpEntities() {
            phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
            tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());

            arizona = new State("AZ",
                    34, true,
                    22, false,
                    50, false,
                    0, true,
                    13, true,
                    21, true, 0.2);

            user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 10000.50);
        }

        void setUpMockreturns() {
            when(userService.getCurrentUser()).thenReturn(user);
            when(userService.canUserAffordPurchase(anyDouble())).thenReturn(true);
        }

        @Test
        void buyItemHelper_Integration_Test() {
            String expected = "redirect:/";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Invalid_Temp_User() {
            when(userService.getCurrentUser()).thenReturn(new User("test" , "test", "test1234", 20,arizona, 4352.54));

            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Please select a new user as the app is initialized with a default/temp user";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_No_Stock() {
            phone.setStock(0);
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone is out of stock";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Invalid_Purchase() {
            when(userService.getCurrentUser()).thenReturn(new User("jose" , "robles", "test1234", -1,arizona, 4352.54));

            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone isn't allowed in your state and/or you aren't old enough to purchase this item.";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Not_Enough_Balance() {
            when(userService.canUserAffordPurchase(anyDouble())).thenReturn(false);

            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Not enough user balance to purchase Phone. Listed price does not include taxes.";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }
    }

    // ItemController.setUserHelper --> userService.userExists
    @Nested
    class ItemController_UserService_Pairwise_Integration_Test {
        @BeforeEach
        void setUp() {

            userRepository = mock(UserRepository.class);
            userService = new UserServiceImpl(userRepository);

            itemService = mock(ItemService.class);
            stateService = mock(StateService.class);
            model = mock(Model.class);

            itemController = new ItemController(userService, itemService, stateService);

            setUpEntities();
            setUpMockreturns();
        }

        void setUpEntities() {
            phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
            tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());

            arizona = new State("AZ",
                    34, true,
                    22, false,
                    50, false,
                    0, true,
                    13, true,
                    21, true, 0.2);

            user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 10000.50);
            user.setId(1L);
        }

        void setUpMockreturns() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        }

        @Test
        void setUserHelper_Integration_Test() {
            when(userRepository.existsById(anyLong())).thenReturn(true);

            String expected = "redirect:/";
            userService.setCurrentUser(user);
            String actual = itemController.setUserSubmit(user, model);
            assertEquals(expected, actual);
        }

        @Test
        void setUserHelper_Integration_Test_Non_Existing_User() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Inputted user ID does not exist...";
            String actual = itemController.setUserSubmit(user, model);
            assertEquals(expected, actual);
        }
    }

    // ItemController.addToCart --> ItemService.addToCart
    @Nested
    class ItemController_ItemService_Pairwise_Integration_Test_2 {
        @BeforeEach
        void setUp() {
            userService = mock(UserService.class);
            itemRepository = mock(ItemRepository.class);
            itemService = new ItemServiceImpl(itemRepository);
            stateService = mock(StateService.class);
            model = mock(Model.class);

            itemController = new ItemController(userService, itemService, stateService);

            setUpEntities();
            setUpMockreturns();
        }

        void setUpEntities() {
            phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
            tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());

            arizona = new State("AZ",
                    34, true,
                    22, false,
                    50, false,
                    0, true,
                    13, true,
                    21, true, 0.2);

            california = new State("CA",
                    22, false,
                    48, true,
                    44, true,
                    31, true,
                    24, true,
                    30, true, 0.20);

            user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 10000.50);
        }

        void setUpMockreturns() {
            when(userService.getCurrentUser()).thenReturn(user);
            when(userService.canUserAffordPurchase(anyDouble())).thenReturn(true);
        }

        @Test
        void addToCart_Integration_Test() {
            String expected = "redirect:/";
            String actual = itemController.addItemToCart(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void addToCart_Integration_Test_No_Stock() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Item cannot be added to cart - not enough stock";
            phone.setStock(0);
            String actual = itemController.addItemToCart(phone, model);
            assertEquals(expected, actual);
        }
    }

    // ItemController.buyItemHelper--> userService.canUserAffordPurchase
    @Nested
    class ItemController_UserService_Pairwise_Integration_Test_2 {
        @BeforeEach
        void setUp() {

            userRepository = mock(UserRepository.class);
            userService = new UserServiceImpl(userRepository);

            itemService = mock(ItemService.class);
            stateService = mock(StateService.class);
            model = mock(Model.class);

            itemController = new ItemController(userService, itemService, stateService);

            setUpEntities();
            setUpMockreturns();
        }

        void setUpEntities() {
            phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
            tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());

            arizona = new State("AZ",
                    34, true,
                    22, false,
                    50, false,
                    0, true,
                    13, true,
                    21, true, 0.2);

            california = new State("CA",
                    22, false,
                    48, true,
                    44, true,
                    31, true,
                    24, true,
                    30, true, 0.20);

            user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 10000.50);
            user2 = new User("Tres", "Hiatt", "tHiatt", 55, california, 10000.50);
        }

        void setUpMockreturns() {
            when(userRepository.existsById(anyLong())).thenReturn(true);
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        }

        @Test
        void buyItemHelper_Integration_Test() {
            when(itemService.isValidPurchase(any(User.class), any(Item.class))).thenReturn(true);

            String expected = "redirect:/";
            userService.setCurrentUser(user2);
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Invalid_Temp_User() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Please select a new user as the app is initialized with a default/temp user";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_No_Stock() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone is out of stock";
            userService.setCurrentUser(user);
            phone.setStock(0);
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Invalid_Purchase() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone isn't allowed in your state and/or you aren't old enough to purchase this item.";
            userService.setCurrentUser(user);
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Integration_Test_Not_Enough_Balance() {
            when(itemService.isValidPurchase(any(User.class), any(Item.class))).thenReturn(true);

            user2.setBalance(1);
            userService.setCurrentUser(user2);

            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Not enough user balance to purchase Phone. Listed price does not include taxes.";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }
    }
}
