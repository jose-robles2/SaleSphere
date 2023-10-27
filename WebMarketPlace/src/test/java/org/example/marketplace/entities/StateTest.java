package org.example.marketplace.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private State california;
    private State anotherCalifornia;
    private State texas;

    @BeforeEach
    void setUp() {
        california = new State("California", 21, true, 21, true, 21, false, 18, true, 18, true, 21, true, 0.07);
        anotherCalifornia = new State("California", 21, true, 21, true, 21, false, 18, true, 18, true, 21, true, 0.07);
        texas = new State("Texas", 18, true, 18, true, 20, true, 17, true, 19, true, 21, true, 0.06);
    }

    @Test
    void getFirearmsAge() {
        assertEquals(21, california.getFirearmsAge());

    }

    @Test
    void isFirearmsAllowed() {
        assertTrue(california.isFirearmsAllowed());

    }

    @Test
    void getTobaccoAge() {
        assertEquals(21, california.getTobaccoAge());

    }

    @Test
    void isTobaccoAllowed() {
        assertTrue(california.isTobaccoAllowed());

    }

    @Test
    void getDrugsAge() {
        assertEquals(21, california.getDrugsAge());

    }

    @Test
    void isDrugAllowed() {
        assertFalse(california.isDrugAllowed());

    }

    @Test
    void getTechnologyAge() {
        assertEquals(18, california.getTechnologyAge());

    }

    @Test
    void isTechnologyAllowed() {
        assertTrue(california.isTechnologyAllowed());

    }

    @Test
    void getMedicineAge() {
        assertEquals(18, california.getMedicineAge());

    }

    @Test
    void isMedicineAllowed() {
        assertEquals(21, california.getAlcoholAge());

    }

    @Test
    void getStateName() {
        assertEquals("California", california.getStateName());

    }

    @Test
    void getAlcoholAge() {
        assertEquals(21, california.getAlcoholAge());

    }

    @Test
    void isAlcoholAllowed() {
        assertTrue(california.isAlcoholAllowed());

    }

    @Test
    void getTaxRate() {
        assertEquals(0.07, california.getTaxRate());
    }

    @Test
    void testEquals() {
        assertEquals(california, anotherCalifornia, "Both objects should be considered equal as they have the same properties.");
        assertNotEquals(california, texas, "Both objects should not be considered equal as they have different properties.");
    }

    @Test
    void testHashCode() {
        assertEquals(california.hashCode(), anotherCalifornia.hashCode(), "Hash codes of two equal objects should be the same.");
    }

    @Test
    void testToString() {
        String expected = "State{stateName='California', firearmsAge=21, firearmsAllowed=true, tobaccoAge=21, tobaccoAllowed=true, drugAge=21, drugAllowed=false, technologyAge=18, technologyAllowed=true, medicineAge=18, medicineAllowed=true, alcoholAge=21, alcoholAllowed=true, taxRate=0.07, id=null}";
        assertEquals(expected, california.toString(), "The toString method should return the expected string representation.");
    }

    @Test
    void testEquals_SameObject() {
        assertTrue(california.equals(california));
    }

    @Test
    void testEquals_DifferentType() {
        assertFalse(california.equals(new Object()));
    }

    @Test
    void testEquals_DifferentStates() {
        assertFalse(california.equals(texas));
    }

    @Test
    void testEquals_SameStateValues() {
        assertTrue(california.equals(anotherCalifornia));
    }


}