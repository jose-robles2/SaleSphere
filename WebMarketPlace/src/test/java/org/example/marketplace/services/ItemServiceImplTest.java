package org.example.marketplace.services;

import org.example.marketplace.entities.Category;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.State;
import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    private List<Item> shoppingCart;

    private ItemRepository itemRepository;

    private ItemService itemService;

    private Item phone;

    private Item tv;

    private Item laptop;

    private Item firearm;

    private Item alcohol;

    private Item drug;

    private Item medicine;

    private Item tobacco;

    private State alabama;

    private State alaska;

    private State arizona;

    private State arkansas;

    private State california;

    @BeforeEach
    void setUp() {
        shoppingCart = new ArrayList<>();
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemServiceImpl(itemRepository, shoppingCart);

        setUpMockReturns();
        setUpItemsAndStates();
    }

    void setUpMockReturns() {
        Random random = new Random();
        when(itemService.save(any(Item.class))).thenAnswer(invocationOnMock -> {
            Item savedItem = invocationOnMock.getArgument(0);
            savedItem.setId(random.nextLong());
            return savedItem;
        });
    }

    void setUpItemsAndStates() {
        phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
        tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());
        laptop = new Item("TV", "Samsung 4K TV", "/image", "Washington", 999.99, 5, Category.TECHNOLOGY.ordinal());
        firearm = new Item("Handgun", "Kimber Stainless 45 Auto", "/image", "Idaho", 699.99, 3, Category.FIREARM.ordinal());
        alcohol = new Item("Rum", "Malibu Caribbean Rum", "/image", "Oregon", 19.99, 4, Category.ALCOHOL.ordinal());
        medicine = new Item("Hydrocodone Painkillers", "Hydrocodone Max Strength", "/image", "California", 59.99, 4, Category.MEDICINE.ordinal());
        drug= new Item("Classic Marijuana", "Large weed pack", "/image", "California", 9.99, 0, Category.DRUGS.ordinal());
        tobacco =  new Item("Classic Cigarettes", "Large tobacco cigarette pack", "/image", "Washington", 9.99, 4, Category.TOBACCO.ordinal());

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
    }

    @Test
    void findAllTest() {
        when(itemService.findAll()).thenReturn(List.of(phone, tv, laptop));

        List<Item> expected = new ArrayList<>(List.of(phone, tv, laptop));
        assertEquals(itemService.findAll(), expected);
    }

    @Test
    void saveTest() {
        Item tablet = new Item("Tablet", "iPad Pro", "/image", "Washington", 799.99, 5, Category.TECHNOLOGY.ordinal());

        tablet = itemService.save(tablet);
        assertNotEquals(tablet.getId(), null);
    }

    @Test
    void deleteTest() {
        Item itemToDelete = phone;
        itemService.delete(itemToDelete);

        // Verify that the delete method was called with the correct item since delete() doesn't return a val
        verify(itemRepository).delete(itemToDelete);
    }

    @Test
    void buyItemTest1() {
        Item itemToBuy = phone;
        int quantity = 1;
        int expectedStock = itemToBuy.getStock() - 1;

        Item updatedItem = itemService.buyItem(itemToBuy, quantity);

        assertEquals(updatedItem.getStock(), expectedStock);
    }

    @Test
    void buyItemTest2() {
        Item itemToBuy = phone;
        int quantity = 1;
        itemToBuy.setStock(0);

        Item updatedItem = itemService.buyItem(itemToBuy, quantity);

        assertEquals(itemToBuy, updatedItem);
    }

    @Test
    void buyItemTest3() {
        Item itemToBuy = phone;
        int quantity = 1, expectedCartSize = 0;
        itemService.addItemToCart(itemToBuy);

        Item updatedItem = itemService.buyItem(itemToBuy, quantity);

        assertEquals(itemService.getShoppingCartSize(), expectedCartSize);
    }

    @Test
    void buyItemTest4() {
        Item itemToBuy = phone;
        int quantity = 1, expectedCartSize = 1;
        itemService.addItemToCart(tv);

        Item updatedItem = itemService.buyItem(itemToBuy, quantity);

        assertEquals(itemService.getShoppingCartSize(), expectedCartSize);
    }

    @Test
    void addItemToCartTest1() {
        Item itemToAdd = phone;
        int expectedCartSize = 1;
        itemService.addItemToCart(itemToAdd);

        assertEquals(itemService.getShoppingCartSize(), expectedCartSize);
    }

    @Test
    void addItemToCartTest2() {
        Item itemToAdd = phone;
        int expectedCartSize = 1;

        itemToAdd.setStock(1);
        itemService.addItemToCart(itemToAdd);
        itemService.addItemToCart(itemToAdd);

        assertEquals(itemService.getShoppingCartSize(), expectedCartSize);
    }

    @Test
    void addItemToCartTest3() {
        Item itemToAdd = laptop;
        int expectedCartSize = 2;

        itemService.addItemToCart(itemToAdd);
        itemService.addItemToCart(itemToAdd);

        assertEquals(itemService.getShoppingCartSize(), expectedCartSize);
    }

    @Test
    void getQuantityToDeductStockTest() {
        Item targetItem = laptop;
        int expectedQuantity = 3;

        itemService.addItemToCart(targetItem);
        itemService.addItemToCart(targetItem);
        itemService.addItemToCart(targetItem);

        assertEquals(itemService.getQuantityToDeductStock(targetItem), expectedQuantity);
    }

    @Test
    void getShoppingCartItemsTest() {
        itemService.addItemToCart(phone);
        assertEquals(itemService.getShoppingCartItems(), List.of(phone));
    }

    @Test
    void getShoppingCartSizeTest() {
        itemService.addItemToCart(tv);
        itemService.addItemToCart(laptop);
        assertEquals(itemService.getShoppingCartSize(), 2);
    }

    @Test
    void getShoppingCartTotalTest() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double expectedTotal = phone.getPrice() + tv.getPrice() + laptop.getPrice();
        expectedTotal = Double.parseDouble(decimalFormat.format(expectedTotal));
        itemService.addItemToCart(phone);
        itemService.addItemToCart(tv);
        itemService.addItemToCart(laptop);

        assertEquals(itemService.getShoppingCartTotal(), expectedTotal);
    }

    @Test
    void clearShoppingCartTest() {
        itemService.addItemToCart(phone);
        itemService.addItemToCart(tv);
        itemService.addItemToCart(laptop);
        itemService.clearShoppingCart();

        assertEquals(itemService.getShoppingCartSize(), 0);
    }

    @Test
    void isValidPurchaseTest_Firearm1() {
        User user = new User("Jose", "Robles", "jRob", 17, alabama, 14567.87);

        assertFalse(itemService.isValidPurchase(user, firearm));
    }

    @Test
    void isValidPurchaseTest_Firearm2() {
        User user = new User("Jose", "Robles", "jRob", 17, alaska, 14567.87);

        assertFalse(itemService.isValidPurchase(user, firearm));
    }

    @Test
    void isValidPurchaseTest_Firearm3() {
        User user = new User("Jose", "Robles", "jRob", 21, alaska, 14567.87);

        assertTrue(itemService.isValidPurchase(user, firearm));
    }

    @Test
    void isValidPurchaseTest_Firearm4() {
        User user = new User("Jose", "Robles", "jRob", 99, arkansas, 14567.87);

        assertTrue(itemService.isValidPurchase(user, firearm));
    }

    @Test
    void isValidPurchaseTest_Alcohol1() {
        User user = new User("Jose", "Robles", "jRob", 17, arkansas, 14567.87);

        assertFalse(itemService.isValidPurchase(user, alcohol));
    }

    @Test
    void isValidPurchaseTest_Alcohol2() {
        User user = new User("Jose", "Robles", "jRob", 17, california, 14567.87);

        assertFalse(itemService.isValidPurchase(user, alcohol));
    }

    @Test
    void isValidPurchaseTest_Alcohol3() {
        User user = new User("Jose", "Robles", "jRob", 33, california, 14567.87);

        assertTrue(itemService.isValidPurchase(user, alcohol));
    }

    @Test
    void isValidPurchaseTest_Alcohol4() {
        User user = new User("Jose", "Robles", "jRob", 99, alaska, 14567.87);

        assertTrue(itemService.isValidPurchase(user, alcohol));
    }

    @Test
    void isValidPurchaseTest_Drugs1() {
        User user = new User("Jose", "Robles", "jRob", 17, arizona, 14567.87);

        assertFalse(itemService.isValidPurchase(user, drug));
    }

    @Test
    void isValidPurchaseTest_Drugs2() {
        User user = new User("Jose", "Robles", "jRob", 17, california, 14567.87);

        assertFalse(itemService.isValidPurchase(user, drug));
    }

    @Test
    void isValidPurchaseTest_Drugs3() {
        User user = new User("Jose", "Robles", "jRob", 45, california, 14567.87);

        assertTrue(itemService.isValidPurchase(user, drug));
    }

    @Test
    void isValidPurchaseTest_Drugs4() {
        User user = new User("Jose", "Robles", "jRob", 99, alaska, 14567.87);

        assertTrue(itemService.isValidPurchase(user, drug));
    }

    @Test
    void isValidPurchaseTest_Medicine1() {
        User user = new User("Jose", "Robles", "jRob", 17, alabama, 14567.87);

        assertFalse(itemService.isValidPurchase(user, medicine));
    }

    @Test
    void isValidPurchaseTest_Medicine2() {
        User user = new User("Jose", "Robles", "jRob", 10, arizona, 14567.87);

        assertFalse(itemService.isValidPurchase(user, medicine));
    }

    @Test
    void isValidPurchaseTest_Medicine3() {
        User user = new User("Jose", "Robles", "jRob", 33, arizona, 14567.87);

        assertTrue(itemService.isValidPurchase(user, medicine));
    }

    @Test
    void isValidPurchaseTest_Medicine4() {
        User user = new User("Jose", "Robles", "jRob", 99, california, 14567.87);

        assertTrue(itemService.isValidPurchase(user, medicine));
    }

    @Test
    void isValidPurchaseTest_Technology1() {
        User user = new User("Jose", "Robles", "jRob", 17, alabama, 14567.87);

        assertFalse(itemService.isValidPurchase(user, phone));
    }

    @Test
    void isValidPurchaseTest_Technology2() {
        User user = new User("Jose", "Robles", "jRob", 10, alaska, 14567.87);

        assertFalse(itemService.isValidPurchase(user, phone));
    }

    @Test
    void isValidPurchaseTest_Technology3() {
        User user = new User("Jose", "Robles", "jRob", 53, alaska, 14567.87);

        assertTrue(itemService.isValidPurchase(user, phone));
    }

    @Test
    void isValidPurchaseTest_Technology4() {
        User user = new User("Jose", "Robles", "jRob", 99, arizona, 14567.87);

        assertTrue(itemService.isValidPurchase(user, phone));
    }

    @Test
    void isValidPurchaseTest_Tobacco1() {
        User user = new User("Jose", "Robles", "jRob", 17, arizona, 14567.87);

        assertFalse(itemService.isValidPurchase(user, tobacco));
    }

    @Test
    void isValidPurchaseTest_Tobacco2() {
        User user = new User("Jose", "Robles", "jRob", 10, arkansas, 14567.87);

        assertFalse(itemService.isValidPurchase(user, tobacco));
    }

    @Test
    void isValidPurchaseTest_Tobacco3() {
        User user = new User("Jose", "Robles", "jRob", 53, arkansas, 14567.87);

        assertTrue(itemService.isValidPurchase(user, tobacco));
    }

    @Test
    void isValidPurchaseTest_Tobacco4() {
        User user = new User("Jose", "Robles", "jRob", 99, california, 14567.87);

        assertTrue(itemService.isValidPurchase(user, tobacco));
    }

    @Test
    void isValidPurchaseTest_UnsupportedItem() {
        Item invalidItem = new Item("item", "item", "item", "item", 0,0,10);
        User user = new User("Jose", "Robles", "jRob", 99, california, 14567.87);

        assertFalse(itemService.isValidPurchase(user, invalidItem));
    }
}