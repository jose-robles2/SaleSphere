package org.example.cms.setup;

import org.example.cms.entities.Course;
import org.example.cms.entities.Instructor;
import org.example.cms.repositories.CourseRepository;
import org.example.cms.repositories.InstructorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Spring will initialize this component, then look at the deps. and inject instances of the deps. into its constr.

@Component
public class InitialSetup implements CommandLineRunner {

    private CourseRepository courseRepository;

    private InstructorRepository instructorRepository;

    public InitialSetup(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    // This method runs automatically since we implement the command line runner - output to console
    @Override
    public void run(String... args) throws Exception {
        Instructor instructor = new Instructor("Subu", "Kandadswamy", "Scholarly associate professor");
        Instructor instructor2 = new Instructor("Venera", "Arnadouva", "associate professor");

        // This saves the object and returns objects with populated random long id
        instructor = instructorRepository.save(instructor);
        instructor2 = instructorRepository.save(instructor2);

        Course course = new Course("Cpts 422", "Software Testing", instructor);
        Course course2 = new Course("Cpts 321", "OOP", instructor2);

        // This saves the object and returns objects with populated random long id
        course = courseRepository.save(course);
        course2 = courseRepository.save(course2);

        System.out.println("Initializing database...");
        System.out.println(course);
        System.out.println(course2);
    }
}
