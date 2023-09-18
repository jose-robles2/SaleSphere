package org.example.cms.controllers;

import org.example.cms.entities.Course;
import org.example.cms.repositories.CourseRepository;
import org.example.cms.services.CourseService;
import org.example.cms.services.InstructorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// This course controller recevies requests from index.html
    // we want to retrieve courses for the user, and also let users create a course
//temp
@Controller // handles http reqs
public class CourseController {

    private final CourseService courseService;

    private final InstructorService instructorService;

    public CourseController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    // Returns name of the template that needs to be populated, user's request to localhost triggers this method
    @RequestMapping("/") // root of the app
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.findAll()); // use the service middleman
        model.addAttribute("newCourse", new Course()); // This object lets users add to database

        model.addAttribute("instructors", instructorService.findAll());


        return "index";
    }

    @PostMapping("/createCourse")
    public String createCourse(@ModelAttribute Course course, Model model) {
        courseService.save(course);
        return "redirect:/"; // send us back to the root so we can display info to user
    }
}
