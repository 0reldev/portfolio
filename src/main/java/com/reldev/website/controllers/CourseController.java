package com.reldev.website.controllers;

import com.reldev.website.entities.Course;
import com.reldev.website.entities.Skill;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.CourseRepository;
import com.reldev.website.repositories.SkillCategoryRepository;
import com.reldev.website.repositories.SkillRepository;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

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
        out.addAttribute("skillListForSelection", skillRepository.findAllOrderedByCategoryAndName());
        out.addAttribute("skillCategoryList", skillCategoryRepository.findAllOrderedByName());


        List<Skill> skills = new ArrayList<>();
        Method method = getMethod(course, "getCourseSkills",
                new Class[]{});
        if (method != null) {

            try {

                skills = (List<Skill>) method.invoke(course);
            } catch (IllegalAccessException | InvocationTargetException e) {

                e.printStackTrace();
            }
        }
        out.addAttribute("courseSkills", skills);
        return "/admin/course";
    }


/*
    @PostMapping("/admin/course")
    public String postCourse(@ModelAttribute Course course) {

        repository.save(course);
        return "redirect:/admin";
    }
*/

    @PostMapping("/admin/course")
    public String postCourse(@RequestParam Long idCourse,
                             @RequestParam(required = false) Long[] skillIds) {

        Optional<Course> optionalCourse = repository.findById(idCourse);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            for (Long idSkill : skillIds) {

                Optional<Skill> optionalSkill = skillRepository.findById(idSkill);
                if (optionalSkill.isPresent()) {
                    Skill skill = optionalSkill.get();

                    // call the method getSkills in Course
                    List<Skill> skills;
                    Method method = getMethod(course, "getCourseSkills",
                            new Class[]{});
                    if (method != null) {
                        try {
                            skills = (List<Skill>) method.invoke(course);
                            skills.add(skill);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    repository.save(course);
                }
            }
        }
        return "redirect:/admin";
    }





    @GetMapping("/admin/course/delete")
    public String deleteCourse(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }

    public Method getMethod(Object obj, String methodName, Class[] args) {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName, args);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
