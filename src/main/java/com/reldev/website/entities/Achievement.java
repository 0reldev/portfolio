package com.reldev.website.entities;

import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Lob
    private String description;

    @Column
    private String url;

    @Column
    private String organism;

    @Column
    private String customer;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "filter_tag")
    private String filterTag;

    @Transient
    private String skillList;

/*    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "achievement_skill",
            joinColumns = @JoinColumn(name ="achievement_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();*/

//    @ManyToMany(mappedBy = "achievements")
//    private List<Skill> skills = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "skill_achievement",
            joinColumns = @JoinColumn(name ="achievement_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"achievement_id", "skill_id"}))
    private List<Skill> achievementSkills = new ArrayList<>();



    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "achievementCategory_id")
    private AchievementCategory achievementCategory;


    public Achievement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<Skill> getAchievementSkills() {
        return achievementSkills;
    }

    public void setAchievementSkills(List<Skill> skills) {
        this.achievementSkills = skills;
    }

    public String getFilterTag() {
        return filterTag;
    }

    public void setFilterTag(String filterTag) {
        this.filterTag = filterTag;
    }

    public String getSkillList() {
        return skillList;
    }

    public void setSkillList(String skillList) {
        this.skillList = skillList;
    }

    public AchievementCategory getAchievementCategory() {
        return achievementCategory;
    }

    public void setAchievementCategory(AchievementCategory achievementCategory) {
        this.achievementCategory = achievementCategory;
    }
}


