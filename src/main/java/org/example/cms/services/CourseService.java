package org.example.cms.services;

import org.example.cms.entities.Course;

public interface CourseService {
    public Iterable<Course> findAll();

    public Course save(Course course);
}
