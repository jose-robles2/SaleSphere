package org.example.marketplace.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item phone;

    @BeforeEach
    void setUp() {
        phone = new Item("Phone", "IPhone 15", "/image", "Washington", 1199.99, 1, Category.TECHNOLOGY.ordinal());
    }

    @Test
    void getNameTest() {
        String expected = "Phone";
        assertEquals(phone.getName(), expected);
    }

    @Test
    void getDescriptionTest() {
        String expected = "IPhone 15";
        assertEquals(phone.getDescription(), expected);
    }

    @Test
    void getImageUrlTest() {
        String expected = "/image";
        assertEquals(phone.getImageUrl(), expected);
    }

    @Test
    void getLocationStateTest() {
        String expected = "Washington";
        assertEquals(phone.getLocationState(), expected);
    }

    @Test
    void getPriceTest() {
        double expected = 1199.99;
        assertEquals(phone.getPrice(), expected);
    }

    @Test
    void getIdTest() {
        Long expected = null;
        assertEquals(phone.getId(), expected);
    }

    @Test
    void getStockTest() {
        int expected = 1;
        assertEquals(phone.getStock(), expected);
    }

    @Test
    void getCategoryTest() {
        int expected = Category.TECHNOLOGY.ordinal();
        assertEquals(phone.getCategory(), expected);
    }

    @Test
    void setNameTest() {
        String expected = "IPhone2";
        phone.setName(expected);
        assertEquals(phone.getName(), expected);
    }

    @Test
    void setDescriptionTest() {
        String expected = "New Description";
        phone.setDescription(expected);
        assertEquals(phone.getDescription(), expected);
    }

    @Test
    void setImageUrlTest() {
        String expected = "/newImage";
        phone.setImageUrl(expected);
        assertEquals(phone.getImageUrl(), expected);
    }

    @Test
    void setLocationStateTest() {
        String expected = "California";
        phone.setLocationState(expected);
        assertEquals(phone.getLocationState(), expected);
    }

    @Test
    void setPriceTest() {
        double expected = 1000;
        phone.setPrice(expected);
        assertEquals(phone.getPrice(), expected);
    }

    @Test
    void setIdTest() {
        Long expected = 10L;
        phone.setId(expected);
        assertEquals(phone.getId(), expected);
    }

    @Test
    void setStockTest() {
        int expected = 100;
        phone.setStock(expected);
        assertEquals(phone.getStock(), expected);
    }

    @Test
    void setCategoryTest() {
        int expected = Category.ALCOHOL.ordinal();
        phone.setCategory(expected);
        assertEquals(phone.getCategory(), expected);
    }
}