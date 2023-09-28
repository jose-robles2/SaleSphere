package org.example.marketplace.services;

import org.example.marketplace.entities.State;

import java.util.List;

public interface StateService {
    public Iterable<State> findAll();

    public State save(State state);

    public void delete(State state);

    public State getState(Long ID);
}
