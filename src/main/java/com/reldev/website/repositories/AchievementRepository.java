package com.reldev.website.repositories;

import com.reldev.website.entities.Achievement;
import com.reldev.website.entities.Course;
import com.reldev.website.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query("SELECT a FROM Achievement a ORDER BY a.achievementCategory ASC, a.date DESC")
    public List<Achievement> findAllOrderedByDate();

}

/*
TODO: create a PUBLICATIONS entity*/
