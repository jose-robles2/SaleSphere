package org.example.marketplace.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    private Item phone;

    private Item tv;

    private Item laptop;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart(new ArrayList<Item>());

        phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
        tv = new Item("Laptop", "Macbook Pro", "/image", "Washington", 2199.99, 5, Category.TECHNOLOGY.ordinal());
        laptop = new Item("TV", "Samsung 4K TV", "/image", "Washington", 999.99, 5, Category.TECHNOLOGY.ordinal());

    }

    @Test
    void addTest() {
        int expectedSize = 3;
        shoppingCart.add(phone);
        assertFalse(shoppingCart.add(phone));
    }

    @Test
    void addTest2() {
        int expectedSize = 3;
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        assertEquals(shoppingCart.getSize(), expectedSize);
    }

    @Test
    void removeTest() {
        int expectedSize = 2;
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        shoppingCart.remove(laptop);
        assertEquals(shoppingCart.getSize(), expectedSize);
    }

    @Test
    void clearTest() {
        int expectedSize = 0;
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        shoppingCart.clear();
        assertEquals(shoppingCart.getSize(), expectedSize);
    }

    @Test
    void getCartItemsTest() {
        List<Item> expectedItems = List.of(phone, tv, laptop);
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        assertEquals(shoppingCart.getCartItems(), expectedItems);
    }

    @Test
    void getSizeTest() {
        int expectedSize = 3;
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        assertEquals(shoppingCart.getSize(), expectedSize);
    }

    @Test
    void getTotalTest() {
        double expectedTotal = phone.getPrice() + tv.getPrice() + laptop.getPrice();
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        assertEquals(shoppingCart.getTotal(), expectedTotal);
    }

    @Test
    void containsTest() {
        shoppingCart.add(phone);
        shoppingCart.add(tv);
        shoppingCart.add(laptop);
        assertTrue(shoppingCart.contains(phone));
    }

    @Test
    void getQuantityToDeductStockTest() {
        int expectedQuantity = 3;
        shoppingCart.add(tv);
        shoppingCart.add(tv);
        shoppingCart.add(tv);
        assertEquals(shoppingCart.getQuantityToDeductStock(tv), expectedQuantity);
    }

    @Test
    void getQuantityToDeductStockTest2() {
        int expectedQuantity = 0;
        shoppingCart.add(tv);
        shoppingCart.add(tv);
        shoppingCart.add(tv);
        assertEquals(shoppingCart.getQuantityToDeductStock(phone), expectedQuantity);
    }

    @Test
    void canAddToCartTest1() {
        Item itemToAdd = phone;
        itemToAdd.setStock(1);
        shoppingCart.add(itemToAdd);

        assertFalse(shoppingCart.canAddToCart(itemToAdd));
    }

    @Test
    void canAddToCartTest2() {
        Item itemToAdd = phone;
        itemToAdd.setStock(1);
        shoppingCart.add(itemToAdd);
        itemToAdd.setStock(0);

        assertFalse(shoppingCart.canAddToCart(itemToAdd));
    }
}