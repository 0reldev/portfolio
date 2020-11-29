package com.reldev.website.controllers;

import com.reldev.website.entities.Achievement;
import com.reldev.website.entities.Course;
import com.reldev.website.entities.Skill;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.AchievementCategoryRepository;
import com.reldev.website.repositories.AchievementRepository;
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
public class AchievementController {

    @Autowired
    private UserService userService;

    @Autowired
    private AchievementRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AchievementCategoryRepository achievementCategoryRepository;

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @GetMapping("/admin/achievement")
    public String getAchievement(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        Achievement achievement = new Achievement();
        if (id != null) {

            Optional<Achievement> optionalAchievement = repository.findById(id);
            if (optionalAchievement.isPresent()) {

                achievement = optionalAchievement.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("achievement", achievement);
        out.addAttribute("achievementCategories", achievementCategoryRepository.findAllOrderedByName());
        out.addAttribute("skillCategoryList", skillCategoryRepository.findAllOrderedByName());
        out.addAttribute("skillListForSelection", skillRepository.findAllOrderedByCategoryAndName());
        out.addAttribute("adminPage", true);
        out.addAttribute("subAdminPage", true);

        List<Skill> skills = new ArrayList<>();
        Method method = getMethod(achievement, "getAchievementSkills",
                new Class[]{});
        if (method != null) {

            try {

                skills = (List<Skill>) method.invoke(achievement);
            } catch (IllegalAccessException | InvocationTargetException e) {

                e.printStackTrace();
            }
        }
        out.addAttribute("achievementSkills", skills);
        return "/admin/achievement";
    }

//    @PostMapping("/admin/achievement")
//    public String postAchievement(@ModelAttribute Achievement achievement) {
//
//        repository.save(achievement);
//        return "redirect:/admin#achievementsSection";
//    }

    @PostMapping("/admin/achievement")
    public String postCourse(@RequestParam Long idAchievement,
                             @RequestParam(required = false) Long[] skillIds) {

        Optional<Achievement> optionalAchievement = repository.findById(idAchievement);
        if (optionalAchievement.isPresent()) {
            Achievement achievement = optionalAchievement.get();

            for (Long idSkill : skillIds) {

                Optional<Skill> optionalSkill = skillRepository.findById(idSkill);
                if (optionalSkill.isPresent()) {
                    Skill skill = optionalSkill.get();

                    List<Skill> skills;
                    Method method = getMethod(achievement, "getAchievementSkills",
                            new Class[]{});
                    if (method != null) {
                        try {
                            skills = (List<Skill>) method.invoke(achievement);
                            skills.add(skill);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    repository.save(achievement);
                }
            }
        }
        return "redirect:/admin#achievementsSection";
    }



    @GetMapping("/admin/achievement/delete")
    public String deleteAchievement(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin#achievementsSection";
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
