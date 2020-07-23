package com.reldev.website.repositories;

import com.reldev.website.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}

/*
TODO: add a query to find all the stuff ordered by date*/
