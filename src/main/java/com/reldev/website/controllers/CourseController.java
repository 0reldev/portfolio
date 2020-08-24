package com.reldev.website.controllers;

import com.reldev.website.entities.Course;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.CourseRepository;
import com.reldev.website.repositories.SkillRepository;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/admin/course")
    public String getCourse(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        Course course = new Course();
        if (id != null) {

            Optional<Course> optionalCourse = repository.findById(id);
            if (optionalCourse.isPresent()) {

                course = optionalCourse.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("course", course);
        out.addAttribute("skillList", skillRepository.findAll());
        return "/admin/course";
    }

    @PostMapping("/admin/course")
    public String postCourse(@ModelAttribute Course course) {

        repository.save(course);
        return "redirect:/admin";
    }

    @GetMapping("/admin/course/delete")
    public String deleteCourse(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }

}
