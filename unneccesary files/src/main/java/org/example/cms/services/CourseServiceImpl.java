package org.example.cms.services;

import org.example.cms.entities.Course;
import org.example.cms.entities.Instructor;
import org.example.cms.repositories.CourseRepository;
import org.example.cms.repositories.InstructorRepository;
import org.springframework.stereotype.Service;

/*
* What's the point of this level of indirection? The purpose is so that in OUR app, we can have a place to do
* our business logic. For example in save() for a store app we can do tax computations etc.
* THIS SERVICE will have lots of logic, and lots of tests
* */

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

//    private InstructorRepository instructorRepository;

    public CourseServiceImpl(CourseRepository courseRepository) { //, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
//        this.instructorRepository = instructorRepository;
    }

    @Override
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course save(Course course) {
        // Get instructors and return the very first one
//        Instructor instructor = instructorRepository.findAll().iterator().next();
//        course.setInstructor(instructor); not needed anymore
        return courseRepository.save(course);
    }
}
