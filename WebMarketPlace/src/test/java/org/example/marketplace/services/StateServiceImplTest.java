package org.example.marketplace.services;

import org.example.marketplace.repositories.StateRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.marketplace.entities.Category;
import org.example.marketplace.entities.Item;
import org.example.marketplace.entities.State;
import org.example.marketplace.entities.User;
import org.example.marketplace.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;

class StateServiceImplTest {
    private StateRepository stateRepository;
    private StateServiceImpl stateService;
    @BeforeEach
    void setUp() {
        stateRepository = mock(StateRepository.class);
        stateService = new StateServiceImpl(stateRepository);  // assuming a constructor injection for the repository
    }

    @Test
    void findAll() {


        State state = new State();
        when(stateRepository.findAll()).thenReturn(Collections.singletonList(state));
        // When
        Iterable<State> states = stateService.findAll();

        // Then
        assertNotNull(states);//Checks that the states object is not null
        assertTrue(states.iterator().hasNext());// Checks that the states iterable is not empty.
        verify(stateRepository, times(1)).findAll();
        //This line verifies that the findAll method of the stateRepository was called exactly
        // once during the execution of the test.


    }

    @Test
    void save() {
        State state = new State();
        when(stateRepository.save(any(State.class))).thenReturn(state);
        // When
        State savedState = stateService.save(state);
        // Then
        assertNotNull(savedState);
        verify(stateRepository, times(1)).save(state);


    }

    @Test
    void delete() {
        // Given
        State state = new State();


        //mocking
        doNothing().when(stateRepository).delete(state);


        // When
        stateService.delete(state);

        // Then
        verify(stateRepository, times(1)).delete(state);
    }

    @Test
    void getState() {
        // Given
        Long id = 1L;
        State expectedState = new State();
        when(stateRepository.findById(id)).thenReturn(Optional.of(expectedState));

        // When
        State retrievedState = stateService.getState(id);

        // Then
        assertEquals(expectedState, retrievedState);
        verify(stateRepository, times(1)).findById(id);

    }

}