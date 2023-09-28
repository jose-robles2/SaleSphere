package org.example.marketplace.services;

import org.example.marketplace.entities.State;
import org.example.marketplace.repositories.StateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    @Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public Iterable<State> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public State save(State state) { return stateRepository.save(state); }

    @Override
    public void delete(State state) { this.stateRepository.delete(state); }
    @Override
    public State getState(Long ID) { return stateRepository.findById(ID).get(); }

}

