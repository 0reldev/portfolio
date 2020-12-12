package com.reldev.website.controllers;

import com.reldev.website.entities.Experience;
import com.reldev.website.entities.Skill;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.ExperienceRepository;
import com.reldev.website.repositories.SkillCategoryRepository;
import com.reldev.website.repositories.SkillRepository;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExperienceController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @GetMapping("/admin/experience")
    public String getExperience(Model out,
                                @RequestParam(required = false) Long id,
                                @RequestParam(required = false) Long skillId) {

        User user = userService.getLoggedUser();
        Experience experience = new Experience();

        if (id != null) {

            Optional<Experience> optionalExperience = repository.findById(id);
            if (optionalExperience.isPresent()) {

                experience = optionalExperience.get();

                if (skillId != null) {

                    Optional<Skill> optionalSkill = skillRepository.findById(skillId);
                    if (optionalSkill.isPresent()) {

                        Skill skill = optionalSkill.get();
                        experience.removeSkill(skill);
                    }
                }
            }
        }

        out.addAttribute("user", user);
        out.addAttribute("experience", experience);
        out.addAttribute("skillListForSelection", skillRepository.findAllOrderedByCategoryAndName());
        out.addAttribute("skillCategoryList", skillCategoryRepository.findAllOrderedByName());
        out.addAttribute("adminPage", true);
        out.addAttribute("subAdminPage", true);

        List<Skill> skills = new ArrayList<>();
        Method method = getMethod(experience, "getExperienceSkills",
                new Class[]{});
        if (method != null) {

            try {

                skills = (List<Skill>) method.invoke(experience);
            } catch (IllegalAccessException | InvocationTargetException e) {

                e.printStackTrace();
            }
        }
        out.addAttribute("experienceSkills", skills);

        return "/admin/experience";
    }

    @PostMapping("/admin/experience")
    public String postExperience(@RequestParam Long idExperience,
                                 @RequestParam(required = false) Long[] skillIds)
    {
        Optional<Experience> optionalExperience = repository.findById(idExperience);
        if (optionalExperience.isPresent()) {
            Experience experience = optionalExperience.get();

            for (Long idSkill : skillIds) {

                Optional<Skill> optionalSkill = skillRepository.findById(idSkill);
                if (optionalSkill.isPresent()) {
                    Skill skill = optionalSkill.get();

                    List<Skill> skills;
                    Method method = getMethod(experience, "getExperienceSkills",
                            new Class[]{});
                    if (method != null) {
                        try {
                            skills = (List<Skill>) method.invoke(experience);
                            skills.add(skill);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    repository.save(experience);
                }
            }
        }
        return "redirect:/admin#experiencesSection";
    }


    @GetMapping("/admin/experience/delete")
    public String deleteExperience(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin#experiencesSection";
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
