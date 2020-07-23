package com.reldev.website.repositories;

import com.reldev.website.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}

/*
TODO: add a query to find all the stuff ordered by category, then by alphabetical order*/
