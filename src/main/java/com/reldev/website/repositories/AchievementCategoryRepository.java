package com.reldev.website.repositories;

import com.reldev.website.entities.Achievement;
import com.reldev.website.entities.AchievementCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementCategoryRepository extends JpaRepository<AchievementCategory, Long> {

    @Query("SELECT c FROM AchievementCategory c ORDER BY c.name ASC")
    public List<AchievementCategory> findAllOrderedByDate();
}

