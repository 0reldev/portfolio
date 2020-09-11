package com.reldev.website.repositories;

import com.reldev.website.entities.SkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {

    @Query("SELECT c FROM SkillCategory c ORDER BY c.name ASC")
    public List<SkillCategory> findAllOrderedByName();
}
