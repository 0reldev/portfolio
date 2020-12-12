package com.reldev.website.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "job_title")
    private String jobTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column
    private String duration;

    @Lob
    private String description;

    @Lob
    private String mainMission;

    @Column
    private String sector;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "company_name")
    private String companyName;

    @Column
    private String city;

    @Column(name = "company_logo_url")
    private String companyLogoUrl;

    @Column(name = "experience_illustration_url")
    private String experienceIllustrationUrl;

    @Column(name = "filter_tag")
    private String filterTag;

/*    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "experience_skill",
            joinColumns = @JoinColumn(name ="experience_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();*/

//    @ManyToMany(mappedBy = "experiences")
//    private List<Skill> skills = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "skill_experience",
            joinColumns = @JoinColumn(name ="experience_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"experience_id", "skill_id"}))
    private List<Skill> experienceSkills = new ArrayList<>();

    @Transient
    private List<Skill> skillList;

    public Experience() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        this.companyLogoUrl = companyLogoUrl;
    }

    public String getFilterTag() {
        return filterTag;
    }

    public void setFilterTag(String filterTag) {
        this.filterTag = filterTag;
    }

    public List<Skill> getExperienceSkills() {
        return experienceSkills;
    }

    public void setExperienceSkills(List<Skill> experienceSkills) {
        this.experienceSkills = experienceSkills;
    }

    public String getExperienceIllustrationUrl() {
        return experienceIllustrationUrl;
    }

    public void setExperienceIllustrationUrl(String experienceIllustrationUrl) {
        this.experienceIllustrationUrl = experienceIllustrationUrl;
    }

    public String getMainMission() {
        return mainMission;
    }

    public void setMainMission(String mainMission) {
        this.mainMission = mainMission;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    /* Methods */
    public void removeSkill(Skill skill) {
        this.experienceSkills.remove(skill);
        skill.getExperiences().remove(this);
    }

}


