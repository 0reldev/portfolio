package com.reldev.website.repositories;

import com.reldev.website.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s FROM Skill s ORDER BY s.expertiseLevel DESC, s.name ASC")
    public List<Skill> findAllOrderedByExpertiseLevelAndName();

    @Query("SELECT s FROM Skill s ORDER BY s.name ASC")
    public List<Skill> findAllOrderedByName();

    @Query("SELECT s FROM Skill s ORDER BY s.skillCategory.name ASC, s.name ASC")
    public List<Skill> findAllOrderedByCategoryAndName();
}