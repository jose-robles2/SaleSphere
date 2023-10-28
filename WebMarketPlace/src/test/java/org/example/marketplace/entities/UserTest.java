package org.example.marketplace.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    State alabama = new State("AL",
            18, false,
            18, false,
            21, false,
            25, false,
            25, false,
            18, false, 0.07);

    State alaska = new State("AK",
            21, true,
            15, true,
            33, true,
            32, true,
            1, true,
            99, true, 0.06);

    State arizona = new State("AZ",
            34, true,
            22, false,
            50, false,
            18, true,
            18, true,
            21, true, 0.2);


    User user = new User("Tres", "Hiatt", "tHiatt", 55, arizona, 1000.50);
    User testUser = new User();

    @Test
    void getFirstName() {
        String expected = "Tres";
        assertEquals(user.getFirstName(), expected);
    }

    @Test
    void getLastName() {
        String expected = "Hiatt";
        assertEquals(user.getLastName(), expected);
    }

    @Test
    void getUserName() {
        String expected = "tHiatt";
        assertEquals(user.getUserName(), expected);
    }

    @Test
    void getAge() {
        Integer expected = 55;
        assertEquals(user.getAge(), expected);
    }

    @Test
    void getState() {
        State expected = arizona;
        assertEquals(user.getState(), expected);
    }

    @Test
    void getBalance() {
        Double expected = 1000.50;
        assertEquals(user.getBalance(), expected);
    }

    @Test
    void getId() {
        Long id = null;
        assertEquals(user.getId(), id);
    }

    @Test
    void setFirstName() {
        user.setFirstName("TEST");
        String expected = "TEST";
        assertEquals(user.getFirstName(), expected);
    }

    @Test
    void setLastName() {
        user.setLastName("TEST");
        String expected = "TEST";
        assertEquals(user.getLastName(), expected);
    }

    @Test
    void setUserName() {
        user.setUserName("TEST");
        String expected = "TEST";
        assertEquals(user.getUserName(), expected);
    }

    @Test
    void setAge() {
        user.setAge(25);
        Integer expected = 25;
        assertEquals(user.getAge(), expected);
    }

    @Test
    void setState() {
        user.setState(alaska);
        State expected = alaska;
        assertEquals(user.getState(), expected);
    }

    @Test
    void setBalance() {
        user.setBalance(500.0);
        Double expected = 500.0;
        assertEquals(user.getBalance(), expected);
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}