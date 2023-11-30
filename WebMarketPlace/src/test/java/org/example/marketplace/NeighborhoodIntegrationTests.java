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
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NeighborhoodIntegrationTests {

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
    class ItemController_ItemService_Neighborhood_Integration_Test {
        @BeforeEach
        void setUp() {
            // Real entities involved: ItemController, ItemService, UserService, Item, User
            // Everything else: Mock
            userRepository = mock(UserRepository.class);
            userService = new UserServiceImpl(userRepository);
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
            user2 = new User("Tres", "Hiatt", "tHiatt", 55, california, 10000.50);
        }

        void setUpMockreturns() {

        }

        @Test
        void buyItemHelper_Neighborhood_Integration_Test() {
            userService.setCurrentUser(user);

            String expected = "redirect:/";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Neighborhood_Integration_Test_Invalid_Temp_User() {
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Please select a new user as the app is initialized with a default/temp user";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Neighborhood_Integration_Test_No_Stock() {
            userService.setCurrentUser(user);
            phone.setStock(0);
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone is out of stock";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Neighborhood_Integration_Test_Invalid_Purchase() {
            userService.setCurrentUser(new User("jose" , "robles", "test1234", -1,arizona, 4352.54));
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: The item Phone isn't allowed in your state and/or you aren't old enough to purchase this item.";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }

        @Test
        void buyItemHelper_Neighborhood_Integration_Test_Not_Enough_Balance() {
            user.setBalance(0);
            userService.setCurrentUser(user);
            String expected = "redirect:/triggerError?customErrorMsg=ERROR: Not enough user balance to purchase Phone. Listed price does not include taxes.";
            String actual = itemController.buyItem(phone, model);
            assertEquals(expected, actual);
        }
    }
}
