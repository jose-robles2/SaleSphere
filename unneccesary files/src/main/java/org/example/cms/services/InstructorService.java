package org.example.cms.services;

import org.example.cms.entities.Instructor;

public interface InstructorService {
    public Iterable<Instructor> findAll();

    public Instructor save(Instructor instructor);
}
